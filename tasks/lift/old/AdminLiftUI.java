package tasks.lift;

/**
 * AdminLiftUI реализует сущность Интерфэйс администрирования лифта.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-18
 */
class AdminLiftUI {
    /**
     * Источник ввода данных.
     */
    private final AbstractLiftIO io;
    /**
     * Меню.
     */
    private final AdminMenu menu;
    /**
     * Конструктор.
     * @param liftParams параметры лифта.
     * @param io источник ввода и вывода данных.
     */
    AdminLiftUI(final HashMap<String, Integer> liftParams, final AbstractLiftIO io) {
        this.io = io;
        this.menu = new AdminMenu(liftParams, this.io);
    }
    /**
     * Инициализирует интерфэйс администрирования лифта.
     */
    public void init() {
        this.fillActions();
        while (true) {
            this.show();
            this.select();
        }
    }
}