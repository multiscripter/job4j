package ru.job4j.control;

import java.util.LinkedList;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ru.job4j.control.persistence.AddressDAO;
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.service.Address;
/**
 * Класс AddressDAOTest тестирует класс AddressDAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-07
 * @since 2018-01-10
 */
public class AddressDAOTest {
    /**
     * AddressDAO.
     */
    private AddressDAO as;
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            this.driver.close();
        } catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            this.driver = DBDriver.getInstance();
            if (!this.driver.isDBDriverSet()) {
                this.driver.setDbDriver();
            }
            this.as = new AddressDAO();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public Address getAddressById(final int id) throws SQLException.
     */
    @Test
    public void testGetAddressById() {
        try {
            int addressId = 1;
            Address expected = new Address(addressId, "РФ", "Москва", "Кремль");
            Address actual = this.as.getAddressById(addressId);
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public Address getAddressById(final int id) throws SQLException.
     */
    @Test
    public void testGetAddressByIdThrowsSQLException() {
        try {
            this.as.getAddressById(-1);
        } catch (SQLException ex) {
            assertTrue(ex instanceof SQLException);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует public LinkedList<> getAddresses() throws SQLException.
     */
    @Test
    public void testGetAddresses() {
        try {
            LinkedList<Address> expected = new LinkedList<>();
            expected.add(new Address(3, "РФ", "Брянск", "Брянский лес"));
            expected.add(new Address(1, "РФ", "Москва", "Кремль"));
            expected.add(new Address(5, "РФ", "Москва", "ЛДПР"));
            expected.add(new Address(2, "РФ", "Москва", "ФБК"));
            expected.add(new Address(4, "США", "Вашингтон", "Капитолийский холм"));
            LinkedList<Address> actual = this.as.getAddresses();
            assertEquals(expected, actual);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}