package ru.job4j.ui;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс UserService.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-11-26
 */
class UserService {
	/**
     * Драйвер бд.
     */
	private UserStore db;
	/**
     * Логгер.
     */
    private Logger logger;
    /**
     * Таблица в бд.
     */
    private String tbl;
    /**
     * Конструктор.
     */
    UserService() {
    	this.logger = LogManager.getLogger("UserService");
    	this.db = UserStore.getInstance();
    	this.tbl = this.db.getTblName();
    }
    /**
     * Добавляет пользователя.
     * @param user новый пользователь.
     * @return true если пользователь добавлен в бд. Иначе false.
     * @throws SQLException ошибка SQL.
     * @throws ParseException ошибка парсинга.
     */
    public boolean addUser(User user) throws SQLException, ParseException {
        boolean result = false;
        String query = String.format("insert into %s (name, login, email, createDate) values ('%s', '%s', '%s', '%5$tY-%5$tm-%5$td')", this.tbl, user.getName(), user.getLogin(), user.getEmail(), user.getDate());
        HashMap<String, String> entry = this.db.insert(query);
        if (!entry.isEmpty()) {
    		user.setId(Integer.parseInt(entry.get("id")));
        	result = true;
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
        int affected = this.db.delete(query);
        if (affected > 0) {
        	result = true;
        }
        return result;
    }
    /**
     * Получает пользователя по идентификатору.
     * @param id идентификатор пользователя.
     * @return пользователь.
     * @throws SQLException ошибка SQL.
     * @throws ParseException ошибка парсинга.
     */
    public User getUser(int id) throws SQLException, ParseException {
        User user = null;
        String query = String.format("select * from %s where id = %d", this.tbl, id);
        HashMap<String, String> entry = this.db.select(query).getFirst();
        if (!entry.isEmpty()) {
            GregorianCalendar cal = new GregorianCalendar();
    		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    		String strDate = entry.get("createdate");
    		Date date = sdf.parse(strDate);
    		cal.setTime(date);
            user = new User(Integer.parseInt(entry.get("id")), entry.get("name"), entry.get("login"), entry.get("email"), cal);
        }
        return user;
    }
    /**
     * Получает всех пользователей.
     * @param order имя столбца сортировки (по умолчанию id).
     * @param desc направление сортировки (asc, desc. По умолчанию asc).
     * @return Коллекция пользователей.
     * @throws SQLException ошибка SQL.
     * @throws ParseException ошибка парсинга.
     */
    public LinkedList<User> getUsers(final String order, final boolean desc) throws SQLException, ParseException {
        LinkedList<User> users = new LinkedList<>();
        String query = String.format("select * from %s order by %s %s", this.tbl, order.equals("") ? "id" : order, desc ? "desc" : "asc");
    	LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
        	for (HashMap<String, String> entry : rl) {
        		GregorianCalendar cal = new GregorianCalendar();
        		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        		String strDate = entry.get("createdate");
        		Date date = sdf.parse(strDate);
        		cal.setTime(date);
        		users.add(new User(Integer.parseInt(entry.get("id")), entry.get("name"), entry.get("login"), entry.get("email"), cal));
        	}
        }
        return users;
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
        int affected = this.db.update(query);
        if (affected > 0) {
        	result = true;
        }
        return result;
    }
}