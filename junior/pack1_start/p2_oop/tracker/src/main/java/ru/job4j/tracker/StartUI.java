package ru.job4j.tracker;

/**
 * Class StartUI реализует сущность пользовательского интрефэйса трэкера.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
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
    private String[] actions = {"Add new Item", "Show all items", "Edit item", "Delete item", "Find item by Id", "Find items by name", "Exit Program"};
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
        sb.append("Task added. Id: ");
        sb.append(id);
        sb.append("\n\n");
        System.out.println(sb.toString());
    }
    /**
     * Показывает все заявки в трэкере.
     */
    public void showAllTasks() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nTasks in tracker:");
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
        System.out.print("Buy our program for 10$.");
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
                case 0:
                    this.addNewItem();
                    break;
                case 1:
                    this.showAllTasks();
                    break;
                case 2:
                    this.editItem();
                    break;
                case 3:
                    this.deleteItem();
                    break;
                case 4:
                    this.findById();
                    break;
                case 5:
                    this.findByName();
                    break;
                case 6:
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