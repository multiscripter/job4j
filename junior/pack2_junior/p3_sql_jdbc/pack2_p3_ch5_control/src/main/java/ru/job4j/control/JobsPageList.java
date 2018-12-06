package ru.job4j.control;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
     * Названия месяцев.
     */
    private final String[] months = {"янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"};
    /**
     * Дата в миллисекундах в прошлом, позже которой объявления будут считаться устаревшими.
     */
    private final long old;
    /**
     * Количество устаревших объявлений.
     */
    private int outdated;
    /**
     * Объект парсера.
     */
    private final JobsParser parser;
    /**
     * Настройки задачи.
     */
    private final Properties props;
    /**
     * Ссылка на страницу списка.
     */
    private final String url;
    /**
     * Конструктор.
     * @param parser парсер.
     * @param props настройки задачи.
     * @param url ссылка на страницу списка.
     */
    JobsPageList(final JobsParser parser, final Properties props, final String url) {
        this.parser = parser;
        this.props = props;
        this.url = url;
        GregorianCalendar date = new GregorianCalendar();
        long period = (long) Integer.parseInt(this.props.getProperty("days")) * 86400 * 1000;
        date.setTimeInMillis(date.getTimeInMillis() - period);
        this.old = date.getTimeInMillis();
        this.outdated = 0;
    }
    /**
     * Получает время публикации предложения о работе в миллисекундах.
     * @param footText текст из футера, содержащий строку с датой публикации.
     * @return время публикации предложения о работе в миллисекундах.
     */
    private long getOfferTimeInMillis(String footText) {
        footText = footText.substring(0, footText.indexOf('['));
        String[] strArr = footText.split(",");
        strArr[0] = strArr[0].trim();
        String[] strTime = strArr[1].split(":");
        strTime[0] = strTime[0].trim();
        strTime[1] = strTime[1].substring(0, 2);
        GregorianCalendar date = new GregorianCalendar();
        if (strArr[0].equals("вчера")) {
            date.setTimeInMillis(date.getTimeInMillis() - 86400);
        } else if (!strArr[0].equals("сегодня")) {
            String[] strDate = strArr[0].split(" ");
            strDate[1] = strDate[1].trim();
            for (int a = 0; a < this.months.length; a++) {
                if (strDate[1].equals(months[a])) {
                   date.set(GregorianCalendar.MONTH, a);
                   break;
                }
            }
            date.set(GregorianCalendar.YEAR, Integer.parseInt(strDate[2]) + 2000);
            date.set(GregorianCalendar.DATE, Integer.parseInt(strDate[0]));
        }
        date.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(strTime[0]));
        date.set(GregorianCalendar.MINUTE, Integer.parseInt(strTime[1]));
        date.set(GregorianCalendar.SECOND, 0);
        // По умолчанию миллисекунды могут быть случаным числом, хоть в api и написано Default Value 0.
        date.set(GregorianCalendar.MILLISECOND, 0);
        return date.getTimeInMillis();
    }
    /**
     * Получает предложение о работе.
     * @param url ссылка на страницу первого объявления со страницы списка.
     * @param title заголовок темы объявления.
     * @return JobOffer объявление о вакансии.
     * @throws IOException ошибка ввода-вывода.
     */
    private JobOffer getJobOffer(String url, String title) throws IOException {
        JobOffer jo = null;
        Document doc = Jsoup.connect(url).get();
        Elements elems = doc.select(this.props.getProperty("queryGetOfferText"));
        String text = elems.eq(1).text();
        Pattern pattern = Pattern.compile("(java|[Дд][Жж][Аа][Вв][Аа])(?![\\s|-]*(script|[Сс][Кк][Рр][Ии][Пп][Тт]))", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            elems = doc.select(this.props.getProperty("queryGetAuthor"));
            String author = elems.eq(0).text();
            long pubTimeInMillis = this.getOfferTimeInMillis(doc.select(this.props.getProperty("queryGetOfferFooter")).eq(0).text());
            if (pubTimeInMillis > this.old) {
                jo = new JobOffer(author, pubTimeInMillis, text, title, url);
            } else {
                this.outdated++;
            }
        }
        return jo;
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect(this.url).get();
            //this.parser.getLogger().error("url: " + this.url);
            Elements elems = doc.select(this.props.getProperty("queryJobsListPage"));
            JobOfferList jol = this.parser.getJobOfferList();
            for (Element elem : elems) {
                JobOffer jo = this.getJobOffer(elem.attr("href"), elem.text());
                if (jo != null) {
                    while (!jol.add(jo)) {
                        sleep(10);
                    }
                }
            }
            if (elems.size() == this.outdated) {
                this.parser.setOutdated();
            }
            Thread.currentThread().interrupt();
        } catch (IOException | InterruptedException ex) {
            this.parser.getLogger().error("ERROR", ex);
        }
    }
}
