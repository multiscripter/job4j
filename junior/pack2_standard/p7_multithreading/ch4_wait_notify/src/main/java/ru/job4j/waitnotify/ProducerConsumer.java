package ru.job4j.waitnotify;

import java.util.Date;
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
 * @version 1
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
     * Конструктор без параметров.
     */
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
        this.lock = new Object();
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
            return this.queue.add(e);
        }
    }
    /**
     * Удаляет элемент с указанным индексом из очереди.
     * @param index индекс удаляемого элемента.
     * @return удалённый элемент.
     */
    @GuardedBy("lock")
    public E remove(int index) {
        synchronized (this.lock) {
            return this.queue.remove(index);
        }
    }
    /**
     * Возвращает размер очереди.
     * @return размер очереди.
     */
    @GuardedBy("lock")
    public int size() {
        synchronized (this.lock) {
            return this.queue.size();
        }
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
    private static int counter = 1;
    /**
     * Данные.
     */
    private int data;
    /**
     * Имя объекта.
     */
    private String name;
    /**
     * Объект блокирующей очереди.
     */
    private SimpleBlockingQueue sbq;
    /**
     * Конструктор.
     * @param sbq объект блокирующей очереди.
     */
    Producer(SimpleBlockingQueue sbq) {
        this.name = String.format("Producer-%d", counter++);
        this.sbq = sbq;
    }
    /**
     * Производит данные.
     * @return произведённые данные.
     */
    private String produce() {
        return String.format("%s. Data: %d", this.name, this.data++);
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        this.sbq.add(this.produce());
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
     * Объект рандомизатора.
     */
    private Random rnd;
    /**
     * Объект блокирующей очереди.
     */
    private SimpleBlockingQueue sbq;
    /**
     * Конструктор.
     * @param sbq объект блокирующей очереди.
     */
    Consumer(SimpleBlockingQueue sbq) {
        this.rnd = new Random(new Date().getTime());
        this.sbq = sbq;
    }
    /**
     * Потребляет данные.
     * @param data данные Производителя.
     */
    private void consume(String data) {
        //Сделать что-то с данными Производителя.
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        this.consume((String) this.sbq.remove(this.rnd.nextInt(this.sbq.size())));
    }
}
