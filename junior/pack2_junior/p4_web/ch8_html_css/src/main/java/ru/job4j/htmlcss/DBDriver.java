package ru.job4j.htmlcss;

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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
/**
 * Класс DBDriver реализует сущность Драйвер бд.
 * Использован apache tomcat database connection pool.
 * Для конфигурирования пула соединений с бд не используется JNDI.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-26
 * @since 2017-11-05
 */
class DBDriver {
    /**
     * Класс Синглетона.
     */
    private static DBDriver instance;
    /**
     * Соединение с бд.
     */
    private Connection con;
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
     * Конструктор.
     * @throws ClassNotFoundException класс не найден.
     * @throws IllegalAccessException незаконный доступ.
     * @throws InstantiationException исключение создания экземпляра.
     * @throws URISyntaxException исключение синтакса URI.
     * @throws IOException исключение ввода-вывода.
     * @throws SQLException исключение SQL.
     */
	private DBDriver() throws ClassNotFoundException, IllegalAccessException, InstantiationException, URISyntaxException, IOException, SQLException {
        this.logger = LogManager.getLogger("DBDriver");
        this.props = new Properties();
        Class.forName("org.postgresql.Driver").newInstance(); //load driver
        this.path = new File(DBDriver.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath() + "/";
        this.path = this.path.replaceFirst("^/(.:/)", "$1");
        this.loadProperties("db.properties");
        this.setDbDriver();
        this.setConnection();
	}
	/**
     * Выполняет вудуеу sql-запрос.
     * @param query sql-запрос.
     * @return количество записей, затронутых запросом.
     * @throws SQLException исключение SQL.
     */
	public int delete(String query) throws SQLException {
        if (this.con == null) {
			this.setConnection();
    	}
    	int affected;
		try (Statement stmt = this.con.createStatement()) {
			affected = stmt.executeUpdate(query);
		} catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return affected;
	}
    /**
     * Выполняет sql-запрос.
     * @param query sql-запрос.
     * @throws SQLException исключение SQL.
     */
    public void executeSql(String query) throws SQLException {
    	if (this.con == null) {
			this.setConnection();
    	}
    	try (Statement stmt = this.con.createStatement()) {
			stmt.execute(query);
    	} catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    /**
     * Выполняет sql-скрипт.
     * @param localName локальное имя sql-файла.
     * @throws IOException исключение ввода-вывода.
     * @throws SQLException исключение SQL.
     */
    public void executeSqlScript(String localName) throws IOException, SQLException {
        if (this.con == null) {
			this.setConnection();
    	}
        byte[] bytes = Files.readAllBytes(Paths.get(this.path + localName));
        String query = new String(bytes, "UTF-8");
        try (Statement stmt = this.con.createStatement()) {
            stmt.execute(query);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    /**
     * Получает псенглетон.
     * @return сенглетон.
     * @throws ClassNotFoundException класс не найден.
     * @throws IllegalAccessException незаконный доступ.
     * @throws InstantiationException исключение создания экземпляра.
     * @throws URISyntaxException исключение синтакса URI.
     * @throws IOException исключение ввода-вывода.
     * @throws SQLException исключение SQL.
     */
    public static DBDriver getInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException, URISyntaxException, IOException, SQLException {
        if (DBDriver.instance == null) {
            DBDriver.instance = new DBDriver();
        }
        return DBDriver.instance;
    }
    /**
     * Выполняет insert sql-запрос.
     * @param query sql-запрос.
     * @return карту с результатом запроса к бд.
     * @throws SQLException исключение SQL.
     */
    public HashMap<String, String> insert(String query) throws SQLException {
    	if (this.con == null) {
			this.setConnection();
    	}
    	HashMap<String, String> entry = new HashMap<>();
    	try (Statement stmt = this.con.createStatement()) {
			if (stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS) > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
                if (rs != null) {
                    rs.next();
                    ResultSetMetaData rsmd = rs.getMetaData();
                	entry.put(rsmd.getColumnName(1), Integer.toString(rs.getInt(rsmd.getColumnName(1))));
                }
			}
    	} catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return entry;
	}
	/**
     * Загружает свойства соединения с бд.
     * @param localName локальное имя properties-файла.
     * @throws IOException исключение ввода-вывода.
     */
    private void loadProperties(String localName) throws IOException {
        Path fName = Paths.get(this.path + localName);
        InputStream is = Files.newInputStream(fName);
        this.props.load(is);
    }
    /**
     * Выполняет select sql-запрос.
     * @param query sql-запрос.
     * @return карту с результатом запроса к бд.
     * @throws SQLException исключение SQL.
     */
    public LinkedList<HashMap<String, String>> select(String query) throws SQLException {
    	if (this.con == null) {
			this.setConnection();
    	}
    	LinkedList<HashMap<String, String>> rl = new LinkedList<>();
    	try (PreparedStatement pstmt = this.con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
        	if (rs != null) {
            	ResultSetMetaData rsmd = rs.getMetaData();
            	while (rs.next()) {
            		HashMap<String, String> entry = new HashMap<>();
            		for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
	            		entry.put(rsmd.getColumnName(a), rs.getString(a));
	            	}
            		rl.add(entry);
            	}
        	}
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return rl;
    }
    /**
     * Устанавливает соединение с бд.
     * @throws SQLException исключение SQL.
     */
    private void setConnection() throws SQLException {
    	try {
    		this.con = this.ds.getConnection();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    /**
     * Устанавливает драйвер бд.
     */
    private void setDbDriver() {
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
        }
    }
    /**
     * Выполняет update sql-запрос.
     * @param query sql-запрос.
     * @return количество записей, затронутых запросом.
     * @throws SQLException исключение SQL.
     */
    public int update(String query) throws SQLException {
    	return this.delete(query);
    }
}