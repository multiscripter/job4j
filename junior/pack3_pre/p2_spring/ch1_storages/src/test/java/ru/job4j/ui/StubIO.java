package ru.job4j.ui;

/**
 * Класс StubIO реализует сущность тестирования ввода из консоли.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-04
 * @since 2017-04-19
 */
public class StubIO implements IIO {
    /**
     * массив ответов.
     */
    private String[] answers;
    /**
     * Позиция в массиве ответов.
     */
    private int position;
    /**
     * Конструктор.
     * @param answers массив ответов.
     */
    public StubIO(String[] answers) {
        this.answers = answers;
        this.position = 0;
    }
    /**
     * Опрашивает пользователя.
     * @param question вопрос пользователю.
     * @return ответ пользователя.
     */
    public String ask(String question) {
        return this.answers[this.position++];
    }
    /**
     * Опрашивает пользователя.
     * @param question вопрос пользователю.
     * @param range диапазон допустимых значений.
     * @return ответ пользователя.
     */
    public int ask(String question, int[] range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        }
        throw new MenuOutException("out of menu range");
    }
}
