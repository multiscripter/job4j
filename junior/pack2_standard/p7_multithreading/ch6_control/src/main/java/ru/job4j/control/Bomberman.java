package ru.job4j.control;

/**
 * Класс Bomberman реализует сущность Бомбермэн.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2017-09-06
 * @since 2017-08-25
 */
class Bomberman extends Creature {
    /**
     * Счётчик объектов.
     */
    private static int counter = 0;
    /**
     * Имя сущности.
     */
    private final String name;
    /**
     * Короткое имя сущности.
     */
    private final String sname = "X";
    /**
     * Коснтруктор.
     * @param board игровое поле.
     * @param pos объект координат позиции.
     */
    Bomberman(final Board board, final Position pos) {
        super(board, pos);
        this.name = "Bomberman-" + counter++;
    }
    /**
     * Возвращает имя сущности.
     * @return имя сущности.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Возвращает короткое имя сущности.
     * @return короткое имя сущности.
     */
    public String getSname() {
        return this.sname;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        this.getBoard().blockPosition(this.getPosition());
        try {
            while (!Thread.currentThread().isInterrupted()) {
                //Ожидаем ввода пользователя.
                //this.move();
                Thread.currentThread().sleep(100);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
