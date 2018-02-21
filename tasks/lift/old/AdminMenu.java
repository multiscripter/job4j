package tasks.lift;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
/**
 * AdminMenu реализует меню администратора лифта.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-22
 */
class AdminMenu {
    /**
     * Массив действий пользователя.
     */
    private final ArrayList<ILiftAction> actions;
    /**
     * Ёмкость буфера.
     */
    private final int capacity = 8;
    /**
     * Источник ввода и вывода данных.
     */
    private final AbstractLiftIO io;
    /**
     * Лифт.
     */
    private Lift lift;
    /**
     * Параметры лифта.
     */
    private final HashMap<String, Integer> liftParams;
    /**
     * Символ новой строки.
     */
    public static final String LS = System.getProperty("line.separator");
    /**
     * Канал сокета.
     */
    private final SocketChannel sockCh;
    /**
     * Конструктор.
     * @param liftParams параметры лифта.
     * @param io источник ввода и вывода данных.
     */
    AdminMenu(final HashMap<String, Integer> liftParams, final AbstractLiftIO io) {
        this.actions = new ArrayList<>();
        this.io = io;
        this.lift = null;
        this.liftParams = liftParams;
        try (SocketChannel sockCh = SocketChannel.open()) {
            this.sockCh = sockCh;
            this.sockCh.configureBlocking(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Соединиться с лифтом.
     * @return true если соединение установлено. Иначе false.
     */
    public boolean connect() {
        boolean result = false;
        try {
            this.sockCh.connect(new InetSocketAddress("localhost", this.liftParams.get("port")));
            while (!this.sockCh.finishConnect()) {
                System.out.println("Идёт соединение с сервером.");
                this.wait(20);
            }
            result = true;
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            return result;
        }
    }
    /**
     * Инициализирует меню администратора лифта.
     */
    public void fillActions() {
        this.actions.add(new ActionExit(AdminActions.EXIT));
        this.actions.add(new ActionPowerOn(AdminActions.POWERON));
        this.actions.add(new ActionPowerOff(AdminActions.POWEROFF));
    }
    /**
     * Выполняет действие, выбранное пользователем.
     */
    public void select() {
        try {
            int key = Integer.parseInt(this.io.read(), 10);
            this.actions.get(key).execute(this.lift, this.io);
        } catch (NumberFormatException ex) {
            this.io.write("Enter positive integer number.");
        }
    }
    /**
     * Выводит меню администратора лифта.
     */
    public void show() {
        this.io.write("Please, enter the action number:");
        for (ILiftAction action : this.actions) {
            if (action != null) {
                this.io.write(action.info());
            }
        }
    }
}
/**
 * Класс ActionExit реализует действие Выход из программы.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-22
 */
class ActionExit extends LiftAction {
    /**
     * Конструктор.
     * @param action константа действия.
     */
    ActionExit(AdminActions action) {
        super(action);
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param lift лифт.
     * @param io источник ввода и вывода данных.
     */
    public void execute(final Lift lift, final AbstractLiftIO io) {
        io.write("Exiting from program.");
        try {
            System.exit(0);
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }
}
/**
 * Класс ActionPowerOn реализует действие Включение лифта.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-22
 */
class ActionPowerOn extends LiftAction {
    /**
     * Конструктор.
     * @param action константа действия.
     */
    ActionPowerOn(AdminActions action) {
        super(action);
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param lift лифт.
     * @param io источник ввода и вывода данных.
     */
    public void execute(final Lift lift, final AbstractLiftIO io) {
        String message = "Lift power is not on";
        if (lift.on()) {
            message = "Lift power is on";
        }
        io.write(message);
    }
}
/**
 * Класс ActionPowerOff реализует действие Выключение лифта.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-22
 */
class ActionPowerOff extends LiftAction {
    /**
     * Конструктор.
     * @param action константа действия.
     */
    ActionPowerOff(AdminActions action) {
        super(action);
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param lift лифт.
     * @param io источник ввода и вывода данных.
     */
    public void execute(final Lift lift, final AbstractLiftIO io) {
        String message = "Lift power is on";
        if (lift.off()) {
            message = "Lift power is not on";
        }
        io.write(message);
    }
}