package ru.job4j.task4;

/**
 * Класс Paint реализует сущность рисования.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-22
 */
class Paint {
    /**
     * Рисует фигуру в консоль.
     * @param shape фигура для отрисовки.
     */
    public void draw(Shape shape) {
        System.out.println(shape.pic());
    }
}
