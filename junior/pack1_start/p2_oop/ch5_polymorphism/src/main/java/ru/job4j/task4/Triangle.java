package ru.job4j.task4;

/**
 * Класс Triangle реализует сущность треугольник.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-22
 */
class Triangle implements Shape {
    /**
     * Возвращает фигуру в виде строки.
     * @return фигура в виде строки.
     */
    public String pic() {
        StringBuilder sb = new StringBuilder();
        sb.append("  *  ");
        sb.append(System.getProperty("line.separator"));
        sb.append(" * * ");
        sb.append(System.getProperty("line.separator"));
        sb.append("*****");
        return sb.toString();
    }
}
