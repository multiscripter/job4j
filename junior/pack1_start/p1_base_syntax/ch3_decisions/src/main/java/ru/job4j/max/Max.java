package ru.job4j.max;

/**
 * Class Max вычисляет больше из двух чисел.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version %G%
 * @since 1
 */
public class Max {
    /**
     * Сравнивает два значения и возвращает большее из них.
     * @param first первое значение.
     * @param second второе значение.
     * @return большее из зничений.
     */
    public int max(int first, int second) {
        return first > second ? first : second;
    }
}
