package ru.job4j.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Класс MenuTracker реализует сущность меню трэкера.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
 * @since 2017-04-23
 */
class MenuTracker {
    /**
     * Символ новой строки.
     */
    public static final String LS = System.getProperty("line.separator");
    /**
     * Объект ввода.
     */
    private Input input;
    /**
     * Объект трэкера заявок.
     */
    private  Tracker tracker;
    /**
     * Массив действий пользователя.
     */
    private ArrayList<IUserAction> actions = new ArrayList<>();
    /**
     * Конструктор.
     * @param input объект класса, реализующего интерфэйс Input.
     * @param tracker объект трэкера.
     */
    MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }
    /**
     * Инициализирует меню трэкера.
     */
    public void fillActions() {
        this.actions.add(new AddItem(MenuActions.ADD));
        //this.actions.add(this.new AddItem(MenuActions.ADD));
        this.actions.add(new MenuTracker.ShowItems(MenuActions.SHOW));
        this.actions.add(new EditItem(MenuActions.EDIT));
        this.actions.add(new DeleteItem(MenuActions.DELETE));
        this.actions.add(new FindItemById(MenuActions.FINDBYID));
        this.actions.add(new FindItemsByName(MenuActions.FINDBYNAME));
    }
    /**
     * Добавляет в массив пользовательских действие нвое действие.
     * @param action добавляемое действие.
     */
    public void addAction(UserAction action) {
        this.actions.add(action);
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param key идентификатор действия.
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }
    /**
     * Выводит меню трэкера.
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
     * Класс AddItem реализует сущность добавления заявки в трэкер.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-04-23
     */
    private class AddItem extends UserAction {
        /**
         * Конструктор.
         * @param action константа действия.
         */
        AddItem(MenuActions action) {
            super(action);
        }
        /**
         * Выполняет действие, выбранное пользователем.
         * @param input объект ввода.
         * @param tracker объект трэкера.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("");
            System.out.println("New task.");
            String name = input.ask("Enter user name: ");
            String desc = input.ask("Enter task description: ");
            try {
                Item item = new Item(name, desc);
                tracker.add(item);
                StringBuilder sb = new StringBuilder();
                sb.append(LS);
                sb.append("Task added. Id: ");
                sb.append(item.getId());
                sb.append(LS);
                System.out.println(sb.toString());
            } catch (SQLException ex) {
                System.out.println("Error. Task not added.");
                //ex.printStackTrace();
            }
        }
    }
    /**
     * Класс ShowItems реализует сущность печати заявок трэкера.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-04-23
     */
    private static class ShowItems extends UserAction {
        /**
         * Конструктор.
         * @param action константа действия.
         */
        ShowItems(MenuActions action) {
            super(action);
        }
        /**
         * Выполняет действие, выбранное пользователем.
         * @param input объект ввода.
         * @param tracker объект трэкера.
         */
        public void execute(Input input, Tracker tracker) {
            StringBuilder sb = new StringBuilder();
            sb.append(LS);
            sb.append("Tasks in tracker:");
            sb.append(LS);
            for (Item item : tracker.getAll()) {
                sb.append(item.toString());
                sb.append(LS);
            }
            System.out.println(sb.toString());
        }
    }
    /**
     * Класс DeleteItem реализует сущность удаления заявки из трэкера.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-04-23
     */
    private class DeleteItem extends UserAction {
        /**
         * Конструктор.
         * @param action константа действия.
         */
        DeleteItem(MenuActions action) {
            super(action);
        }
        /**
         * Выполняет действие, выбранное пользователем.
         * @param input объект ввода.
         * @param tracker объект трэкера.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("");
            System.out.println("Delete task.");
            String id = input.ask("Enter task id: ");
            try {
                if (tracker.delete(id)) {
                    System.out.println("Task deleted.");
                } else {
                    System.out.println("Nothing deleted.");
                }
            }  catch (SQLException ex) {
                System.out.printf("Error occured while deleting task with id: %d.", id);
            }
            System.out.println("");
        }
    }
    /**
     * Класс FindItemById реализует сущность поиска заявки по идентификатору.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-04-23
     */
    private class FindItemById extends UserAction {
        /**
         * Конструктор.
         * @param action константа действия.
         */
        FindItemById(MenuActions action) {
            super(action);
        }
        /**
         * Выполняет действие, выбранное пользователем.
         * @param input объект ввода.
         * @param tracker объект трэкера.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("");
            System.out.println("Find task.");
            String id = input.ask("Enter task id: ");
            Item item = tracker.findById(id);
            if (item.isEmpty()) {
                System.out.println("Nothing found.");
                System.out.println("");
            } else {
                System.out.println("");
                System.out.println("Found:");
                System.out.println(item.toString());
                System.out.println("");
            }
        }
    }
    /**
     * Класс FindItemsByName реализует сущность поиска заявок по имени.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-04-23
     */
    private class FindItemsByName extends UserAction {
        /**
         * Конструктор.
         * @param action константа действия.
         */
        FindItemsByName(MenuActions action) {
            super(action);
        }
        /**
         * Выполняет действие, выбранное пользователем.
         * @param input объект ввода.
         * @param tracker объект трэкера.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("");
            System.out.println("Find task.");
            String name = input.ask("Enter user name: ");
            Item[] items = tracker.findByName(name);
            if (items.length == 0) {
                System.out.println("Nothing found.");
                System.out.println("");
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(LS);
                sb.append("Found:");
                sb.append(LS);
                for (Item item : items) {
                    sb.append(item.toString());
                    sb.append(LS);
                }
                System.out.println(sb.toString());
            }
        }
    }
}
/**
 * Класс EditItem реализует сущность редактирования заявки в трэкере.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-23
 */
class EditItem extends UserAction {
    /**
     * Конструктор.
     * @param action константа действия.
     */
    EditItem(MenuActions action) {
        super(action);
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param input объект ввода.
     * @param tracker объект трэкера.
     */
    public void execute(Input input, Tracker tracker) {
        System.out.println("");
        System.out.println("Edit task.");
        String id = input.ask("Enter task id: ");
        Item item = tracker.findById(id);
        if (item.isEmpty()) {
            System.out.println("There is no task with this id.");
            System.out.println("");
            return;
        }
        String name = input.ask("Enter new user name or just press Enter to go on: ");
        if (!name.isEmpty()) {
            try {
                item.setName(name);
            } catch (SQLException ex) {
                System.out.println("SQL error occured while setting name.");
            }
        }
        String desc = input.ask("Enter new description or just press Enter to go on: ");
        if (!desc.isEmpty()) {
            try {
                item.setDesc(desc);
            } catch (SQLException ex) {
                System.out.println("SQL error occured while setting description.");
            }
        }
        tracker.update(item);
        System.out.println("Task updated.");
        System.out.println("");
    }
}
