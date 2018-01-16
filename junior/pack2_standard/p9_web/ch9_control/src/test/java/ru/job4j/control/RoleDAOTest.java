package ru.job4j.control;

import java.util.LinkedList;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ru.job4j.control.persistence.DBDriver;
import ru.job4j.control.persistence.RoleDAO;
import ru.job4j.control.service.Role;
/**
 * Класс RoleDAOTest тестирует класс RoleDAO.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-15
 * @since 2017-12-18
 */
public class RoleDAOTest {
    /**
     * Драйвер бд.
     */
    private DBDriver driver;
    /**
     * RoleDAO.
     */
    private RoleDAO rls;
    /**
     * Действия после теста.
     */
    @After
    public void afterTest() {
        try {
            this.driver.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.driver = DBDriver.getInstance("junior.pack2.p9.ch9.task1");
        if (!this.driver.isDBDriverSet()) {
            this.driver.setDbDriver();
        }
        this.rls = new RoleDAO();
    }
    /**
     * Тестирует public Role getRoleById(final int id) throws SQLException.
     */
    @Test
    public void testGetRoleById() {
        try {
            int roleId = 1;
            Role expected = new Role(roleId, "administrator");
            Role actual = this.rls.getRoleById(roleId);
            assertEquals(expected, actual);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public Role getRoleById(final int id) throws SQLException.
     * @throws SQLException исключение SQL.
     */
    @Test
    public void testGetRoleByIdThrowsSQLException() throws SQLException {
        try {
            this.rls.getRoleById(-1);
        } catch (SQLException ex) {
            assertTrue(ex instanceof SQLException);
        }
    }
    /**
     * Тестирует public Role getRoleByName(final String name) throws SQLException.
     */
    @Test
    public void testGetRoleByName() {
        try {
            String roleName = "user";
            Role expected = new Role(3, roleName);
            Role actual = this.rls.getRoleByName(roleName);
            assertEquals(expected, actual);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует исключение SQLException, выбрасываемое из public Role getRoleByName(final String name) throws SQLException.
     */
    @Test
    public void testGetRoleByNameThrowsSQLException() {
        try {
            String roleName = "'";
            Role expected = new Role(2, roleName);
            Role actual = this.rls.getRoleByName(roleName);
            assertEquals(expected, actual);
        } catch (SQLException ex) {
            assertTrue(ex instanceof SQLException);
        }
    }
    /**
     * Тестирует public LinkedList<Role> getRoles() throws SQLException.
     */
    @Test
    public void testGetRoles() {
        try {
            LinkedList<Role> expected = new LinkedList<>();
            expected.add(new Role(1, "administrator"));
            expected.add(new Role(2, "moderator"));
            expected.add(new Role(3, "user"));
            LinkedList<Role> actual = this.rls.getRoles();
            assertEquals(expected, actual);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}