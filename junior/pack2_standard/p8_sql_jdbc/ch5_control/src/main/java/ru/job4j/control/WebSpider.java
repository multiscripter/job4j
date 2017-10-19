package ru.job4j.control;

import java.util.Properties;
/**
 * Класс WebSpider реализует сущность Вэб-паук.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-19
 */
class WebSpider {
    /**
     * Настройки задачи.
     */
    private final Properties props;
    /**
     * Корструктор.
     * @param props настройки задачи.
     */
    WebSpider(final Properties props) {
        this.props = props;
    }
}