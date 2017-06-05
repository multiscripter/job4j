package ru.job4j.list;

/**
 * Класс NodeCycleChecker реализует сущность Проверятель списка узлов на ссылочную замкнутость.
 *
 * @param <T> параметризированный тип.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
 * @since 2017-05-31
 */
class NodeCycleChecker<T> {
    /**
     * Позиция.
     */
    private int pos = 0;
    /**
     * Проверяет список узлов на ссылочную замкнутость.
     * @param x первый элемент списка.
     * @return true если список зациклен. Иначе false.
     */
    public boolean hasCycle(Node<T> x) {
        boolean result = false;
        Node<T> x2 = x.getNext();
        while (x != null || x2 != null) {
            this.pos++;
            for (int a = this.pos * 2; a > this.pos; a--) {
                if (x2 == null) {
                    break;
                }
                x2 = x2.getNext();
            }
            if (x == x2) {
                result = true;
                break;
            }
            x = x.getNext();
        }
        return result;
    }
}
