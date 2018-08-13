package ru.job4j.ui;

/**
 * Класс ValidateIO реализует сущность Валидации данных, введённых из консоли.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-03
 * @since 2017-04-24
 */
class ValidateIO extends ConsoleIO {
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
                System.out.println("Please, select key from menu.");
            } catch (NumberFormatException ex) {
                System.out.println("Please, enter valid data again.");
            }
        } while (invalid);
        return value;
    }
}
