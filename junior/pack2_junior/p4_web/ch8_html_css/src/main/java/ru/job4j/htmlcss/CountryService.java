package ru.job4j.htmlcss;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;
/**
 * Класс CountryService.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-08
 * @since 2017-12-19
 */
public class CountryService {
    /**
     * Драйвер бд.
     */
	private DBDriver db;
    /**
     * Конструктор.
     * @throws ClassNotFoundException класс не найден.
     * @throws IllegalAccessException незаконный доступ.
     * @throws InstantiationException исключение создания экземпляра.
     * @throws URISyntaxException исключение синтакса URI.
     * @throws IOException исключение ввода-вывода.
     * @throws SQLException исключение SQL.
     */
    CountryService() throws IllegalAccessException, InstantiationException, IOException, SQLException, URISyntaxException, ClassNotFoundException {
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
        String query = "select * from countries order by name";
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            countries.addAll(rl.stream().map(entry -> new Country(Integer.parseInt(entry.get("id")), entry.get("name"))).collect(Collectors.toList()));
        }
        return countries;
    }
}