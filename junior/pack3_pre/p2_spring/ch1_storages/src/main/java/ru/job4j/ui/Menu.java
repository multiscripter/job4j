package ru.job4j.ui;

import java.util.ArrayList;
import ru.job4j.models.User;
import ru.job4j.services.Storage;
/**
 * Класс Menu реализует сущность Меню хранилища.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-08-04
 * @since 2018-08-03
 */
class Menu {
    /**
     * Объект ввода-вывода.
     */
    private IIO io;
    /**
     * Символ новой строки.
     */
    public static final String LS = System.getProperty("line.separator");
    /**
     * Хранилище.
     */
    private Storage storage;
    /**
     * Массив действий пользователя.
     */
    private ArrayList<IUserAction> actions = new ArrayList<>();
    /**
     * Конструктор.
     * @param io объект ввода-вывода.
     * @param storage объект хранилища.
     */
    Menu(IIO io, Storage storage) {
        this.io = io;
        this.storage = storage;
    }
    /**
     * Инициализирует меню хранилища.
     */
    public void fillActions() {
        this.actions.add(new AddUser(MenuActions.ADD));
        this.actions.add(new ShowUsers(MenuActions.SHOW));
        this.actions.add(new ActionExit(MenuActions.EXIT));
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param key идентификатор действия.
     */
    public void select(int key) {
        this.actions.get(key).execute(this.io, this.storage);
    }
    /**
     * Выводит меню хранилища.
     */
    public void show() {
        System.out.println("Please, enter the action number:");
        for (IUserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }
    /**
     * Класс AddUser реализует сущность Добавление пользователя в хранилище.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 2018-08-03
     * @since 2017-04-23
     */
    private class AddUser extends UserAction {
        /**
         * Конструктор.
         * @param action константа действия.
         */
        AddUser(MenuActions action) {
            super(action);
        }
        /**
         * Выполняет действие, выбранное пользователем.
         * @param io объект ввода-вывода.
         * @param storage объект хранилища.
         */
        public void execute(IIO io, Storage storage) {
            System.out.println("");
            System.out.println("New user.");
            String name = io.ask("Enter user name: ");
            try {
                User user = new User(0, name);
                storage.add(user);
                StringBuilder sb = new StringBuilder();
                sb.append(LS);
                sb.append("User added. Id: ");
                sb.append(user.getId());
                sb.append(LS);
                System.out.println(sb.toString());
            } catch (Exception ex) {
                System.out.println("Error. User not added.");
                ex.printStackTrace();
            }
        }
    }
    /**
     * Класс ActionExit реализует сущность Выход из приложения.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 2018-08-03
     * @since 2018-08-03
     */
    private class ActionExit extends UserAction {
        /**
         * Конструктор.
         * @param action константа действия.
         */
        ActionExit(MenuActions action) {
            super(action);
        }
        /**
         * Выполняет действие, выбранное пользователем.
         * @param io объект ввода-вывода.
         * @param storage объект хранилища.
         */
        public void execute(IIO io, Storage storage) {
            System.out.println("Buy our program for 10$.");
            System.exit(0);
        }
    }
    /**
     * Класс ShowUsers реализует сущность Вывод пользователей хранилища.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 2018-08-03
     * @since 2017-04-23
     */
    private class ShowUsers extends UserAction {
        /**
         * Конструктор.
         * @param action константа действия.
         */
        ShowUsers(MenuActions action) {
            super(action);
        }
        /**
         * Выполняет действие, выбранное пользователем.
         * @param io объект ввода-вывода.
         * @param storage объект хранилища.
         */
        public void execute(IIO io, Storage storage) {
            StringBuilder sb = new StringBuilder();
            sb.append(LS);
            sb.append("Users in storage:");
            sb.append(LS);
            try {
                for (User user : storage.read(new User())) {
                    sb.append(user.toString());
                    sb.append(LS);
                }
                System.out.println(sb.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
