package ru.job4j.list;

/**
 * Класс NodeCycleChecker реализует сущность Проверятель списка узлов на ссылочную замкнутость.
 *
 * @param <T> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-31
 */
class NodeCycleChecker<T> {
    /**
     * Позиция.
     */
    private int pos = 0;
    /**
     * Проверяет список узлов на ссылочную замкнутость.
     * @param first первый элемент списка.
     * @return true если список зациклен. Иначе false.
     */
    public boolean hasCycle(Node<T> first) {
        boolean result = false;
        Node<T> x = first.getNext();
        if (x != null) {
            this.pos++;
            Node<T> x2 = x;
            for (int a = this.pos * 2; a > this.pos; a--) {
                x2 = x2.getNext();
                if (x2 == null) {
                    break;
                }
            }
            if (x2 != null) {
                result = x == x2 ? true : this.hasCycle(x);
            }
        }
        return result;
    }
}
