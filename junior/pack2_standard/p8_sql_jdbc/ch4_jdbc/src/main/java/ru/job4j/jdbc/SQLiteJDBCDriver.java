package ru.job4j.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Класс SQLiteJDBCDriver реализует функционал работы с SQLite.
 * Для того, чтобы JVM нашла драйвер БД при выполнении программы нужно
 * закинуть jar-файл драйвера БД в папку jdk/jre/lib/ext.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-12
 */
class SQLiteJDBCDriver {
    /**
     * Получает объект соединения с бд.
     * jdbc:sqlite:local_filename.db
     * jdbc:sqlite:D:/absolute/file/name.db
     * jdbc:sqlite::memory
     * @param url путь к бд.
     * @return объект соединения с бд.
     */
    public Connection getConnection(String url) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", url));
        } catch (SQLException ex) {
            System.err.println("SQL Error.");
            ex.printStackTrace();
        }
        return conn;
    }
}