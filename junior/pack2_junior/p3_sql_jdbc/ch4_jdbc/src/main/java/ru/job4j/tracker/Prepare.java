package ru.job4j.tracker;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
/**
 * Класс Prepare выполняет подготовительные действия.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-24
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
     * Загружает ствойства соединения с бд.
     * @param localName локальное имя properties-файла.
     * @throws Exception исключение.
     */
    public void loadProperties(String localName) throws Exception {
        Path fName = Paths.get(path + localName);
        InputStream is = Files.newInputStream(fName);
        this.props.load(is);
    }
    /**
     * Загружает sql-скрипт.
     * @param localName локальное имя sql-файла.
     * @throws Exception исключение.
     */
    public void executeSql(String localName) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(path + localName));
        String query = new String(bytes, "UTF-8");
        Connection con = this.dbDriver.getConnection();
        if (con == null) {
            throw new Exception(this.dbDriver.getException());
        }
        Statement stmt = con.createStatement();
        stmt.execute(query);
        stmt.close();
        con.close();
    }
    /**
     * Получет драйвер бд.
     * @return dbDriver драйвер бд.
     */
    public PgSQLJDBCDriver getDbDriver() {
        return this.dbDriver;
    }
    /**
     * Получает свойства соединения с бд.
     * @return свойства соединения с бд.
     */
    public Properties getProperties() {
        return this.props;
    }
    /**
     * Устанавливает драйвер бд.
     * @param dbDriver драйвер бд.
     */
    public void setDbDriver(PgSQLJDBCDriver dbDriver) {
        this.dbDriver = dbDriver;
    }
}