package ru.job4j.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Класс JobsParser реализует сущность Парсер объявлений о работе на sql.ru.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-19
 */
class JobsParser {
    /**
     * Максимальное количество потоков.
     */
    private int maxThreads = 0;
    /**
     * Настройки задачи.
     */
    private final Properties props;
    /**
     * Коллекция ссылок объявлений вакансий.
     */
    private final JobsRefsList refsList;
    /**
     * Список потоков.
     */
    private final LinkedList<JobsPageList> threads;
    /**
     * Корструктор.
     * @param props настройки задачи.
     */
    JobsParser(final Properties props, int maxThreads) {
        this.props = props;
        this.maxThreads = maxThreads;
        this.refsList = new JobsRefsList();
        this.threads = new LinkedList<>();
    }
    /**
     * Получает кллекцию объявлений вакансий.
     * @return коллекция ссылок объявлений вакансий.
     */
    public JobsRefsList getRefsList() {
        return this.refsList;
    }
    /**
     * Парсит ресурс по url, указанному в properties-файле.
     * @throws IOException ошибка ввода-вывода.
     */
    public void parse() throws IOException {
        Document doc = Jsoup.connect(this.props.getProperty("url")).get();
        Elements elems = doc.select(this.props.getProperty("queryJobsLastPageNum"));
        int last = Integer.parseInt(elems.eq(0).text());
        try {
            for (int a = 1; a < 2/*last + 1*/; a++) {
                try {
                    JobsPageList pl = new JobsPageList(this, String.format("%s/%d", this.props.getProperty("url"), a));
                    this.threads.add(pl);
                    pl.start();
                    // Получить список объяв с первой страницы.
                    //doc = Jsoup.connect(String.format("%s/%d", this.props.getProperty("url"), a)).get();
                    // Получить заголовок объявы.
                    //elems = doc.select(this.props.getProperty("queryJobsListPage"));
                    //for (Element elem : elems) {
                    //    
                    //}
                } catch (OutOfMemoryError ex) {
                    sleep(100);
                }
            }
            for (JobsPageList t : this.threads) {
                t.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("---------- end ----------");
    }
}