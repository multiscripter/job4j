package ru.job4j.ui;

import java.util.ArrayList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.services.Storage;
/**
 * Класс ImportUser реализует сущность Запускатель пользовательского интрефейса хранилища.
 * Запуск из точки сборки: java -jar ./target/ch1_storages-1.0-jar-with-dependencies.jar
 * Запуск из точки сборки: java -cp ./target/classes/ ru.job4j.ui.ImportUser
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-03-18
 * @since 2017-04-19
 */
public class ImportUser {
    /**
     * Массив допустимых значений.
     */
    private ArrayList<Integer> range;
    /**
     * Объект ввода-вывода.
     */
    private IIO io;
    /**
     * Объект хранилища пользователй.
     */
    private Storage storage;
    /**
     * Конструктор.
     * @param io объект класса, реализующего интерфейс IIO.
     * @param ctxName локальное имя файла контекста.
     * @param beanId локальное имя файла контекста Spring.
     */
    public ImportUser(IIO io, String ctxName, String beanId) {
        this.io = io;
        ApplicationContext ctx = new ClassPathXmlApplicationContext(ctxName);
        this.storage = (Storage) ctx.getBean(beanId);
        this.range = new ArrayList<>(MenuActions.values().length);
        for (int a = 0; a < MenuActions.values().length; a++) {
            this.range.add(a);
        }
    }
    /**
     * Инициализирует трэкер и интерфэйс пользователя.
     */
    public void init() {
        Menu menu = new Menu(this.io, this.storage);
        menu.fillActions();
        System.out.println("User storage is on-line.");
        do {
            menu.show();
            menu.select(this.io.ask("Select: ", this.range.stream().mapToInt(i -> i).toArray()));
        } while (!"y".equals(this.io.ask("Exit? y|n: ")));
        System.out.println("Press CTRL+C to stop.");
    }
    /**
     * Точка входа в программу.
     * @param args массив аргументов командной строки.
     */
    public static void main(String[] args) {
        IIO io = new ValidateIO();
        new ImportUser(io, "spring-context.xml", "storageDBMS").init();
    }
}