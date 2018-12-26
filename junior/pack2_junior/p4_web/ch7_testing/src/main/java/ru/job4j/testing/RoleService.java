package ru.job4j.testing;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс RoleService.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-26
 * @since 2017-12-08
 */
public class RoleService {
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
    RoleService() {
    	this.logger = LogManager.getLogger("RoleService");
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
        String query = "select * from roles order by name";
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            roles.addAll(rl.stream().map(entry -> new Role(Integer.parseInt(entry.get("id")), entry.get("name"))).collect(Collectors.toList()));
        }
        return roles;
    }
}