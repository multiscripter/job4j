package ru.job4j.checking;

import java.io.IOException;
import java.nio.file.Files;
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
/**
 * Класс DBDriver используется в тестах для проверки классов, работающих с СУБД.
 *
 * @see https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-07-23
 * @since 2018-03-07
 */
public class DBDriver {
    /**
     * Соединение с бд.
     */
	private Connection con;
    /**
     * Пароль бд.
     */
    private String pass;
    /**
     * Ресурс СУБД.
     */
    private String url;
    /**
     * Пользователь бд.
     */
    private String user;
    /**
	 * Конструктор.
     * @param url соединеия с СУБД.
     * @param user имя пользователя СУБД.
     * @param pass пароль пользователя СУБД.
	 */
	public DBDriver(String url, String user, String pass) {
        this.pass = pass;
        this.url = url;
        this.user = user;
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
    	int affected = 0;
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
    	if (this.con == null || this.con.isClosed()) {
			this.setConnection();
    	}
    	try (Statement stmt = this.con.createStatement()) {
			stmt.execute(query);
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
     * @throws SQLException исключение SQL.
     */
    public int[] executeSqlScript(String name) throws IOException, SQLException {
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
            this.con = DriverManager.getConnection(this.url, this.user, this.pass);
        }
    }
    /**
     * Устанавливает пароль пользователя СУБД.
     * @param pass пользователя СУБД.
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
    /**
     * Устанавливает ресурс СУБД.
     * @param url ресурс СУБД.
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Устанавливает пользователя СУБД.
     * @param user пользователь СУБД.
     */
    public void setUser(String user) {
        this.user = user;
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
