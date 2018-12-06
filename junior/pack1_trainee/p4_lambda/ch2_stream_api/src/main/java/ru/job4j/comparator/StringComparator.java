package ru.job4j.comparator;

import java.util.Comparator;
/**
 * Класс StringComparator реализует функционал сравнения срок.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-05
 * @since 2018-12-05
 */
public class StringComparator implements Comparator<String> {
    /**
     * Сравнивает две строки.
     * @param left первая сравниваемая строка.
     * @param right вторая сравниваемая строка.
     * @return результат сравнения.
     */
    @Override
    public int compare(String left, String right) {
        int result = 0;
        int a = 0;
        for (; a < left.length() && a < right.length(); a++) {
            result = Character.compare(left.charAt(a), right.charAt(a));
            if (result != 0) {
                break;
            }
        }
        if (result == 0) {
            result = Integer.compare(left.length(), right.length());
        }
        return result;
    }
}
