package tasks.lift;

import java.util.HashMap;
/**
 * LiftMenu реализует сущность Меню лифта.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-27
 * @since 2018-01-22
 */
class LiftMenu {
    /**
     * Массив действий пользователя.
     */
    private final HashMap<Integer, ILiftAction> actions;
    /**
     * Источник ввода и вывода данных.
     */
    private final AbstractLiftIO io;
    /**
     * Абстрактный лифт.
     */
    private final LiftClient client;
    /**
     * Конструктор.
     * @param io источник ввода и вывода данных.
     * @param client абстрактный лифт.
     */
    LiftMenu(final LiftClient client, final AbstractLiftIO io) {
        this.actions = new HashMap<>();
        this.io = io;
        this.client = client;
    }
    /**
     * Инициализирует меню лифта.
     */
    public void fillActions() {
        this.actions.clear();
        HashMap<Integer, String> exitMsgs = new HashMap<>();
        exitMsgs.put(0, "Exiting from program.");
        this.actions.put(LiftActions.EXIT.ordinal(), new ActionExit(LiftActions.EXIT, exitMsgs));
        if (this.client.getStatus() == 0) {
            HashMap<Integer, String> callMsgs = new HashMap<>();
            callMsgs.put(0, "Lift not or already called.");
            callMsgs.put(1, "Lift called. Please wait.");
            callMsgs.put(3, "Lift already on selected floor");
            this.actions.put(LiftActions.CALL.ordinal(), new ActionCall(LiftActions.CALL, callMsgs));
        }
        if (this.client.getStatus() == 1) {
            HashMap<Integer, String> moveMsgs = new HashMap<>();
            moveMsgs.put(0, "Floor not or already selected.");
            moveMsgs.put(1, "Floor selected.");
            moveMsgs.put(2, "Invalid floor.");
            moveMsgs.put(3, "You already on selected floor");
            this.actions.put(LiftActions.MOVE.ordinal(), new ActionMove(LiftActions.MOVE, moveMsgs));
        }
    }
    /**
     * Получает сообщение по коду.
     * @param num номер действия.
     * @param code код сообщения.
     * @return сообщение.
     */
    public String getMessage(final int num, final int code) {
        return this.actions.get(num).getMessage(code);
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param num номер действия.
     * @return команда в виде строки.
     * @throws NullPointerException несуществующий элемент.
     */
    public String select(final int num) throws NullPointerException {
        return this.actions.get(num).execute(this.client, this.io);
    }
    /**
     * Выводит меню администратора лифта.
     */
    public void show() {
        for (ILiftAction action : this.actions.values()) {
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
 * @version 2018-02-12
 * @since 2018-01-22
 */
class ActionExit extends LiftAction {
    /**
     * Конструктор.
     * @param action константа действия.
     * @param messages хэш-карта сообщений.
     */
    ActionExit(LiftActions action, HashMap<Integer, String> messages) {
        super(action, messages);
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param client абстрактный лифт.
     * @param io источник ввода и вывода данных.
     * @return команда в виде строки.
     */
    @Override
    public String execute(final LiftClient client, final AbstractLiftIO io) {
        io.write(this.getMessage(0));
        return "exit";
    }
}
/**
 * Класс ActionCall реализует действие Вызов лифта.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-12
 * @since 2018-01-26
 */
class ActionCall extends LiftAction {
    /**
     * Конструктор.
     * @param action константа действия.
     * @param messages хэш-карта сообщений.
     */
    ActionCall(LiftActions action, HashMap<Integer, String> messages) {
        super(action, messages);
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param client абстрактный лифт.
     * @param io источник ввода и вывода данных.
     * @return команда в виде строки.
     */
    @Override
    public String execute(final LiftClient client, final AbstractLiftIO io) {
        io.write(String.format("Calling lift to floor %d.", client.getCurrentFloor()));
        return String.format("Call:%d", client.getCurrentFloor());
    }
}
/**
 * Класс ActionMove реализует действие Выбор этажа.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-12
 * @since 2018-01-30
 */
class ActionMove extends LiftAction {
    /**
     * Конструктор.
     * @param action константа действия.
     * @param messages хэш-карта сообщений.
     */
    ActionMove(LiftActions action, HashMap<Integer, String> messages) {
        super(action, messages);
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param client абстрактный лифт.
     * @param io источник ввода и вывода данных.
     * @return команда в виде строки.
     */
    @Override
    public String execute(final LiftClient client, final AbstractLiftIO io) {
        io.write("Please, enter floor number.");
        String numStr = "";
        do {
            numStr = io.read();
        } while (!client.validate(numStr));
        String command = "";
        int num = Integer.parseInt(numStr);
        if (client.getStatus() == 1) {
            client.setDestFloor(num);
            command = String.format("Call:%s", numStr);
        } else if (client.getStatus() == 0 && num == 1) {
            command = String.format("Call:%s", client.getCurrentFloor());
        }
        return command;
    }
}