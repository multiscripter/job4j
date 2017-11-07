package ru.job4j.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
    private int maxThreads = 1;
    /**
     * Настройки задачи.
     */
    private final Properties props;
    /**
     * Коллекция ссылок объявлений вакансий.
     */
    private final JobOfferList offerList;
    /**
     * Список потоков.
     */
    private final LinkedList<JobsPageList> threads;
    /**
     * Логгер.
     */
    private final Logger logger;
    /**
     * Не слишком ли старые объявления.
     */
    private boolean outdated;
    /**
     * Корструктор.
     * @param props настройки задачи.
     * @param maxThreads максимальое число трэдов.
     */
    JobsParser(final Properties props, int maxThreads) {
        this.props = props;
        if (maxThreads > this.maxThreads) {
            this.maxThreads = maxThreads;
        }
        this.offerList = new JobOfferList();
        this.threads = new LinkedList<>();
        this.outdated = false;
        this.logger = LogManager.getLogger("JobsParser");
    }
    /**
     * Парсит ресурс по url, указанному в properties-файле.
     */
    public void begin() {
        try {
            this.parse();
            this.db();
        } catch (IOException | SQLException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Работает с бд.
     * @throws IOException ошибка ввода-вывода.
     * @throws SQLException ошибка SQL.
     */
    private void db() throws IOException, SQLException {
        JobsDB db = new JobsDB(logger);
        db.loadProperties("jpack2p8ch5task3db.properties");
        db.setDbDriver(new PgSQLJDBCDriver());
        db.executeSql("junior.pack2.p8.ch5.task3.sql");
        JobOfferList dbjol = db.getJobOfferList();
        if (dbjol.size() == 0) {
            db.insert(this.offerList);
        } else {
            this.offerList.removeEquals(dbjol);
            if (dbjol.size() > 0) {
                db.delete(dbjol);
            }
            if (this.offerList.size() > 0) {
                db.insert(this.offerList);
            }
        }
    }
    /**
     * Получает коллекцию объявлений вакансий.
     * @return коллекция объявлений вакансий.
     */
    public JobOfferList getJobOfferList() {
        return this.offerList;
    }
    /**
     * Получает логгер.
     * @return логгер.
     */
    public Logger getLogger() {
        return this.logger;
    }
    /**
     * Парсит ресурс по url, указанному в properties-файле.
     * @throws IOException ошибка ввода-вывода.
     */
    private void parse() throws IOException {
        Document doc = Jsoup.connect(this.props.getProperty("url")).get();
        Elements elems = doc.select(this.props.getProperty("queryJobsLastPageNum"));
        int last = Integer.parseInt(elems.eq(0).text());
        try {
            for (int a = 1; a < last + 1;) {
                for (int b = 0; b < this.maxThreads; b++) {
                    try {
                        JobsPageList pl = new JobsPageList(this, this.props, String.format("%s/%d", this.props.getProperty("url"), a++));
                        this.threads.add(pl);
                        pl.start();
                    } catch (OutOfMemoryError ex) {
                        Thread.currentThread().sleep(100);
                        b--;
                    }
                }
                while (this.threads.size() > 0) {
                    Iterator<JobsPageList> iter = this.threads.iterator();
                    JobsPageList pl = null;
                    while (iter.hasNext()) {
                        pl = iter.next();
                        if (pl.getState() != Thread.State.NEW) {
                            iter.remove();
                            pl.join();
                        }
                    }
                }
                if (this.outdated) {
                    break;
                }
            }
        } catch (InterruptedException ex) {
            this.logger.error("ERROR", ex);
        }
    }
    /**
     * Устанавливает признак старости в true.
     */
    public void setOutdated() {
        this.outdated = true;
    }
}