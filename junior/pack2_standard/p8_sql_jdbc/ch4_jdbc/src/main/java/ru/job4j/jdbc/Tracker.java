package ru.job4j.jdbc;

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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
/**
 * Class Tracker реализует сущность Трэкер заявок.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 8
 * @since 2017-04-18
 */
public class Tracker {
    /**
     * Драйвер бд.
     */
    private PgSQLJDBCDriver dbDriver;
    /**
     * Массив заявок.
     */
    private HashMap<String, Item> items;
    /**
     * Ёмкость массива заявок.
     */
    private int capacity;
    /**
     * Конструктор без параметров.
     */
    public Tracker() {
        try {
            String path = PgSQLJDBCDriver.class.getResource(".").getPath().replaceFirst("^/(.:/)", "$1");
            path = String.format("%s../../../../../src/main/resources/tracker.properties", path);
            Path fName = Paths.get(path);
            InputStream is = Files.newInputStream(fName);
            Properties props = new Properties();
            props.load(is);
            dbDriver = new PgSQLJDBCDriver();
            dbDriver.setProtocol(props.getProperty("tracker.protocol"));
            dbDriver.setSrc(props.getProperty("tracker.src"));
            dbDriver.setPort(Integer.parseInt(props.getProperty("tracker.port")));
            dbDriver.setUser(props.getProperty("tracker.user"));
            dbDriver.setPass(props.getProperty("tracker.pass"));
            dbDriver.setDB(props.getProperty("tracker.db"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.items = new HashMap<>();
    }
    /**
     * Добавляет заявку.
     * @param item заявка.
     * @return добавленная заявка.
     * @throws SQLException ошибка SQL.
     */
    public Item add(Item item) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            con = this.dbDriver.getConnection();
            String query = "insert into orders (name, descr, created) values (?, ?, ?)";
            pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDesc());
            pstmt.setTimestamp(3, new Timestamp(item.getCreated()));
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            resultSet.next();
            item.setId(Integer.toString(resultSet.getInt(1)));
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (con != null) {
                con.close();
            }
        }
        this.items.put(item.getId(), item);
        return item;
    }
    /**
     * Удаляет заявку по идентификатору.
     * @param id идентификатор заявки.
     * @return true если заявка удалена, иначе false.
     * @throws SQLException ошибка SQL.
     */
    public boolean delete(String id) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        boolean result = false;
        try {
            Item item = this.items.remove(id);
            if (item != null) {
                con = this.dbDriver.getConnection();
                stmt = con.createStatement();
                int affectedRows = stmt.executeUpdate(String.format("delete from orders where id = %d", Integer.parseInt(item.getId())));
                item = new Item();
                result = true;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
    /**
     * Ищет заявку по идентификатору.
     * @param id идентификатор заявки.
     * @return найденный или пустой объект заявки.
     */
    public Item findById(String id) {
        Item item = this.items.get(id);
        return item != null ? item : new Item();
    }
    /**
     * Ищет заявку по имени.
     * @param name имя заявки.
     * @return массив найденных заявок или пустой массив.
     */
    public Item[] findByName(String name) {
        Collection<Item> items = this.items.values();
        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            if (!name.equals(item.getName())) {
                iter.remove();
            }
        }
        return items.toArray(new Item[items.size()]);
    }
    /**
     * Получает все заявки.
     * @return массив заявок.
     */
    public Item[] getAll() {
        Collection<Item> c = this.items.values();
        ArrayList<Item> list = new ArrayList<Item>(c);
        Collections.sort(list, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return list.toArray(new Item[list.size()]);
    }
    /**
     * Получет драйвер бд.
     * @return dbDriver драйвер бд.
     */
    public PgSQLJDBCDriver getDbDriver() {
        return this.dbDriver;
    }
    /**
     * Получает количество заявок.
     * @return количество заявок.
     */
    public int getQuantity() {
        return this.items.size();
    }
    /**
     * Обновляет заявку.
     * @param item заявка.
     * @return true если обновление успешно. Иначе false.
     * @throws SQLException ошибка SQL.
     */
    public boolean update(Item item) throws SQLException {
        boolean result = false;
        if (this.items.containsKey(item.getId())) {
            Connection con = null;
            Statement stmt = null;
            try {
                con = this.dbDriver.getConnection();
                stmt = con.createStatement();
                int affectedRows = stmt.executeUpdate(String.format("update orders set name = '%s', descr = '%s', created = '%s' where id = %s", item.getName(), item.getDesc(), new Timestamp(item.getCreated()), item.getId()));
                if (affectedRows == 1) {
                    this.items.replace(item.getId(), item);
                    result = true;
                }
            } catch (SQLException ex) {
                throw new SQLException(ex);
            } finally {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        }
        return result;
    }
}
