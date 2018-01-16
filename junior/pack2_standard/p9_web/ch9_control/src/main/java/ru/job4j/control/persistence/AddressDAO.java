package ru.job4j.control.persistence;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.job4j.control.service.Address;
/**
 * Класс AddressDAO реализует слой DAO между Address и бд.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-16
 * @since 2018-01-10
 */
public class AddressDAO {
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
    public AddressDAO() {
    	this.logger = LogManager.getLogger("AddressDAO");
    	this.db = DBDriver.getInstance("junior.pack2.p9.ch9.task1");
    }
    /**
	 * Получает адрес по идентификатору.
	 * @param id идентификатор.
     * @return адрес.
     * @throws SQLException исключение SQL.
	 */
    public Address getAddressById(final int id) throws SQLException {
        Address address = null;
        String query = String.format("select country, city, addr from addresses where id = %d", id);
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            HashMap<String, String> entry = rl.getFirst();
            address = new Address(id, entry.get("country"), entry.get("city"), entry.get("addr"));
        }
        return address;
    }
    /**
	 * Получает список адресов.
     * @return список адресов.
     * @throws SQLException исключение SQL.
	 */
    public LinkedList<Address> getAddresses() throws SQLException {
        LinkedList<Address> addresses = new LinkedList<>();
        String query = "select * from addresses order by country, city, addr";
        LinkedList<HashMap<String, String>> rl = this.db.select(query);
        if (!rl.isEmpty()) {
            for (HashMap<String, String> entry : rl) {
                addresses.add(new Address(Integer.parseInt(entry.get("id")), entry.get("country"), entry.get("city"), entry.get("addr")));
            }
        }
        return addresses;
    }
}