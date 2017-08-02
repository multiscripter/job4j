package ru.job4j.waitnotify;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс ProducerConsumer иллюстрирует проблему "Producer–consumer".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-01
 */
class ProducerConsumer {
    /**
     * Главный метод.
     * @param args массив аргументов вызова.
     */
    public static void main(String[] args) {
        System.out.println("ProducerConsumer.main()");
    }
}
/**
 * Класс SimpleBlockingQueue реализует сущность "Блокирующая очередь".
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-08-01
 */
@ThreadSafe
class SimpleBlockingQueue<E> {
    /**
     * Очередь.
     */
    private LinkedList<E> queue;
    /**
     * Максимальный размер очереди.
     */
    private int maxSize;
    /**
     * Объект блокировки (монитора).
     */
    private Object lock;
    /**
     * Конструктор без параметров.
     */
    SimpleBlockingQueue() {
        this.queue = new LinkedList<>();
        this.maxSize = 0;
        this.lock = this;
    }
    /**
     * Конструктор.
     * @param maxSize максимальный размер очереди.
     */
    SimpleBlockingQueue(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException("Size cannot be less than 1.");
        }
        if (maxSize > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(String.format("Size cannot be greater than %d.", Integer.MAX_VALUE));
        }
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;
        this.lock = new Object();
    }
    /**
     * Добавляет элемент в конец очереди.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в очередь.
     */
    @GuardedBy("lock")
    public boolean add(E e) {
        synchronized (this.lock) {
            boolean result = this.maxSize > 0 && this.size() == this.maxSize ? false : this.queue.add(e);
            return result;
        }
    }
    /**
     * Возвращает итератор.
     * @return итератор.
     */
    public Iterator<E> iterator() {
        return this.queue.iterator();
    }
    /**
     * Удаляет элемент с указанным индексом из очереди.
     * @param index индекс удаляемого элемента.
     * @return удалённый элемент.
     */
    @GuardedBy("lock")
    public E remove(int index) {
        synchronized (this.lock) {
            E result = index < this.size() ? this.queue.remove(index) : null;
            return result;
        }
    }
    /**
     * Возвращает размер очереди.
     * @return размер очереди.
     */
    public int size() {
        return this.queue.size();
    }
}
/**
 * Класс Data реализует сущность "Данные".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-02
 */
class Data {
    /**
     * Идентификатор производителя.
     */
    private int producerId;
    /**
     * Данные.
     */
    private int data;
    /**
     * Конструктор.
     * @param producerId идентификатор производителя.
     * @param data данные.
     */
    Data(int producerId, int data) {
        this.producerId = producerId;
        this.data = data;
    }
    /**
     * Получает идентификатор производителя.
     * @return идентификатор производителя.
     */
    public int getProducerId() {
        return this.producerId;
    }
    /**
     * Получает данные производителя.
     * @return данные.
     */
    public int getData() {
        return this.data;
    }
    /**
     * Возвращает строковое представление объекта данных.
     * @return строковое представление объекта данных.
     */
    @Override
    public String toString() {
        return String.format("Producer-%d. Data: %d", this.producerId, this.data);
    }
}
/**
 * Класс Producer реализует сущность "Производитель".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-01
 */
class Producer extends Thread {
    /**
     * Счётчик объектов.
     */
    private static int counter = 0;
    /**
     * Данные.
     */
    private int data;
    /**
     * Идентификатор.
     */
    private int id;
    /**
     * Объект блокирующей очереди.
     */
    private SimpleBlockingQueue sbq;
    /**
     * Количество запросов к очереди.
     */
    private int reqs;
    /**
     * Конструктор.
     * @param sbq объект блокирующей очереди.
     * @param reqs количество запросов к очереди.
     */
    Producer(SimpleBlockingQueue sbq, int reqs) {
        this.data = 1;
        this.id = counter++;
        this.setName(String.format("Producer-%d", this.id));
        this.sbq = sbq;
        this.reqs = reqs;
    }
    /**
     * Производит данные.
     * @return произведённые данные.
     */
    private Data produce() {
        return new Data(this.id, this.data++);
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            Thread t = Thread.currentThread();
            Data data;
            while (this.reqs-- > 0) {
                data = this.produce();
                while (!this.sbq.add(data)) {
                    t.sleep(5);
                }
            }
            t.interrupt();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
/**
 * Класс Collector реализует сущность "Сборщик данных".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-02
 */
@ThreadSafe
class Collector {
    /**
     * Массив.
     */
    private int[] collector;
    /**
     * Объект блокировки (монитора).
     */
    private Object lock;
    /**
     * Конструктор.
     * @param size размер массива.
     */
    Collector(int size) {
        this.collector = new int[size];
        this.lock = this;
    }
    /**
     * Получает массив.
     * @return массив.
     */
    public int[] getCollector() {
        return this.collector;
    }
    /**
     * Устанавливает данные по индексу.
     * @param index индекс элемента.
     * @param value данные.
     */
    @GuardedBy("lock")
    public void setValue(int index, int value) {
        synchronized (this.lock) {
            this.collector[index] += value;
        }
    }
}
/**
 * Класс Consumer реализует сущность "Потребитель".
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-01
 */
class Consumer extends Thread {
    /**
     * Счётчик объектов.
     */
    private static int counter = 0;
    /**
     * Идентификатор объекта.
     */
    private int id;
    /**
     * Объект рандомизатора.
     */
    private Random rnd;
    /**
     * Объект блокирующей очереди.
     */
    private SimpleBlockingQueue sbq;
    /**
     * Количество запросов к очереди.
     */
    private int reqs;
    /**
     * Объект сборщика.
     */
    private Collector collector;
    /**
     * Конструктор.
     * @param sbq объект блокирующей очереди.
     * @param reqs количество запросов к очереди.
     * @param collector объект сборщика.
     */
    Consumer(SimpleBlockingQueue sbq, int reqs, Collector collector) {
        this.id = counter++;
        this.setName(String.format("Consumer-%d", this.id));
        this.rnd = new Random(new Date().getTime());
        this.sbq = sbq;
        this.reqs = reqs;
        this.collector = collector;
    }
    /**
     * Потребляет данные.
     * @param data данные Производителя.
     */
    private void consume(Data data) {
        if (data != null) {
            this.collector.setValue(data.getProducerId(), data.getData());
        }
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            Thread t = Thread.currentThread();
            Data data;
            int size;
            int index;
            while (this.reqs-- > 0) {
                do {
                    do {
                        t.sleep(1);
                        size = this.sbq.size();
                    } while (size < 1);
                    data = (Data) this.sbq.remove(this.rnd.nextInt(size));
                } while (null == data);
                this.consume(data);
            }
            t.interrupt();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
