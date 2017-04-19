package ru.job4j.tracker;

/**
 * Class StartUI реализует сущность пользовательского интрефэйса трэкера.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-04-19
 */
public class StartUI {
    /**
     * Объект ввода.
     */
    private Input input;
    /**
     * Массив действий.
     */
    private String[] actions;
    /**
     * Объект трэкера заявок.
     */
    private Tracker tracker;
    /**
     * Конструктор.
     * @param input объект класса, реализующего интерфэйс Input.
     */
    public StartUI(Input input) {
        this.input = input;
        this.actions = new String[7];
        this.actions[MenuActions.ADD] = "Add new Item";
        this.actions[MenuActions.SHOW] = "Show all items";
        this.actions[MenuActions.EDIT] = "Edit item";
        this.actions[MenuActions.DELETE] = "Delete item";
        this.actions[MenuActions.FINDBYID] = "Find item by Id";
        this.actions[MenuActions.FINDBYNAME] = "Find items by name";
        this.actions[MenuActions.EXIT] = "Exit program";
    }
    /**
     * Реализует действия по добавлению новой заявки в трэкер.
     */
    public void addNewItem() {
        System.out.println("\nNew task.");
        String name = this.input.ask("Enter user name: ");
        String desc = this.input.ask("Enter task description: ");
        String id = this.tracker.generateId();
        tracker.add(new Item(id, name, desc));
        StringBuilder sb = new StringBuilder();
        sb.append("\nTask added. Id: ");
        sb.append(id);
        sb.append("\n");
        System.out.println(sb.toString());
    }
    /**
     * Показывает все заявки в трэкере.
     */
    public void showAllTasks() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nTasks in tracker:\n");
        for (Item item : this.tracker.getAll()) {
            sb.append(item.toString());
            sb.append("\n");
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }
    /**
     * Показывает сообщение при выходе из программы.
     */
    public void showExitMsg() {
        System.out.println("Buy our program for 10$.");
    }
    /**
     * Реализует действия по изменению заявки в трэкере.
     */
    public void editItem() {
        System.out.println("\nEdit task.");
        String id = this.input.ask("Enter task id: ");
        Item item = this.tracker.findById(id);
        if (item.isEmpty()) {
            return;
        }
        String name = this.input.ask("Enter new user name or just press Enter to go on: ");
        if (!name.isEmpty()) {
            item.setName(name);
        }
        String desc = this.input.ask("Enter new description or just press Enter to go on: ");
        if (!desc.isEmpty()) {
            item.setDesc(desc);
        }
        this.tracker.update(item);
        System.out.println("Task updated.\n");
    }
    /**
     * Реализует действия по удалению заявки из трэкера.
     */
    public void deleteItem() {
        System.out.println("\nDelete task.");
        String id = this.input.ask("Enter task id: ");
        this.tracker.delete(id);
        System.out.println("Task deleted.\n");
    }
    /**
     * Реализует действия по поиску заявки по идентификатору в трэкере.
     */
    public void findById() {
        System.out.println("\nFind task.");
        String id = this.input.ask("Enter task id: ");
        Item item = this.tracker.findById(id);
        if (item.isEmpty()) {
            System.out.println("Nothing found.\n");
        } else {
            System.out.println("Found:\n" + item.toString() + "\n");
        }
    }
    /**
     * Реализует действия по поиску заявки по имени пользователя в трэкере.
     */
    public void findByName() {
        System.out.println("\nFind task.");
        String name = this.input.ask("Enter user name: ");
        Item[] items = this.tracker.findByName(name);
        if (items.length == 0) {
            System.out.println("Nothing found.\n");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("\nFound:");
            sb.append("\n");
            for (Item item : items) {
                sb.append(item.toString());
                sb.append("\n");
            }
            sb.append("\n");
            System.out.println(sb.toString());
        }
    }
    /**
     * Инициализирует трэкер и интерфэйс пользователя.
     */
    public void init() {
        this.tracker = new Tracker();
        exit:
        while (true) {
            System.out.println("Please, enter the action number:");
            for (int a = 0; a < this.actions.length; a++) {
                System.out.println(a + ". " + this.actions[a]);
            }
            int entered = Integer.valueOf(this.input.ask("Select: "));
            switch (entered) {
                case MenuActions.ADD:
                    this.addNewItem();
                    break;
                case MenuActions.SHOW:
                    this.showAllTasks();
                    break;
                case MenuActions.EDIT:
                    this.editItem();
                    break;
                case MenuActions.DELETE:
                    this.deleteItem();
                    break;
                case MenuActions.FINDBYID:
                    this.findById();
                    break;
                case MenuActions.FINDBYNAME:
                    this.findByName();
                    break;
                case MenuActions.EXIT:
                    this.showExitMsg();
                    break exit;
                default:
                    break;
            }
        }
    }
    /**
     * Точка входа в программу.
     * @param args массив аргументов командной строки.
     */
    public static void main(String[] args) {
        Input input = new ConsoleInput();
        new StartUI(input).init();
    }
}
