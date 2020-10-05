package ru.job4j.checking;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import ru.job4j.utils.PropertyLoader;
/**
 * Класс DBDriver используется в тестах для проверки классов, работающих с СУБД.
 * https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-05-15
 * @since 2018-03-07
 */
public class DBDriver {
    /**
     * Соединение с бд.
     */
	private Connection con;
    /**
     * Свойства настроек бд.
     */
    private final Properties props;
    /**
     * Конструктор.
     * @param path абсолютный путь к папке ресурсов.
     * @throws ClassNotFoundException класс не найден.
     * @throws IOException исключение ввода-вывода.
     * @throws IllegalAccessException исключение Незаконный доступ.
     * @throws InstantiationException исключение содания экземпляра.
     * @throws SQLException исключение SQL.
     */
	public DBDriver(String path) throws ClassNotFoundException, IOException, IllegalAccessException, InstantiationException, SQLException {
        //String dbmsName = new PropertyLoader(String.format("%s%s", this.path, "activeDBMS.properties")).getPropValue("name");
        this.props = new PropertyLoader(String.format("%s.properties", path)).getProperties();
        Class.forName(this.props.getProperty("driver")).newInstance(); //load driver
        this.setConnection();
    }
    /**
     * Закрывает соединение с СУБД.
     * @throws SQLException ошибка SQL.
     */
    public void close() throws SQLException {
        if (this.con != null && !this.con.isClosed()) {
            this.con.close();
        }
    }
    /**
     * Выполняет вудуеу sql-запрос.
     * @param query sql-запрос.
     * @return количество записей, затронутых запросом.
     * @throws SQLException исключение SQL.
     */
	public int delete(String query) throws SQLException {
        if (this.con == null || this.con.isClosed()) {
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
     * @param query строка с sql-запросом.
     * @return <code>true</code> if the first result is a <code>ResultSet</code>
     *         object; <code>false</code> if it is an update count or there are
     *         no results.
     * @throws SQLException исключение SQL.
     */
    public boolean executeSql(String query) throws SQLException {
    	if (this.con == null || this.con.isClosed()) {
			this.setConnection();
    	}
    	try (Statement stmt = this.con.createStatement()) {
			return stmt.execute(query);
    	} catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    /**
     * Выполняет набор инструкций из sql-скрипта.
     * @param name имя sql-файла.
     * @return массив, каждое значение которого является количеством строк,
     * затронутых инструкцией.
     * @throws IOException исключение ввода-вывода.
     * @throws NoSuchFileException исключение Нет такого файла.
     * @throws NullPointerException исключение Нулевой указатель.
     * @throws SQLException исключение SQL.
     * @throws URISyntaxException исключение синтаксиса URI.
     */
    public int[] executeSqlScript(String name) throws IOException, NoSuchFileException, NullPointerException, SQLException, URISyntaxException {
        int[] affectedRowsArr;
        if (this.con == null || this.con.isClosed()) {
			this.setConnection();
    	}
        byte[] bytes = Files.readAllBytes(Paths.get(name));
        String query = new String(bytes, "UTF-8");
        String[] commands = query.split(";");
        con.setAutoCommit(false);
        try (Statement stmt = this.con.createStatement()) {
            for (String command : commands) {
                stmt.addBatch(command);
            }
            affectedRowsArr = stmt.executeBatch();
            try {
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw new SQLException(ex);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        con.setAutoCommit(true);
        return affectedRowsArr;
    }
    /**
     * Проверяет соединение на валидность.
     * @return true если соединение с СУБД валидное. Иначе false.
     * @throws SQLException ошибка SQL.
     */
    public boolean isValid() throws SQLException {
        return this.con.isValid(0);
    }
    /**
     * Загружает свойства соединения с бд.
     * @param localName локальное имя properties-файла.
     * @throws IOException исключение ввода-вывода.
     *
    private void loadProperties(String localName) throws IOException {
        String fileName = this.getClass().getClassLoader().getResource(localName).getFile();
        Path fName = Paths.get(fileName);
        InputStream is = Files.newInputStream(fName);
        this.props.load(is);
    }*/
    /**
     * Выполняет select sql-запрос.
     * @param query sql-запрос.
     * @return карту с результатом запроса к бд.
     * @throws SQLException исключение SQL.
     */
    public LinkedList<HashMap<String, String>> select(String query) throws SQLException {
    	if (this.con == null || this.con.isClosed()) {
			this.setConnection();
    	}
    	LinkedList<HashMap<String, String>> rl = new LinkedList<>();
    	try (PreparedStatement pstmt = this.con.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
        	if (rs != null) {
            	ResultSetMetaData rsmd = rs.getMetaData();
            	while (rs.next()) {
            		HashMap<String, String> entry = new HashMap<>();
            		for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
	            		entry.put(rsmd.getColumnName(a).toLowerCase(), rs.getString(a));
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
     * Устанавливает соединение с СУБД.
     * @throws SQLException ошибка SQL.
     */
    public void setConnection() throws SQLException {
        if (this.con == null || this.con.isClosed()) {
            for (int a = 1; this.props.containsKey("url" + a); a++) {
                String url = this.props.getProperty("url" + a);
                url = String.format(url, this.props.getProperty("protocol"), this.props.getProperty("src"), this.props.getProperty("port"), this.props.getProperty("db"));
                if (this.props.containsKey("params")) {
                    url = String.format("%s;%s", url, this.props.get("params"));
                }
                try {
                    Connection con = DriverManager.getConnection(url, this.props.getProperty("user"), this.props.getProperty("pass"));
                    if (con != null) {
                        this.con = con;
                        break;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
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
