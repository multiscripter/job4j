package ru.job4j.control;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс BoundedBufferABQ реализует сущность Ограниченный буфер на базе ArrayBlockingQueue.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-31
 */
@ThreadSafe
class BoundedBufferABQ<E> implements IBoundedBuffer<E> {
    /**
     * Буфер.
     */
    private final ArrayBlockingQueue<E> buffer;
    /**
     * Ёмкость буфера.
     */
    private int capacity = 16;
    /**
     * Монитор.
     */
    private final ReentrantLock lock = new ReentrantLock();
    /**
     * Объект условия "на пустоту".
     */
	private final Condition isEmpty = lock.newCondition();
    /**
     * Объект условия "на заполненность".
     */
	private final Condition isFull = lock.newCondition();
    /**
     * Колчиество элементов в буфере.
     */
    private int size = 0;
    /**
     * Конструктор без параметров.
     */
    BoundedBufferABQ() {
        this.buffer = new ArrayBlockingQueue<>(this.capacity);
    }
    /**
     * Конструктор.
     * @param capacity размер буфера.
     */
    BoundedBufferABQ(int capacity) {
        this.capacity = capacity;
        this.buffer = new ArrayBlockingQueue<>(capacity);
    }
    /**
     * Добавляет элемент в буфер.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в буфер. Иначе false.
     * @throws InterruptedException исключение прерывания.
     */
    @GuardedBy("lock")
    public boolean add(E e) throws InterruptedException {
        this.lock.lock();
        while (this.size >= this.capacity) {
            this.isFull.await();
        }
        boolean result = this.buffer.add(e);
        this.size++;
        this.isEmpty.signal();
        this.lock.unlock();
        return result;
    }
    /**
     * Удаляет элемент из буфера.
     * @return удалённый элемент.
     * @throws InterruptedException исключение прерывания.
     */
    @GuardedBy("lock")
    public E remove() throws InterruptedException {
        this.lock.lock();
        while (this.size < 1) {
            this.isEmpty.await();
        }
        E e = this.buffer.poll();
        this.size--;
        this.isFull.signal();
        this.lock.unlock();
        return e;
    }
}
