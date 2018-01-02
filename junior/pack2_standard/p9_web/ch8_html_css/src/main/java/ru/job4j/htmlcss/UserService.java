package ru.job4j.htmlcss;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
 * @version 4
 * @since 2017-11-26
 */
class UserService {
    /**
     * CityService.
     */
    private CityService cits;
    /**
     * CountryService.
     */
    private CountryService cs;
	/**
     * Драйвер бд.
     */
	private DBDriver db;
    /**
     * Кодировка.
     */
	private String enc;
	/**
     * Логгер.
     */
    private Logger logger;
    /**
     * RoleService.
     */
    private RoleService rls;
    /**
     * Конструктор.
     */
    UserService() {
    	this.logger = LogManager.getLogger("UserService");
    	this.db = DBDriver.getInstance();
        this.cits = new CityService();
        this.cs = new CountryService();
        this.rls = new RoleService();
    }
    /**
     * Добавляет пользователя.
     * @param user новый пользователь.
     * @return true если пользователь добавлен в бд. Иначе false.
     * @throws SQLException исключение SQL.
     * @throws ParseException исключение парсинга.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    public boolean addUser(User user) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean result = false;
        user.setName(this.escape(user.getName()));
        user.setLogin(this.escape(user.getLogin()));
        user.setEmail(this.escape(user.getEmail()));
        String query = String.format("insert into users (name, login, email, createDate, pass, role_id, countries_cities_id) values ('%s', '%s', '%s', '%s', '%s', %d, (select id from countries_cities where country_id = %d and city_id = %d))", user.getName(), user.getLogin(), user.getEmail(), user.getDateStr(), this.getPassHash(user.getPass()), user.getRoleId(), user.getCountryId(), user.getCityId());
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
     * @throws SQLException исключение SQL.
     */
    public boolean deleteUser(final int id) throws SQLException {
        boolean result = false;
        String query = String.format("delete from users where id = %d", id);
        int affected = this.db.delete(query);
        if (affected > 0) {
        	result = true;
        }
        return result;
    }
    /**
     * Редактирует пользователя.
     * @param user новый пользователь.
     * @return true если пользователь обновлён в бд. Иначе false.
     * @throws SQLException исключение SQL.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    public boolean editUser(User user) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean result = false;
        String query = String.format("update users set name='%s', login='%s', email='%s', pass='%s', role_id = %d, countries_cities_id = (select id from countries_cities where country_id = %d and city_id = %d) where id = %d", user.getName(), user.getLogin(), user.getEmail(), this.getPassHash(user.getPass()), user.getRoleId(), user.getCountryId(), user.getCityId(), user.getId());
        int affected = this.db.update(query);
        if (affected > 0) {
        	result = true;
        }
        return result;
    }
    /**
	 * Экранирует символы в строке.
	 * @param str строка.
     * @return экранированная строка.
	 */
    private String escape(String str) {
        return str.replaceAll("[\\\0\\\'\\\"\\\b\\\n\\\r\\\t\\\\]", "");
    }
    /**
	 * Получает хэш-сумму пароля.
	 * @param pass пароль.
     * @return хэш-сумма пароля.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
	 */
    public String getPassHash(String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pass.getBytes(this.enc), 0, pass.length());
        String bi = new BigInteger(1, md.digest()).toString(16);
        return bi;
    }
    /**
     * Получает пользователя по идентификатору.
     * @param id идентификатор пользователя.
     * @return пользователь.
     * @throws SQLException исключение SQL.
     * @throws ParseException исключение парсинга.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    public User getUserById(final int id) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        User user = null;
        String query = String.format("select users.id, users.name, users.login, users.email, users.createdate, users.pass, role_id, roles.name  as role_name, countries.id as country_id, countries.name as country_name, cities.id as city_id, cities.name as city_name from roles, countries, cities, countries_cities, users where role_id = roles.id and countries_cities_id = countries_cities.id and countries_cities.country_id = countries.id and countries_cities.city_id = cities.id and users.id = %d", id);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        LinkedList<User> users = this.processResult(rl);
        if (!users.isEmpty()) {
            user = users.getFirst();
        }
        return user;
    }
    /**
     * Получает пользователя по логину и паролю.
     * @param login логин пользователя.
     * @param pass пароль пользователя.
     * @return пользователь.
     * @throws SQLException исключение SQL.
     * @throws ParseException исключение парсинга.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    public User getUserByLogPass(String login, String pass) throws SQLException, ParseException,  NoSuchAlgorithmException, UnsupportedEncodingException {
        User user = null;
        login = this.escape(login);
        pass = this.getPassHash(pass);
        String query = String.format("select users.id, users.name, users.login, users.email, users.createdate, users.pass, role_id, roles.name  as role_name, countries.id as country_id, countries.name as country_name, cities.id as city_id, cities.name as city_name from roles, countries, cities, countries_cities, users where role_id = roles.id and countries_cities_id = countries_cities.id and countries_cities.country_id = countries.id and countries_cities.city_id = cities.id and users.login = '%s' and users.pass = '%s'", login, pass);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        LinkedList<User> users = this.processResult(rl);
        if (!users.isEmpty()) {
            user = users.getFirst();
        }
        return user;
    }
    /**
     * Получает всех пользователей.
     * @param order имя столбца сортировки (по умолчанию id).
     * @param desc направление сортировки (asc, desc. По умолчанию asc).
     * @return коллекция пользователей.
     * @throws SQLException исключение SQL.
     * @throws ParseException исключение парсинга.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    public LinkedList<User> getUsers(final String order, final boolean desc) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        LinkedList<User> users = new LinkedList<>();
        String query = String.format("select users.id, users.name, users.login, users.email, users.createdate, users.pass, role_id, roles.name  as role_name, countries.id as country_id, countries.name as country_name, cities.id as city_id, cities.name as city_name from roles, countries, cities, countries_cities, users where role_id = roles.id and countries_cities_id = countries_cities.id and countries_cities.country_id = countries.id and countries_cities.city_id = cities.id order by %s %s", order.equals("") ? "id" : order, desc ? "desc" : "asc");
    	LinkedList<HashMap<String, String>> rl = this.db.select(query);
        users = this.processResult(rl);
        return users;
    }
    /**
     * Обрабатывает результат запроса select.
     * @param result результат запроса select.
     * @return коллекция пользователей.
     * @throws SQLException исключение SQL.
     * @throws ParseException исключение парсинга.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    private LinkedList<User> processResult(LinkedList<HashMap<String, String>> result) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        LinkedList<User> users = new LinkedList<>();
        if (!result.isEmpty()) {
        	for (HashMap<String, String> entry : result) {
        		GregorianCalendar cal = new GregorianCalendar();
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        		String strDate = entry.get("createdate");
        		Date date = sdf.parse(strDate);
        		cal.setTime(date);
                Role role = this.rls.getRoleById(Integer.parseInt(entry.get("role_id")));
                Country country = this.cs.getCountryById(Integer.parseInt(entry.get("country_id")));
                City city = this.cits.getCityById(Integer.parseInt(entry.get("city_id")));
                User user = new User();
                user.setId(Integer.parseInt(entry.get("id")));
                user.setName(entry.get("name"));
                user.setLogin(entry.get("login"));
                user.setEmail(entry.get("email"));
                user.setDate(cal);
                user.setPass(entry.get("pass"));
                user.setRole(role);
                user.setCountry(country);
                user.setCity(city);
        		users.add(user);
        	}
        }
        return users;
    }
    /**
     * Устанавдливает кодировку.
     * @param enc кодировка.
     */
    public void setEncoding(String enc) {
        this.enc = enc;
    }
}