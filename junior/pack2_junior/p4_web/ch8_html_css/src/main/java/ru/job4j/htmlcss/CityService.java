package ru.job4j.htmlcss;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
//import java.util.List;
import java.util.LinkedList;
/**
 * Класс CityService.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2019-01-08
 * @since 2017-12-19
 */
public class CityService {
    /**
     * Драйвер бд.
     */
    private final DBDriver db;
    /**
     * Конструктор.
     * @throws ClassNotFoundException класс не найден.
     * @throws IllegalAccessException незаконный доступ.
     * @throws InstantiationException исключение создания экземпляра.
     * @throws URISyntaxException исключение синтакса URI.
     * @throws IOException исключение ввода-вывода.
     * @throws SQLException исключение SQL.
     */
    CityService() throws IllegalAccessException, InstantiationException, IOException, SQLException, URISyntaxException, ClassNotFoundException {
        this.db = DBDriver.getInstance();
    }
    /**
     * Получает город по идентификатору.
     * @param id идентификатор.
     * @return город.
     * @throws SQLException исключение SQL.
     */
    public City getCityById(final int id) throws SQLException {
        City city = null;
        String query = String.format("select name, countries_cities.country_id as country_id from cities, countries_cities where countries_cities.city_id = cities.id and cities.id = %d", id);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            for (HashMap<String, String> entry : rl) {
                if (city != null) {
                    city.addCountryId(Integer.parseInt(entry.get("country_id")));
                } else {
                    city = new City(id, entry.get("name"), new LinkedList<>(Arrays.asList(Integer.parseInt(entry.get("country_id")))));
                }
            }
        }
        return city;
    }
    /**
     * Получает город по названию.
     * @param name название.
     * @return город.
     * @throws SQLException исключение SQL.
     */
    public City getCityByName(final String name) throws SQLException {
        City city = null;
        String query = String.format("select cities.id as id, countries_cities.country_id as country_id from cities, countries_cities where countries_cities.city_id = cities.id and cities.name = '%s'", name);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            for (HashMap<String, String> entry : rl) {
                if (city != null) {
                    city.addCountryId(Integer.parseInt(entry.get("country_id")));
                } else {
                    city = new City(Integer.parseInt(entry.get("id")), name, new LinkedList<>(Arrays.asList(Integer.parseInt(entry.get("country_id")))));
                }
            }
        }
        return city;
    }
    /**
     * Получает список городов.
     * @return список городов.
     * @throws SQLException исключение SQL.
     */
    public LinkedList<City> getCities() throws SQLException {
        LinkedList<City> list = null;
        HashMap<Integer, City> cities = new HashMap<>();
        String query = "select cities.id, cities.name, countries.id as country_id from cities, countries_cities, countries where cities.id = countries_cities.city_id and countries_cities.country_id = countries.id";
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            for (HashMap<String, String> entry : rl) {
                City city = cities.get(Integer.parseInt(entry.get("id")));
                if (city != null) {
                    city.addCountryId(Integer.parseInt(entry.get("country_id")));
                } else {
                    city = new City(Integer.parseInt(entry.get("id")), entry.get("name"), new LinkedList<>(Arrays.asList(Integer.parseInt(entry.get("country_id")))));
                }
                cities.put(Integer.parseInt(entry.get("id")), city);
            }
            list = new LinkedList<>(cities.values());
            Collections.sort((LinkedList) list);
        }
        return list;
    }
}