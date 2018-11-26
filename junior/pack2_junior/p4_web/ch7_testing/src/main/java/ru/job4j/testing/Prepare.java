package ru.job4j.testing;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
/**
 * Класс Prepare выполняет подготовительные действия.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-09-18
 */
class Prepare {
    /**
     * Драйвер бд.
     */
    private PgSQLJDBCDriver dbDriver;
    /**
     * Путь до файла.
     */
    private String path;
    /**
     * Свойства настроек бд.
     */
    private Properties props;
    /**
     * Конструктор без параметров.
     */
    Prepare() {
        this.path = PgSQLJDBCDriver.class.getResource(".").getPath().replaceFirst("^/(.:/)", "$1");
        this.path = String.format("%s../../../../../src/main/resources/", this.path);
        this.props = new Properties();
    }
    /**
     * Получет драйвер бд.
     * @return dbDriver драйвер бд.
     */
    public PgSQLJDBCDriver getDbDriver() {
        return this.dbDriver;
    }
    /**
     * Загружает ствойства соединения с бд.
     * @param localName локальное имя properties-файла.
     * @throws IOException ошибка ввода-вывода.
     */
    public void loadProperties(String localName) throws IOException {
        Path fName = Paths.get(path + localName);
        InputStream is = Files.newInputStream(fName);
        this.props.load(is);
    }
    /**
     * Загружает sql-скрипт.
     * @param localName локальное имя sql-файла.
     * @throws IOException ошибка ввода-вывода.
     * @throws SQLException ошибка SQL.
     */
    public void executeSql(String localName) throws IOException, SQLException {
        byte[] bytes = Files.readAllBytes(Paths.get(path + localName));
        String query = new String(bytes, "UTF-8");
        Connection con = dbDriver.getConnection();
        Statement stmt = con.createStatement();
        stmt.execute(query);
        stmt.close();
        con.close();
    }
    /**
     * Устанавливает драйвер бд.
     * @param dbDriver драйвер бд.
     */
    public void setDbDriver(PgSQLJDBCDriver dbDriver) {
        this.dbDriver = dbDriver;
        this.dbDriver.setProtocol(this.props.getProperty("protocol"));
        this.dbDriver.setSrc(this.props.getProperty("src"));
        this.dbDriver.setPort(Integer.parseInt(this.props.getProperty("port")));
        this.dbDriver.setUser(this.props.getProperty("user"));
        this.dbDriver.setPass(this.props.getProperty("pass"));
        this.dbDriver.setDB(this.props.getProperty("db"));
    }
}