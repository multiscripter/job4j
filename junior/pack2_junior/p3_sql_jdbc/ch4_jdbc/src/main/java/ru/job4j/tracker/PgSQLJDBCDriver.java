package ru.job4j.tracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
/**
 * Класс PgSQLJDBCDriver реализует функционал работы с PostgreSQL.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-25
 * @since 2017-09-12
 */
public class PgSQLJDBCDriver {
    /**
     * Имя бд.
     */
    private String db;
    /**
     * Исключение.
     */
    private Exception ex;
    /**
     * Пароль пользователя бд.
     */
    private String pass = "";
    /**
     * Номер порта подключения к субд.
     */
    private int port;
    /**
     * Свойства настроек бд.
     */
    private Properties props;
    /**
     * Протокол подключения к бд.
     */
    private String protocol;
    /**
     * Источник бд.
     */
    private String src;
    /**
     * Пользователь бд.
     */
    private String user;
    /**
     * Конструктор.
     * @param props свойства соединения с бд.
     */
    public PgSQLJDBCDriver(final Properties props) {
        this.props = props;
    }
    /**
     * Получает объект соединения с бд.
     * @return объект соединения с бд.
     */
    private Connection getCon() {
        this.ex = null;
        Connection con = null;
        StringBuilder url =  new StringBuilder();
        try {
            url.append("jdbc:");
            url.append(this.protocol);
            url.append(":");
            if (this.src != null) {
                url.append("//");
                url.append(this.src);
            }
            if (this.port != 0) {
                url.append(":");
                url.append(this.port);
            }
            if (this.src != null) {
                url.append("/");
            }
            if (this.db != null) {
                url.append(this.db);
            }
            con = DriverManager.getConnection(url.toString(), this.user, this.pass);
        } catch (Exception ex) {
            String msg = String.format("Error connecting to db with url: %s, user: %s with%s password.\n", url.toString(), this.user, this.pass.equals("") ? "out" : "");
            System.err.print(msg);
            System.err.println(ex.getMessage());
            this.ex = ex;
        }
        return con;
    }
    /**
     * Получает объект соединения с бд.
     * @return объект соединения с бд.
     */
    private Connection getConnect() {
        Connection con = this.getCon();
        if (con == null) {
            this.setup();
            this.setPort(0);
            con = this.getCon();
        }
        if (con == null) {
            this.setup();
            this.setSrc(null);
            this.setPort(0);
            con = this.getCon();
        }
        if (con != null) {
            this.saveConfig();
        }
        return con;
    }
    /**
     * Получает объект соединения с бд.
     * @return объект соединения с бд.
     * @throws Exception исключение.
     */
    public Connection getConnection() throws Exception {
        Connection con = this.getConnect();
        if (con == null) {
            this.props.setProperty("pass", "");
            this.setup();
            con = this.getConnect();
        }
        return con;
    }
    /**
     * Получает имя бд.
     * @return имя бд.
     */
    public String getDB() {
        return this.db;
    }
    /**
     * Получает исключение.
     * @return исключение.
     */
    public Exception getException() {
        return this.ex;
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
     * Получает хост бд.
     * @return хост бд.
     */
    public String getSrc() {
        return this.src;
    }
    /**
     * Получает пользователя бд.
     * @return пользователь бд.
     */
    public String getUser() {
        return this.user;
    }
    /**
     * Сохраняет рабочую конфигурацию совйств соединения с бд.
     */
    private void saveConfig() {
        this.props.setProperty("protocol", this.protocol);
        this.props.setProperty("src", this.src);
        this.props.setProperty("port", Integer.toString(this.port));
        this.props.setProperty("pass", this.pass);
    }
    /**
     * Устанавливает имя бд.
     * @param db имя бд.
     */
    public void setDB(String db) {
        this.db = db;
    }
    /**
     * Устанавливает свойства драйвера.
     */
    public void setup() {
        this.setProtocol(this.props.getProperty("protocol"));
        this.setSrc(this.props.getProperty("src"));
        this.setPort(Integer.parseInt(this.props.getProperty("port")));
        this.setUser(this.props.getProperty("user"));
        this.setPass(this.props.getProperty("pass"));
        this.setDB(this.props.getProperty("db"));
    }
    /**
     * Устанавливает номер порта подключения к субд.
     * @param port номер порта подключения к субд.
     */
    public void setPort(int port) {
        this.port = port;
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