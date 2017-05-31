package ru.job4j.list;

import java.util.LinkedList;
/**
 * Класс NodeCycleChecker реализует сущность Проверятель списка узлов на ссылочную замкнутость.
 *
 * @param <T> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-31
 */
class NodeCycleChecker<T> {
    /**
     * Проверяет список узлов на ссылочную замкнутость.
     * @param first первый элемент списка.
     * @return true если список зациклен. Иначе false.
     */
    public boolean hasCycle(Node<T> first) {
        boolean result = false;
        if (first.getNext() != null) {
            LinkedList<Node<T>> list = new LinkedList<>();
            list.add(first);
            do {
                if (list.contains(list.getLast().getNext())) {
                    result = true;
                    break;
                }
                list.add(list.getLast().getNext());
            } while (list.getLast() != null);
        }
        return result;
    }
}
