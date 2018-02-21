package tasks.lift;

import java.util.Scanner;
/**
 * Класс ConsoleIO реализует сущность Ввод из консоли и вывод в консоль.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-22
 */
class ConsoleIO extends AbstractLiftIO {
    /**
     * Объект класса Scanner.
     */
    private final Scanner scanner;
    /**
     * Конструктор.
     */
    ConsoleIO() {
        this.scanner = new Scanner(System.in);
    }
    /**
     * Читает данные из консоли.
     * @return прочитанные данные.
     */
    public String read() {
        return scanner.nextLine();
    }
    /**
     * Отправляет сообщение в источник вывода.
     * @param message сообщение, отправляемое в источник вывода.
     */
    public void write(final String message) {
        System.out.println(message);
    }
}