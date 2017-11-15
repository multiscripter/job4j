package ru.job4j.crud;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Set;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс UsersDB реализует сущность Драйвер бд.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-11-05
 */
class UsersDB {
    /**
     * Логгер.
     */
    private final Logger logger;
	/**
     * DataSource.
     */
	private DataSource ds;
	/**
     * Путь до файла.
     */
    private String path;
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
	UsersDB() {
        this.logger = LogManager.getLogger("UsersServlet");
        this.props = new Properties();
        try {
            this.path = new File(UsersDB.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
    		this.path = this.path.replaceFirst("^/(.:/)", "$1");
        } catch (URISyntaxException | NullPointerException ex) {
            this.logger.error("ERROR", ex);
        }
	}
    /**
     * Добавляет пользователя.
     * @param user новый пользователь.
     * @return true если пользователь добавлен в бд. Иначе false.
     * @throws SQLException ошибка SQL.
     */
    public boolean addUser(User user) throws SQLException {
        boolean result = false;
        ResultSet rs = null;
        try (Connection con = this.ds.getConnection(); Statement stmt = con.createStatement()) {
            String query = String.format("insert into %s (name, login, email, createDate) values ('%s', '%s', '%s', '%5$tY-%5$tm-%5$td')", this.tbl, user.getName(), user.getLogin(), user.getEmail(), user.getDate());
            if (stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS) > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs != null) {
                    rs.next();
                    user.setId(rs.getInt("id"));
                }
                result = true;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return result;
    }
    /**
     * Удаляет пользователя.
     * @param id идентификатор.
     * @return true если пользователь удалён из бд. Иначе false.
     * @throws SQLException ошибка SQL.
     */
    public boolean deleteUser(int id) throws SQLException {
        boolean result = false;
        try (Connection con = this.ds.getConnection(); Statement stmt = con.createStatement()) {
            String query = String.format("delete from %s where id = %d", this.tbl, id);
            if (stmt.executeUpdate(query) == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return result;
    }
    /**
     * Редактирует пользователя.
     * @param id идентификатор.
     * @param body тело PUT-запроса.
     * @return true если пользователь добавлен в бд. Иначе false.
     * @throws SQLException ошибка SQL.
     * @throws NoUserAttributeException нет атрибутов пользователя.
     */
    public boolean editUser(int id, String body) throws SQLException, NoUserAttributeException {
        Hashtable<String, String> attrs = new Hashtable<>();
        String[] strs = body.split("&");
        for (String str : strs) {
            String[] pair = str.split("=");
            attrs.put(pair[0], pair[1]);
        }
        if (attrs.size() == 0) {
            throw new NoUserAttributeException();
        }
        boolean result = false;
        try (Connection con = this.ds.getConnection(); Statement stmt = con.createStatement()) {
            String query = String.format("update %s set ", this.tbl);
            Set<String> keys = attrs.keySet();
            StringBuilder sb = new StringBuilder();
            sb.append(query);
            for (String key : keys) {
                if (key.equals("date")) {
                    sb.append(String.format("%s = '%2$tY-%2$tm-%2$td',", key, Date.valueOf(attrs.get(key))));
                } else {
                    sb.append(String.format("%s = '%s',", key, attrs.get(key)));
                }
            }
            sb = sb.deleteCharAt(sb.length() - 1);
            sb.append(String.format(" where id = %d", id));
            if (stmt.executeUpdate(sb.toString()) == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return result;
    }
    /**
     * Выполняет sql-скрипт.
     * @param localName локальное имя sql-файла.
     * @throws IOException ошибка ввода-вывода.
     * @throws SQLException ошибка SQL.
     */
    public void executeSql(String localName) throws IOException, SQLException {
        byte[] bytes = Files.readAllBytes(Paths.get(path + localName));
        String query = new String(bytes, "UTF-8");
        try (Connection con = this.ds.getConnection(); Statement stmt = con.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    /**
     * ПОлучает всех пользователей.
     * @param order имя столбца сортировки (по умолчанию id).
     * @param desc направление сортировки (asc, desc. По умолчанию asc).
     * @return Коллекция пользователей.
     * @throws SQLException ошибка SQL.
     */
    public LinkedList<User> getUsers(final String order, final boolean desc) throws SQLException {
        LinkedList<User> users = new LinkedList<>();
        String query = String.format("select * from %s order by %s %s", this.tbl, order.equals("") ? "id" : order, desc ? "desc" : "asc");
        try (Connection con = this.ds.getConnection(); PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            if (rs != null) {
                while (rs.next()) {
                    GregorianCalendar date = new GregorianCalendar();
                    date.setTime(rs.getDate("createDate"));
                    users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("login"), rs.getString("email"), date));
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return users;
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
     * @throws NamingException ошибка работы с именами сущностей.
     */
    public void setDbDriver() throws NamingException {
        try {
            InitialContext initC = new InitialContext();
            this.ds = (DataSource) initC.lookup("java:comp/env/jdbc/jpack2p9ch2task1Postgres");
            this.tbl = this.props.getProperty("tbl");
        } catch (NamingException ex) {
            throw new NamingException(ex.getLocalizedMessage());
        }
    }
}