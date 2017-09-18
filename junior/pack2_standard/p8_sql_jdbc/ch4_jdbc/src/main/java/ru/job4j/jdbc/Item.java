package ru.job4j.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 * Class Item реализует сущность Заявка.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 3
 * @since 2017-04-18
 */
public class Item {
    /**
     * Драйвер бд.
     */
    private static PgSQLJDBCDriver dbDriver;
    /**
     * Идентификатор заявки.
     */
    private String id;
    /**
     * Имя заявки.
     */
    private String name;
    /**
     * Описание заявки.
     */
    private String desc;
    /**
     * Время заявки в формате Unix-timestamp.
     */
    private long created;
    /**
     * Массив комментариев заявки.
     */
    private ArrayList<String> comments;
    /**
     * Конструктор без параметров.
     */
    public Item() {
    }
    /**
     * Конструктор.
     * @param name имя заявки.
     * @param desc описание заявки.
     * @throws SQLException ошибка SQL.
     */
    public Item(String name, String desc) throws SQLException {
        try {
            this.addIntoDB(name, desc, System.currentTimeMillis());
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        this.name = name;
        this.desc = desc;
        this.comments = new ArrayList<>();
    }
    /**
     * Добавляет новую заявку в бд.
     * @param name имя заявки.
     * @param desc описание заявки.
     * @param created время создания заявки.
     * @throws SQLException ошибка SQL.
     */
    private void addIntoDB(String name, String desc, long created) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            con = Item.getDbDriver().getConnection();
            String query = "insert into orders (name, descr, created) values (?, ?, ?)";
            pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, name);
            pstmt.setString(2, desc);
            pstmt.setTimestamp(3, new Timestamp(created));
            pstmt.executeUpdate();
            resultSet = pstmt.getGeneratedKeys();
            resultSet.next();
            this.id = Integer.toString(resultSet.getInt(1));
            this.created = created;
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
    }
    /**
     * Добавляет новую заявку в бд.
     * @return true если заявка удалена из бд. Иначе false.
     * @throws SQLException ошибка SQL.
     */
    public boolean deleteFromDB() throws SQLException {
        boolean result = false;
        try (Connection con = dbDriver.getConnection()) {
            Statement stmt = con.createStatement();
            int affectedRows = stmt.executeUpdate(String.format("delete from orders where id = %d", Integer.parseInt(this.id)));
            if (affectedRows == 1) {
                result = true;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
        return result;
    }
    /**
     * Получет идентификатор заявки.
     * @return нидентификатор заявки.
     */
    public String getId() {
        return this.id;
    }
    /**
     * Получет имя заявки.
     * @return имя заявки.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Устанавливает имя заявки.
     * @param name имя.
     * @throws SQLException ошибка SQL.
     */
    public void setName(String name) throws SQLException {
        try (Connection con = dbDriver.getConnection()) {
            Statement stmt = con.createStatement();
            int affectedRows = stmt.executeUpdate(String.format("update orders set name = '%s' where id = %d", name, Integer.parseInt(this.id)));
            if (affectedRows == 1) {
                this.name = name;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    /**
     * Получет описание заявки.
     * @return описание заявки.
     */
    public String getDesc() {
        return this.desc;
    }
    /**
     * Устанавливает описание заявки.
     * @param desc описание заявки.
     * @throws SQLException ошибка SQL.
     */
    public void setDesc(String desc) throws SQLException {
        try (Connection con = dbDriver.getConnection()) {
            Statement stmt = con.createStatement();
            int affectedRows = stmt.executeUpdate(String.format("update orders set descr = '%s' where id = %d", desc, Integer.parseInt(this.id)));
            if (affectedRows == 1) {
                this.desc = desc;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    /**
     * Получет время заявки.
     * @return время заявки.
     */
    public long getCreated() {
        return this.created;
    }
    /**
     * Получет комментарии заявки.
     * @return комментарии заявки.
     */
    public String[] getComments() {
        return this.comments.toArray(new String[this.comments.size()]);
    }
    /**
     * Получет драйвер бд.
     * @return dbDriver драйвер бд.
     */
    public static PgSQLJDBCDriver getDbDriver() {
        return dbDriver;
    }
    /**
     * Проверяет объект на пустоту.
     * @return true если объект пустой, иначе false.
     */
    public boolean isEmpty() {
        return this.id == null && this.name == null && this.desc == null && this.created == 0L;
    }
    /**
     * Переопределяет метод hashCode().
     * @return хэш-сумму.
     */
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        result = prime * result + ((this.desc == null) ? 0 : this.desc.hashCode());
        result = prime * result + String.valueOf(this.created).hashCode();
        return result;
    }
    /**
     * Переопределяет метод equals().
     * @return true если объект пустой, иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Item item = (Item) obj;
        if (!this.id.equals(item.id) || !this.name.equals(item.name) || !this.desc.equals(item.desc) || this.created != item.created) {
            return false;
        }
        return true;
    }
    /**
     * Переопределяет метод toString().
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ");
        sb.append(this.getId());
        sb.append(", user name: ");
        sb.append(this.getName());
        sb.append(", description: ");
        sb.append(this.getDesc());
        sb.append(", created: ");
        sb.append(this.getCreated());
        return sb.toString();
    }
    static {
        try {
            Prepare pre = new Prepare();
            pre.loadProperties("tracker.properties");
            pre.setDbDriver(new PgSQLJDBCDriver());
            dbDriver = pre.getDbDriver();
            pre.executeSql("junior.pack2.p8.ch4.task2.sql");
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}
