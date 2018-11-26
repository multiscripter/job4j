package ru.job4j.control;

/**
 * Класс Concrete реализует сущность Бетонное препятствие.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-05
 */
class Concrete implements IEntity {
    /**
     * Игровое поле.
     */
    private final Board board;
    /**
     * Счётчик объектов.
     */
    private static int counter = 0;
    /**
     * Имя сущности.
     */
    private final String name;
    /**
     * Объект координат позиции.
     */
    private final Position pos;
    /**
     * Короткое имя сущности.
     */
    private final String sname = "O";
    /**
     * Коснтруктор.
     * @param board игровое поле.
     * @param pos объект координат позиции.
     */
    Concrete(final Board board, final Position pos) {
        this.board = board;
        this.pos = pos;
        this.name = "Concrete-" + counter++;
    }
    /**
     * Разрушает препятстивие.
     */
    public void destruct() {
    }
    /**
     * Возвращает имя сущности.
     * @return имя сущности.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Возвращает позицию.
     * @return объект координат позициии на поле.
     */
    public Position getPosition() {
        return this.pos;
    }
    /**
     * Возвращает короткое имя сущности.
     * @return короткое имя сущности.
     */
    public String getSname() {
        return this.sname;
    }
}