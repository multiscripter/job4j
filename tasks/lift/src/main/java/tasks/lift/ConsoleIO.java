package tasks.lift;

import java.util.Scanner;
/**
 * ConsoleIO реализует сущность Ввод из консоли и вывод в консоль.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-27
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
     * Закрывает сущность ввода-вывода.
     */
    @Override
    public void close() {
        this.scanner.close();
    }
    /**
     * Проверяет если ли данные для чтения.
     * @return true если есть данные для чтения. Иначе false.
     */
    @Override
    public boolean hasData() {
        return this.scanner.hasNextLine();
    }
    /**
     * Читает данные из консоли.
     * @return прочитанные данные.
     */
    @Override
    public String read() {
        return scanner.nextLine();
    }
    /**
     * Отправляет сообщение в источник вывода.
     * @param message сообщение, отправляемое в источник вывода.
     */
    @Override
    public void write(final String message) {
        System.out.println(message);
    }
}