package ru.job4j.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс PgSQLJDBCDriver реализует функционал работы с PostgreSQL.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-12
 */
class PgSQLJDBCDriver {
    /**
     * Имя бд.
     */
    private String db;
    /**
     * Источник бд.
     */
    private String src;
    /**
     * Пароль пользователя бд.
     */
    private String pass;
    /**
     * Номер порта подключения к субд.
     */
    private int port;
    /**
     * Протокол подключения к бд.
     */
    private String protocol;
    /**
     * Строка подключения.
     */
    private StringBuilder str =  new StringBuilder();
    /**
     * Пользователь бд.
     */
    private String user;
    /**
     * Получает объект соединения с бд.
     * @return объект соединения с бд.
     */
    public Connection getConnection() {
        Connection conn = null;
        try {
            str.append("jdbc:");
            str.append(this.protocol);
            str.append(this.src);
            conn = DriverManager.getConnection(str.toString());
        } catch (SQLException ex) {
            System.err.println("Ошибка SQL.");
            ex.printStackTrace();
        }
        return conn;
    }
    /**
     * Получает имя бд.
     * @return имя бд.
     */
    public String getDb() {
        return this.db;
    }
    /**
     * Получает хост бд.
     * @return хост бд.
     */
    public String getSrc() {
        return this.src;
    }
    /**
     * Получает номер порта подключения к субд.
     * @return номер порта подключения к субд.
     */
    public int getPort() {
        return this.port;
    }
    /**
     * Получает протокол подключения к субд.
     * @return протокол подключения к субд.
     */
    public String getProtocol() {
        return this.protocol;
    }
    /**
     * Получает пользователя бд.
     * @return пользователь бд.
     */
    public String getUser() {
        return this.user;
    }
    /**
     * Устанавливает имя бд.
     * @param db имя бд.
     */
    public void setDb(String db) {
        this.db = db;
    }
    /**
     * Устанавливает хост бд.
     * @param src хост бд.
     */
    public void setSrc(String src) {
        this.src = src;
    }
    /**
     * Устанавливает пароль пользователя бд.
     * @param pass пароль пользователя бд.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    /**
     * Устанавливает номер порта подключения к субд.
     * @param port номер порта подключения к субд.
     */
    public void setPort(int port) {
        this.port = port;
    }
    /**
     * Устанавливает протокол подключения к субд.
     * @param protocol протокол подключения к субд.
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    /**
     * Устанавливает пользователя бд.
     * @param user пользователь бд.
     */
    public void setUser(String user) {
        this.user = user;
    }
}