package ru.job4j.htmlcss;

import java.util.LinkedList;
import java.sql.SQLException;
import org.junit.Before;
//import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * Класс RoleServiceTest тестирует класс RoleService.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-12-18
 */
public class RoleServiceTest {
    /**
     * RoleService.
     */
    private RoleService rls;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.rls = new RoleService();
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
            Role expected = new Role(2, roleName);
            Role actual = this.rls.getRoleByName(roleName);
            assertEquals(expected, actual);
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            expected.add(new Role(2, "user"));
            LinkedList<Role> actual = this.rls.getRoles();
            assertEquals(expected, actual);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}