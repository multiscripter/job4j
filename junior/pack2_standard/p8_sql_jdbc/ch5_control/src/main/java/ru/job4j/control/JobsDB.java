package ru.job4j.control;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Properties;
/**
 * Класс JobsDB реализует класс работы с бд.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-29
 */
class JobsDB {
    /**
     * Драйвер бд.
     */
    private PgSQLJDBCDriver dbDriver;
    /**
     * Путь до файла.
     */
    private final String path;
    /**
     * Свойства настроек бд.
     */
    private final Properties props;
    /**
     * Таблица в бд.
     */
    private String tbl;
    /**
     * Конструктор.
     */
    JobsDB() {
        this.path = String.format("%s../../../../../src/main/resources/", PgSQLJDBCDriver.class.getResource(".").getPath().replaceFirst("^/(.:/)", "$1"));
        this.props = new Properties();
    }
    /**
     * Возвращает из бд количество предложений о работе.
     * @return количество предложений о работе.
     */
    public int countOffers() {
        return 0;
    }
    /**
     * Удаляет записи из бд.
     * @param offerList список объявлений.
     * @return количество затронутых записей.
     * @throws SQLException ошибка SQL.
     */
    public int delete(JobOfferList offerList) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        int[] affectedRows = null;
        try {
            con = this.dbDriver.getConnection();
            String query = String.format("delete from %s where author = ? and pubdate = ? and title = ? and url = ?", this.tbl);
            pstmt = con.prepareStatement(query);
            con.setAutoCommit(false);
            Iterator<JobOffer> iter = offerList.iterator();
            JobOffer jo;
            while (iter.hasNext()) {
                jo = iter.next();
                pstmt.setString(1, jo.getAuthor());
                pstmt.setLong(2, jo.getDate());
                pstmt.setString(3, jo.getTitle());
                pstmt.setString(4, jo.getUrl());
                pstmt.addBatch();
            }
            affectedRows = pstmt.executeBatch();
            con.commit();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return affectedRows.length;
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
        Connection con = this.dbDriver.getConnection();
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
     * Получить коллекцию объявлений из бд.
     * @return коллекцию объявлений из бд.
     * @throws SQLException ошибка SQL.
     */
    public JobOfferList getJobOfferList() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        JobOfferList offerList = new JobOfferList();
        try {
            con = this.dbDriver.getConnection();
            String query = String.format("select * from %s", this.tbl);
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    offerList.add(new JobOffer(rs.getString("author"), rs.getLong("pubdate"), rs.getString("jobtext"), rs.getString("title"), rs.getString("url")));
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return offerList;
    }
    /**
     * Вставляет записи в бд.
     * @param offerList список объявлений.
     * @return количество вставленных записей.
     * @throws SQLException ошибка SQL.
     */
    public int insert(JobOfferList offerList) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        int[] affectedRows = null;
        try {
            con = this.dbDriver.getConnection();
            String query = String.format("insert into %s (author, pubdate, title, jobtext, url) values (?, ?, ?, ?, ?)", this.tbl);
            pstmt = con.prepareStatement(query);
            con.setAutoCommit(false);
            Iterator<JobOffer> iter = offerList.iterator();
            JobOffer jo;
            while (iter.hasNext()) {
                jo = iter.next();
                pstmt.setString(1, jo.getAuthor());
                pstmt.setLong(2, jo.getDate());
                pstmt.setString(3, jo.getTitle());
                pstmt.setString(4, jo.getText());
                pstmt.setString(5, jo.getUrl());
                pstmt.addBatch();
            }
            affectedRows = pstmt.executeBatch();
            con.commit();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return affectedRows.length;
    }
    /**
     * Загружает свойства соединения с бд.
     * @param localName локальное имя properties-файла.
     * @throws IOException ошибка ввода-вывода.
     */
    public void loadProperties(String localName) throws IOException {
        Path fName = Paths.get(path + localName);
        InputStream is = Files.newInputStream(fName);
        this.props.load(is);
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
        this.tbl = this.props.getProperty("tbl");
    }
}