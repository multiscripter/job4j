package ru.job4j.monitsync;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Класс ParallelSearch реализует параллельный поиск текста в файлах с указанными расширениями, начиная с указанной папки. Использован пул потоков, размер которого равен количеству доступных системе процессоров.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-08-03
 */
class ParallelSearch {
    /**
     * Коллекция папок.
     */
    private HashSet<File> dirs;
    /**
     * Коллекция файлов.
     */
    private LinkedList<File> files;
    /**
     * Строка поиска.
     */
    private String text;
    /**
     * Коллекция расширений файлов, в которых производится поиск.
     */
    private List<String> exts;
    /**
     * Корневая папка поиска.
     */
    private File rootDir;
    /**
     * Коллекция трэдов.
     */
    private ArrayList<ThreadSearch> threads;
    /**
     * Пул трэдов.
     */
    private ExecutorService pool;
    /**
     * Объект блокировки (монитора).
     */
    private Object lock;
    /**
     * Конструктор.
     * @param root имя папки, в которой производится поиск.
     * @param text строка поиска.
     * @param exts список расширений файлов, в которых производится поиск.
     */
    ParallelSearch(String root, String text, List<String> exts) {
        this.rootDir = new File(root);
        if (!this.rootDir.isDirectory()) {
            throw new PathIsNotDirException(root);
        }
        if (text.trim().isEmpty()) {
            throw new SearchStringIsEmptyException();
        }
        this.dirs = new HashSet<>();
        this.dirs.add(this.rootDir.getAbsoluteFile());
        this.files = new LinkedList<>();
        this.threads = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.lock = this;
        this.text = text;
        this.exts = exts;
    }
    /**
     * Помещает в дерево все папки.
     */
    private void findFiles() {
        File[] files = this.rootDir.listFiles();
        ArrayList<File> filePaths = new ArrayList<>(Arrays.asList(files));
        File file;
        String ext;
        for (int a = 0; a < filePaths.size(); a++) {
            file = filePaths.get(a);
            if (Files.isSymbolicLink(file.toPath())) {
                continue;
            }
            ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (file.isDirectory()) {
                if (!this.dirs.contains(file)) {
                    this.dirs.add(file);
                    files = file.listFiles();
                    filePaths.addAll(Arrays.asList(files));
                }
            } else if (file.isFile() && (this.exts.size() > 0 ? this.exts.contains(ext) : true)) {
                String path = file.getAbsolutePath();
                File f = new File(path);
                this.files.add(f);
                this.threads.add(new ThreadSearch(f, this.text));
            }
        }
    }
    /**
     * Ищет в файлах совпадения со строкой поиска.
     */
    private void findMatches() {
        try {
            ArrayList<Future> futureList = new ArrayList<>();
            for (ThreadSearch t : this.threads) {
                futureList.add(this.pool.submit(t));
            }
            for (Future item : futureList) {
                item.get();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Удаляет файл из коллекции.
     * @param file объект удаляемого файла.
     */
    private void remove(File file) {
        synchronized (this.lock) {
            this.files.remove(file);
        }
    }
    /**
     * Возвращает результаты поиска строки в файлах с указанным расширением.
     * @return список имён файлов, в которых есть совпаденияме со строкой поиска.
     */
    public List<String> result() {
        this.findFiles();
        this.findMatches();
        LinkedList<String> list = new LinkedList<>();
        for (File file :  this.files) {
            list.add(file.toString());
        }
        return (List) list;
    }
    /**
     * Класс ThreadSearch реализует сущность "Поиск строки в файле в отдельном потоке".
     *
     * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
     * @version 1
     * @since 2017-07-31
     */
    class ThreadSearch extends Thread {
        /**
         * Файл, в котором производится поиск текста.
         */
        private File file;
        /**
         * Строка поиска.
         */
        private String text;
        /**
         * Конструктор.
         * @param file файл, в котором производится поиск текста.
         * @param text строка поиска.
         */
        ThreadSearch(File file, String text) {
            this.file = file;
            this.text = text;
        }
        /**
         * Переопределёный метод.
         */
        @Override
        public void run() {
            try {
                byte[] bytes = Files.readAllBytes(this.file.toPath());
                InputStreamReader isr = new InputStreamReader(new FileInputStream(this.file.getAbsolutePath()));
                Pattern p = Pattern.compile(this.text);
                Matcher m = p.matcher(new String(bytes, isr.getEncoding()));
                if (!m.find()) {
                    ParallelSearch.this.remove(this.file);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                Thread.currentThread().interrupt();
            }
        }
    }
}
/**
 * Класс PathIsNotDirException реализует исключение "Указанный путь не является папкой".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-31
 */
class PathIsNotDirException extends RuntimeException {
    /**
     * Конструктор.
     * @param path путь до папки.
     */
    PathIsNotDirException(String path) {
        super(String.format("Specified path: %s is not a directory.", path));
    }
}
/**
 * Класс SearchStringIsEmptyException реализует исключение "Строка поиска пуста".
 *
 * @author Goureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-31
 */
class SearchStringIsEmptyException extends RuntimeException {
    /**
     * Конструктор.
     */
    SearchStringIsEmptyException() {
        super(String.format("Search string is empty."));
    }
}
