package tasks.lift;

/**
 * AdminActions реализует перечисление действий интерфейса меню администратора лифта.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-22
 */
enum AdminActions implements ILiftActions {
    /**
     * Выход из программы.
     */
    EXIT("Exit from program"),
    /**
     * Включить лифт.
     */
    POWERON("Power on"),
    /**
     * Выключить лифт.
     */
    POWEROFF("Power off");
    /**
     * Значение перечисления.
     */
    private String value;
    /**
     * Конструктор.
     * @param value значение перечисления.
     */
    AdminActions(String value) {
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