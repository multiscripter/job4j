package ru.job4j.tracker;

/**
 * Класс MenuTracker реализует сущность меню трэкера.
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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
    private UserAction[] actions = new UserAction[6];
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
        this.actions[0] = new AddItem();
        //this.actions[0] = this.new AddItem();
        this.actions[1] = new MenuTracker.ShowItems();
        this.actions[2] = new EditItem();
        this.actions[3] = new DeleteItem();
        this.actions[4] = new FindItemById();
        this.actions[5] = new FindItemsByName();
    }
    /**
     * Выполняет действие, выбранное пользователем.
     * @param key идентификатор действия.
     */
    public void select(int key) {
        this.actions[key].execute(this.input, this.tracker);
    }
    /**
     * Выводит меню трэкера.
     */
    public void show() {
        System.out.println("Please, enter the action number:");
        for (UserAction action : this.actions) {
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
    private class AddItem implements UserAction {
        /**
         * Возвращает идентификатор действия пользователя.
         * @return идентификатор действия.
         */
        public int key() {
            return 0;
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
            String id = tracker.generateId();
            tracker.add(new Item(id, name, desc));
            StringBuilder sb = new StringBuilder();
            sb.append(LS);
            sb.append("Task added. Id: ");
            sb.append(id);
            sb.append(LS);
            System.out.println(sb.toString());
        }
        /**
         * Возвращает строку с выполняемым действием.
         * @return идентификатор действия.
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Add new item");
        }
    }
    /**
     * Класс ShowItems реализует сущность печати заявок трэкера.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-04-23
     */
    private static class ShowItems implements UserAction {
        /**
         * Возвращает идентификатор действия пользователя.
         * @return идентификатор действия.
         */
        public int key() {
            return 1;
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
        /**
         * Возвращает строку с выполняемым действием.
         * @return идентификатор действия.
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Show all items");
        }
    }
    /**
     * Класс DeleteItem реализует сущность удаления заявки из трэкера.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-04-23
     */
    private class DeleteItem implements UserAction {
        /**
         * Возвращает идентификатор действия пользователя.
         * @return идентификатор действия.
         */
        public int key() {
            return 3;
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
            tracker.delete(id);
            System.out.println("Task deleted.");
            System.out.println("");
        }
        /**
         * Возвращает строку с выполняемым действием.
         * @return идентификатор действия.
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Delete item");
        }
    }
    /**
     * Класс FindItemById реализует сущность поиска заявки по идентификатору.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-04-23
     */
    private class FindItemById implements UserAction {
        /**
         * Возвращает идентификатор действия пользователя.
         * @return идентификатор действия.
         */
        public int key() {
            return 4;
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
        /**
         * Возвращает строку с выполняемым действием.
         * @return идентификатор действия.
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Find item by Id");
        }
    }
    /**
     * Класс FindItemsByName реализует сущность поиска заявок по имени.
     *
     * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-04-23
     */
    private class FindItemsByName implements UserAction {
        /**
         * Возвращает идентификатор действия пользователя.
         * @return идентификатор действия.
         */
        public int key() {
            return 5;
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
                sb.append(LS);
                System.out.println(sb.toString());
            }
        }
        /**
         * Возвращает строку с выполняемым действием.
         * @return идентификатор действия.
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Find items by name");
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
class EditItem implements UserAction {
    /**
     * Возвращает идентификатор действия пользователя.
     * @return идентификатор действия.
     */
    public int key() {
        return 2;
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
            return;
        }
        String name = input.ask("Enter new user name or just press Enter to go on: ");
        if (!name.isEmpty()) {
            item.setName(name);
        }
        String desc = input.ask("Enter new description or just press Enter to go on: ");
        if (!desc.isEmpty()) {
            item.setDesc(desc);
        }
        tracker.update(item);
        System.out.println("Task updated.");
        System.out.println("");
    }
    /**
     * Возвращает строку с выполняемым действием.
     * @return идентификатор действия.
     */
    public String info() {
        return String.format("%s. %s", this.key(), "Edit item");
    }
}
