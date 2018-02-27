package tasks.lift;

/**
 * LiftActions реализует перечисление действий интерфейса меню лифта.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-27
 * @since 2018-01-26
 */
enum LiftActions {
    /**
     * Выход из программы.
     */
    EXIT("Exit from program"),
    /**
     * Вызвать лифт.
     */
    CALL("Call lift"),
    /**
     * Выбрать этаж.
     */
    MOVE("Select floor");
    /**
     * Значение перечисления.
     */
    private final String value;
    /**
     * Конструктор.
     * @param value значение перечисления.
     */
    LiftActions(String value) {
        this.value = value;
    }
    /**
     * Возвращает значение перечисления.
     * @return значение перечисления.
     */
    public String getValue() {
        return this.value;
    }
}