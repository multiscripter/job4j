package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class ArrayDuplicateTest тестирует методы класса ArrayDuplicate.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-10
 */
public class ArrayDuplicateTest {
    /**
     * Тестирует метод String[] remove(String[] array), удаляющий дубликаты в массиве.
     */
    @Test
    public void testRemove() {
        String[] arr = {"Привет", "Привет", "Супер", "Мир", "Мир", "Привет", "Мир", "Мир", "Мир"};
        ArrayDuplicate arrDup = new ArrayDuplicate();
        String[] result = arrDup.remove(arr);
        String[] expected = {"Привет", "Супер", "Мир"};
        assertThat(result, is(expected));
    }
}