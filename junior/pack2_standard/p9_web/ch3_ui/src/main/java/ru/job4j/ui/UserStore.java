package ru.job4j.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
/**
 * Класс UserStore реализует сущность Драйвер бд.
 * Использован apache tomcat database connection pool.
 * Для конфигурирования пула соединений с бд не используется JNDI.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
 * @since 2017-11-05
 */
@Singleton
class UserStore {
    /**
     * Логгер.
     */
    private final Logger logger;
	/**
     * DataSource.
     */
	private BasicDataSource ds;
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
    @Inject
	UserStore() {
        this.logger = LogManager.getLogger("UsersServlet");
        this.props = new Properties();
        try {
            this.path = new File(UserStore.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
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
        String query = String.format("insert into %s (name, login, email, createDate) values ('%s', '%s', '%s', '%5$tY-%5$tm-%5$td')", this.tbl, user.getName(), user.getLogin(), user.getEmail(), user.getDate());
        try (Connection con = this.ds.getConnection(); Statement stmt = con.createStatement()) {
            if (stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS) > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs != null) {
                    rs.next();
                    user.setId(rs.getInt("id"));
                }
                result = true;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
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
        String query = String.format("delete from %s where id = %d", this.tbl, id);
        try (Connection con = this.ds.getConnection(); Statement stmt = con.createStatement()) {
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
     * @param user новый пользователь.
     * @return true если пользователь обновлён в бд. Иначе false.
     * @throws SQLException ошибка SQL.
     */
    public boolean editUser(User user) throws SQLException {
        boolean result = false;
        String query = String.format("update %s set name='%s', login='%s', email='%s' where id=%d", this.tbl, user.getName(), user.getLogin(), user.getEmail(), user.getId());
        try (Connection con = this.ds.getConnection(); Statement stmt = con.createStatement()) {
            if (stmt.executeUpdate(query) == 1) {
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
     * Получает пользователя по идентификатору.
     * @param id идентификатор пользователя.
     * @return пользователь.
     * @throws SQLException ошибка SQL.
     */
    public User getUser(int id) throws SQLException {
        User user = null;
        String query = String.format("select * from %s where id = %d", this.tbl, id);
        try (Connection con = this.ds.getConnection(); PreparedStatement pstmt = con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            if (rs != null) {
                rs.next();
                GregorianCalendar date = new GregorianCalendar();
                date.setTime(rs.getDate("createDate"));
                user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("login"), rs.getString("email"), date);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return user;
    }
    /**
     * Получает всех пользователей.
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
     */
    public void setDbDriver() {
        if (this.ds == null) {
            StringBuilder str =  new StringBuilder();
            str.append("jdbc:");
            str.append(this.props.getProperty("protocol"));
            str.append("://");
            str.append(this.props.getProperty("src"));
            if (!this.props.getProperty("port").equals("0")) {
                str.append(":");
                str.append(this.props.getProperty("port"));
            }
            str.append("/");
            if (this.props.getProperty("db") != null) {
                str.append(this.props.getProperty("db"));
            }
            this.ds = new BasicDataSource();
            this.ds.setUrl(str.toString());
            this.ds.setUsername(this.props.getProperty("user"));
            this.ds.setPassword(this.props.getProperty("pass"));
            this.ds.setMinIdle(5);
            this.ds.setMaxIdle(10);
            this.ds.setMaxOpenPreparedStatements(100);
            this.tbl = this.props.getProperty("tbl");
        }
    }
}