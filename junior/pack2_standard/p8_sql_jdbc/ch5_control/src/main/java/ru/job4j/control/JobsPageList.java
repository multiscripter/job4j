package ru.job4j.control;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Класс JobsPageList реализует сущность Коллекция ссылок на страницы объявлений со страницы списка страниц.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-23
 */
class JobsPageList extends Thread {
    /**
     * Объект парсера.
     */
    private final JobsParser parser;
    /**
     * Коллекция ссылок на страницы объявлений вакансий.
     */
    private final HashSet<String> refs;
    /**
     * Ссылка на страницу списка.
     */
    private final url;
    /**
     * Конструктор.
     * @param parser парсер.
     * @param url ссылка на страницу списка.
     */
    JobsPageList(final JobsParser parser, final String url) {
        this.parser = parser;
        this.refs = new HashSet<>();
        this.url = url;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        Document doc = Jsoup.connect(this.url).get();
        Elements elems = doc.select(this.props.getProperty("queryJobsListPage"));
        JobsRefsList jrl = this.parser.getRefsList();
        try {
            for (Element elem : elems) {
                while (!jrl.add(elem)) {
                    sleep(10);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}