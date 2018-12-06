package ru.job4j.control;

/**
 * IBoundedBuffer объявляет интерфэйс Ограниченный буфер.
 *
 * @param <E> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-08-31
 */
public interface IBoundedBuffer<E> {
    /**
     * Добавляет элемент в буфер.
     * @param e добавляемый элемент.
     * @return true если элемент добавлен в буфер. Иначе false.
     * @throws InterruptedException исключение прерывания.
     */
    boolean add(E e) throws InterruptedException;
    /**
     * Удаляет элемент из буфера.
     * @return удалённый элемент.
     * @throws InterruptedException исключение прерывания.
     */
    E remove() throws InterruptedException;
}