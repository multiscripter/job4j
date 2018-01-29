package ru.job4j.control.persistence;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.control.service.Role;
/**
 * Класс RoleDAO реализует слой DAO между Role и бд.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-23
 * @since 2017-12-08
 */
public class RoleDAO {
    /**
     * Драйвер бд.
     */
	private DBDriver db;
	/**
     * Логгер.
     */
    private Logger logger;
    /**
     * Конструктор.
     */
    public RoleDAO() {
    	this.logger = LogManager.getLogger("RoleDAO");
    	this.db = DBDriver.getInstance();
    }
    /**
	 * Получает роль по идентификатору.
	 * @param id идентификатор.
     * @return роль.
     * @throws SQLException исключение SQL.
	 */
    public Role getRoleById(final int id) throws SQLException {
        Role role = null;
        String query = String.format("select name from roles where id = %d", id);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            HashMap<String, String> entry = rl.getFirst();
            role = new Role(id, entry.get("name"));
        }
        return role;
    }
    /**
	 * Получает роль по имени.
	 * @param name имя.
     * @return роль.
     * @throws SQLException исключение SQL.
	 */
    public Role getRoleByName(final String name) throws SQLException {
        Role role = null;
        String query = String.format("select * from roles where name = '%s'", name);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            HashMap<String, String> entry = rl.getFirst();
            role = new Role(Integer.parseInt(entry.get("id")), entry.get("name"));
        }
        return role;
    }
    /**
	 * Получает список ролей.
     * @return список ролей.
     * @throws SQLException исключение SQL.
	 */
    public LinkedList<Role> getRoles() throws SQLException {
        LinkedList<Role> roles = new LinkedList<>();
        String query = String.format("select * from roles order by name");
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            for (HashMap<String, String> entry : rl) {
                roles.add(new Role(Integer.parseInt(entry.get("id")), entry.get("name")));
            }
        }
        return roles;
    }
}