package ru.job4j.xmlxslt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Класс SQLiteJDBCDriver реализует функционал работы с SQLite.
 * Для того, чтобы JVM нашла драйвер БД при выполнении программы нужно
 * закинуть jar-файл драйвера БД в папку jdk/jre/lib/ext.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-19
 * @since 2017-09-12
 */
class SQLiteJDBCDriver {
    /**
     * Получает объект соединения с бд.
     * jdbc:sqlite:local_filename.db
     * jdbc:sqlite:D:/absolute/file/name.db
     * jdbc:sqlite::memory
     * @param url путь к бд.
     * @throws java.sql.SQLException исключение базы данных.
     * @return объект соединения с бд.
     */
    public Connection getConnection(String url) throws SQLException {
        return DriverManager.getConnection(String.format("jdbc:sqlite:%s", url));
    }
}