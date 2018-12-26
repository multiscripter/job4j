package ru.job4j.tracker;

/**
 * Класс ValidateInput реализует сущность валидации данных, введённых из консоли.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-20
 * @since 2017-04-24
 */
class ValidateInput extends ConsoleInput {
    /**
     * Опрашивает пользователя.
     * @param question вопрос пользователю.
     * @param range диапазон допустимых значений.
     * @return ответ пользователя.
     */
    public int ask(String question, int[] range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = super.ask(question, range);
                invalid = false;
            } catch (MenuOutException ex) {
                System.err.println("Please, select key from menu.");
            } catch (NumberFormatException ex) {
                System.err.println("Please, enter valid data again.");
            }
        } while (invalid);
        return value;
    }
}
