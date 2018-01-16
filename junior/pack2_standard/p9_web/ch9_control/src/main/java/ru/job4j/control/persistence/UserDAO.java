package ru.job4j.control.persistence;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.control.service.Address;
import ru.job4j.control.service.MusicType;
import ru.job4j.control.service.Role;
import ru.job4j.control.service.User;
/**
 * Класс UserDAO реализует слой DAO между User и бд.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2017-11-26
 */
public class UserDAO {
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
     * Конструктор.
     */
    public UserDAO() {
    	this.logger = LogManager.getLogger("UserDAO");
    	this.db = DBDriver.getInstance("junior.pack2.p9.ch9.task1");
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
        user.setLogin(this.escape(user.getLogin()));
        String query = String.format("insert into addresses (country, city, addr) values ('%s', '%s', '%s')", user.getCountry(), user.getCity(), user.getAddr());
        HashMap<String, String> entry = this.db.insert(query);
        int addrId;
        if (!entry.isEmpty()) {
            user.setAddressId(Integer.parseInt(entry.get("id")));
            query = String.format("insert into users (login, pass, role_id, addr_id) values ('%s', '%s', %d, %d)", user.getLogin(), this.getPassHash(user.getPass()), user.getRoleId(), user.getAddressId());
            entry = this.db.insert(query);
            if (!entry.isEmpty()) {
                user.setId(Integer.parseInt(entry.get("id")));
                query = this.getUsersMusictypesQuery(user);
                this.db.insert(query);
                result = true;
            }
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
        this.db.delete(String.format("delete from users_musictypes where user_id = %d", id));
        this.db.delete(String.format("delete from addresses where id = (select addr_id from users where id = %d)", id));
        int affected = this.db.delete(String.format("delete from users where id = %d", id));
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
        String query = String.format("update addresses set country = '%s', city = '%s', addr = '%s' where id = %d", user.getCountry(), user.getCity(), user.getAddr(), user.getAddressId());
        int affected1 = this.db.update(query);
        query = String.format("update users set login = '%s', pass = '%s', role_id = %d, addr_id = %d where id = %d", user.getLogin(), this.getPassHash(user.getPass()), user.getRoleId(), user.getAddressId(), user.getId());
        int affected2 = this.db.update(query);
        this.db.delete(String.format("delete from users_musictypes where user_id = %d", user.getId()));
        query = this.getUsersMusictypesQuery(user);
        int affected3 = this.db.update(query);
        if (affected1 > 0 || affected2 > 0 || affected3 > 0) {
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
	 * Получает кодировку.
     * @return кодировка.
	 */
    public String getEncoding() {
        return this.enc;
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
        return new BigInteger(1, md.digest()).toString(16);
    }
    /**
	 * Получает строку запроса.
	 * @param user пользователь.
     * @return строка запроса.
	 */
    private String getUsersMusictypesQuery(User user) {
        LinkedList<MusicType> mtypes = user.getMusicTypes();
        Iterator<MusicType> iter = mtypes.iterator();
        LinkedList<String> rows = new LinkedList<>();
        while (iter.hasNext()) {
            MusicType cur = iter.next();
            rows.add(String.format("(%d, %d)", user.getId(), cur.getId()));
        }
        return String.format("insert into users_musictypes (user_id, musictype_id) values %s", String.join(", ", rows));
    }
    /**
     * Получает пользователя по адресу.
     * @param address адрес пользователя.
     * @return пользователь.
     * @throws SQLException исключение SQL.
     * @throws ParseException исключение парсинга.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    public User getUserByAddress(final Address address) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        User user = null;
        String query = String.format("select users.id, users.login, users.pass, role_id, roles.name as role_name, addresses.id as address_id, addresses.country as country, addresses.city as city, addresses.addr as addr from roles, addresses, users where role_id = roles.id and addr_id = addresses.id and country = '%s' and city = '%s' and addr = '%s'", address.getCountry(), address.getCity(), address.getAddr());
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        query = String.format("select users_musictypes.user_id as user_id, musictypes.id as mtype_id, musictypes.name as mname from musictypes, users_musictypes, users, addresses where musictypes.id = users_musictypes.musictype_id and users_musictypes.user_id = users.id and users.addr_id = addresses.id and country = '%s' and city = '%s' and addr = '%s' group by users_musictypes.user_id, musictypes.id order by mname", address.getCountry(), address.getCity(), address.getAddr());
        LinkedList<HashMap<String, String>> rl2 = this.db.select(query);
        LinkedList<User> users = this.processResult(rl, rl2);
        if (!users.isEmpty()) {
            user = users.getFirst();
        }
        return user;
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
        String query = String.format("select users.id, users.login, users.pass, role_id, roles.name as role_name, addresses.id as address_id, addresses.country as country, addresses.city as city, addresses.addr as addr from roles, addresses, users where role_id = roles.id and addr_id = addresses.id and users.id = %d", id);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        query = String.format("select users_musictypes.user_id as user_id, musictypes.id as mtype_id, musictypes.name as mname from musictypes, users_musictypes where musictypes.id = users_musictypes.musictype_id and users_musictypes.user_id = %d group by users_musictypes.user_id, musictypes.id order by mname", id);
        LinkedList<HashMap<String, String>> rl2 = this.db.select(query);
        LinkedList<User> users = this.processResult(rl, rl2);
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
    public User getUserByLogPass(String login, String pass) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        User user = null;
        login = this.escape(login);
        pass = this.getPassHash(pass);
        String query = String.format("select users.id, users.login, users.pass, role_id, roles.name as role_name, addresses.id as address_id, addresses.country as country, addresses.city as city, addresses.addr as addr from roles, addresses, users where role_id = roles.id and addr_id = addresses.id and users.login = '%s' and users.pass = '%s'", login, pass);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        query = String.format("select users_musictypes.user_id as user_id, musictypes.id as mtype_id, musictypes.name as mname from musictypes, users_musictypes, users where musictypes.id = users_musictypes.musictype_id and users_musictypes.user_id = users.id and users.login = '%s' and users.pass = '%s' group by users_musictypes.user_id, musictypes.id order by mname", login, pass);
        LinkedList<HashMap<String, String>> rl2 = this.db.select(query);
        LinkedList<User> users = this.processResult(rl, rl2);
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
        String query = String.format("select users.id, users.login, users.pass, role_id, roles.name as role_name, addresses.id as address_id, addresses.country as country, addresses.city as city, addresses.addr as addr from roles, addresses, users where role_id = roles.id and addr_id = addresses.id order by %s %s", order.equals("") ? "id" : order, desc ? "desc" : "asc");
    	LinkedList<HashMap<String, String>> rl = this.db.select(query);
        query = "select users_musictypes.user_id as user_id, musictypes.id as mtype_id, musictypes.name as mname from musictypes, users_musictypes where musictypes.id = users_musictypes.musictype_id group by users_musictypes.user_id, musictypes.id order by mname";
        LinkedList<HashMap<String, String>> rl2 = this.db.select(query);
        return this.processResult(rl, rl2);
    }
    /**
     * Получает всех пользователей по массиву музыкальных стилей.
     * @param mtypes массив музыкальных стилей.
     * @param order имя столбца сортировки (по умолчанию id).
     * @param desc направление сортировки (asc, desc. По умолчанию asc).
     * @return коллекция пользователей.
     * @throws SQLException исключение SQL.
     * @throws ParseException исключение парсинга.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    public LinkedList<User> getUsersByMusicTypes(final MusicType[] mtypes, final String order, final boolean desc) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String[] strs = new String[mtypes.length];
        for (int a = 0; a <  mtypes.length; a++) {
            strs[a] = Integer.toString(mtypes[a].getId());
        }
        String query = String.format("select users.id, users.login, users.pass, role_id, roles.name as role_name, addresses.id as address_id, addresses.country as country, addresses.city as city, addresses.addr as addr from roles, addresses, users, users_musictypes where role_id = roles.id and addr_id = addresses.id and users_musictypes.user_id = users.id and users_musictypes.musictype_id in (%s) group by users.id, roles.name, addresses.id order by %s %s", String.join(", ", strs), order.equals("") ? "id" : order, desc ? "desc" : "asc");
    	LinkedList<HashMap<String, String>> rl = this.db.select(query);
        query = String.format("select users_musictypes.user_id as user_id, musictypes.id as mtype_id, musictypes.name as mname from musictypes, users_musictypes, users where musictypes.id = users_musictypes.musictype_id and musictypes.id in (%s) group by users_musictypes.user_id, musictypes.id order by mname", String.join(", ", strs));
        LinkedList<HashMap<String, String>> rl2 = this.db.select(query);
        return this.processResult(rl, rl2);
    }
    /**
     * Получает всех пользователей по роли.
     * @param role роль.
     * @param order имя столбца сортировки (по умолчанию id).
     * @param desc направление сортировки (asc, desc. По умолчанию asc).
     * @return коллекция пользователей.
     * @throws SQLException исключение SQL.
     * @throws ParseException исключение парсинга.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    public LinkedList<User> getUsersByRole(final Role role, final String order, final boolean desc) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String query = String.format("select users.id, users.login, users.pass, role_id, roles.name as role_name, addresses.id as address_id, addresses.country as country, addresses.city as city, addresses.addr as addr from roles, addresses, users where role_id = roles.id and addr_id = addresses.id and roles.id = %d order by %s %s", role.getId(), order.equals("") ? "id" : order, desc ? "desc" : "asc");
    	LinkedList<HashMap<String, String>> rl = this.db.select(query);
        query = String.format("select users_musictypes.user_id as user_id, musictypes.id as mtype_id, musictypes.name as mname from musictypes, users_musictypes, users, roles where musictypes.id = users_musictypes.musictype_id and users_musictypes.user_id = users.id and role_id = roles.id and roles.id = '%d' group by users_musictypes.user_id, musictypes.id order by mname", role.getId());
        LinkedList<HashMap<String, String>> rl2 = this.db.select(query);
        return this.processResult(rl, rl2);
    }
    /**
     * Обрабатывает результат запроса select.
     * @param rl результат запроса select.
     * @param rl2 результат запроса select.
     * @return коллекция пользователей.
     * @throws SQLException исключение SQL.
     * @throws ParseException исключение парсинга.
     * @throws NoSuchAlgorithmException исключение "Нет такого алгоритма".
     * @throws UnsupportedEncodingException исключение "Кодировка не поддерживается.
     */
    private LinkedList<User> processResult(LinkedList<HashMap<String, String>> rl, LinkedList<HashMap<String, String>> rl2) throws SQLException, ParseException, NoSuchAlgorithmException, UnsupportedEncodingException {
        LinkedList<User> users = new LinkedList<>();
        if (!rl.isEmpty()) {
        	for (HashMap<String, String> entry : rl) {
                Role role = new Role(Integer.parseInt(entry.get("role_id")), entry.get("role_name"));
                Address address = new Address(Integer.parseInt(entry.get("address_id")), entry.get("country"), entry.get("city"), entry.get("addr"));
                int userId = Integer.parseInt(entry.get("id"));
                User user = new User();
                user.setAddress(address);
                user.setId(userId);
                user.setLogin(entry.get("login"));
                user.setPass(entry.get("pass"));
                user.setRole(role);
                if (!rl2.isEmpty()) {
                    LinkedList<MusicType> mtypes = new LinkedList<>();
                    for (HashMap<String, String> entry2 : rl2) {
                        if (userId == Integer.parseInt(entry2.get("user_id"))) {
                            MusicType mtype = new MusicType(Integer.parseInt(entry2.get("mtype_id")), entry2.get("mname"));
                            mtypes.add(mtype);
                        }
                    }
                    user.setMusicTypes(mtypes);
                }
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