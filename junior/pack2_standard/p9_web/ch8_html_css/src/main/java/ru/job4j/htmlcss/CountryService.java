package ru.job4j.htmlcss;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * Класс CountryService.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-19
 */
public class CountryService {
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
    CountryService() {
    	this.logger = LogManager.getLogger("CountryService");
    	this.db = DBDriver.getInstance();
    }
    /**
	 * Получает страну по идентификатору.
	 * @param id идентификатор.
     * @return страна.
     * @throws SQLException исключение SQL.
	 */
    public Country getCountryById(final int id) throws SQLException {
        Country country = null;
        String query = String.format("select name from countries where id = %d", id);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            HashMap<String, String> entry = rl.getFirst();
            country = new Country(id, entry.get("name"));
        }
        return country;
    }
    /**
	 * Получает страну по названию.
	 * @param name название.
     * @return страна.
     * @throws SQLException исключение SQL.
	 */
    public Country getCountryByName(final String name) throws SQLException {
        Country country = null;
        String query = String.format("select * from countries where name = '%s'", name);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            HashMap<String, String> entry = rl.getFirst();
            country = new Country(Integer.parseInt(entry.get("id")), entry.get("name"));
        }
        return country;
    }
    /**
	 * Получает список стран.
     * @return список стран.
     * @throws SQLException исключение SQL.
	 */
    public LinkedList<Country> getCountries() throws SQLException {
        LinkedList<Country> countries = new LinkedList<>();
        String query = String.format("select * from countries order by name");
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            for (HashMap<String, String> entry : rl) {
                countries.add(new Country(Integer.parseInt(entry.get("id")), entry.get("name")));
            }
        }
        return countries;
    }
}