package ru.job4j.analize;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
/**
 * Класс Analizy реализует сущность Анализатор лог-файлов сервера.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-04-22
 * @since 2019-04-22
 */
public class Analizy {
    /**
     * Диапазон недоступности сервера.
     */
    private String[] diapason;
    /**
     * Читатель буфера.
     */
    private BufferedReader reader;
    /**
     * Писатель в файл диапазона значений недоступности сервера.
     */
    private PrintWriter writer;
    /**
     * Анализирует строку, прочитанную из логфайла сервера.
     * @param line строка с записью сервера.
     */
    private void analize(String line) {
        if (line.length() > 0) {
            String[] pair = line.split(" ");
            if (pair[0].startsWith("4") || pair[0].startsWith("5")) {
                if (this.diapason == null) {
                    this.diapason = new String[2];
                }
                if (this.diapason[0] == null) {
                    this.diapason[0] = pair[1];
                }
            } else {
                if (this.diapason != null) {
                    this.diapason[1] = pair[1];
                    this.write();
                    this.diapason = null;
                }
            }
        }
    }
    /**
     * Закрывает открытые файлы.
     * @throws Exception исключение.
     */
    private void close() throws Exception {
        if (this.reader != null) {
            this.reader.close();
        }
        if (this.writer != null) {
            this.writer.close();
        }
    }
    /**
     * Открывает файл лога сервера на чтение.
     * @param source лог-файл сервера.
     * @param target имя файлс с диапазонами значений недоступности сервера.
     * @throws Exception исключение.
     */
    private void open(String source, String target) throws Exception {
        this.reader = new BufferedReader(new FileReader(source));
        this.writer = new PrintWriter(new FileOutputStream(target));
    }
    /**
     * Читает строки из лог-файла сервера.
     */
    private void read() {
        if (this.reader != null) {
            this.reader.lines().forEach((line) -> {
                this.analize(line.trim());
            });
        }
    }
    /**
     * Анализирует лог-файл сервера, находит диапазоны когда сервер не работал
     * и записывает резудбтат в файл.
     * @param source лог-файл сервера.
     * @param target файл с диапазонами
     * @throws Exception исключение.
     */
    public void unavailable(String source, String target) throws Exception {
        this.open(source, target);
        this.read();
        this.close();
    }
    /**
     * Записывает диапазон значений недоступности сервера в файл.
     */
    private void write() {
        this.writer.println(String.join(";", this.diapason));
    }
}