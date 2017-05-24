package ru.job4j.generic;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
/**
 * Класс StoreTest тестирует класс Store.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-24
 */
public class StoreTest {
    /**
     * Хранилище пользователей.
     */
    private Store<User> storeU;
    /**
     * Хранилище ролей.
     */
    private Store<Role> storeR;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        int size = 11;
        this.storeU = new Store<>(size);
        this.storeR = new Store<>(size);
        for (int a = 0; a < size; a++) {
            User user = new User();
            user.setName("FakeUser" + a);
            this.storeU.add(user);
            Role role = new Role();
            role.setName("FakeRole" + a);
            this.storeR.add(role);
        }
    }
    /**
     * Тестирует add().
     */
    @Test
    public void testAddUser() {
        User user = new User();
        user.setName("FakeUserFoo");
        this.storeU.add(user);
        Role role = new Role();
        role.setName("FakeRoleFoo");
        this.storeR.add(role);
    }
    /**
     * Тестирует count() с типом User.
     */
    @Test
    public void testCountUser() {
        for (int a = 15; a < 20; a++) {
            User user = new User();
            user.setName("FakeUser" + a);
            this.storeU.add(user);
        }
        int result = this.storeU.count();
        assertEquals(16, result);
    }
    /**
     * Тестирует count() с типом Role.
     */
    @Test
    public void testCountRole() {
        for (int a = 15; a < 20; a++) {
            Role role = new Role();
            role.setName("FakeRole" + a);
            this.storeR.add(role);
        }
        int result = this.storeR.count();
        assertEquals(16, result);
    }
    /**
     * Тестирует delete() с типом User.
     */
    @Test
    public void testDeleteUser() {
        User[] expected = new User[5];
        for (int a = 0; a < 5; a++) {
            User user = new User();
            user.setName("FakeUser" + a);
            expected[a] = user;
        }
        for (int a = 5; a < 11; a++) {
            this.storeU.delete(Integer.toString(a));
        }
        User[] result = Arrays.copyOfRange(this.storeU.toArray(), 0, this.storeU.count(), User[].class);
        assertArrayEquals(expected, result);
    }
    /**
     * Тестирует get() с типом User.
     */
    @Test
    public void testGetUser() {
        User expected = new User();
        expected.setName("FakeUser4");
        User result = this.storeU.get(Integer.toString(4));
        assertEquals(expected, result);
    }
    /**
     * Тестирует index() с типом User.
     */
    @Test
    public void testIndexUser() {
        assertEquals(11, this.storeU.index());
    }
    /**
     * Тестирует size() с типом User.
     */
    @Test
    public void testSizeUser() {
        User user = new User();
        user.setName("FakeUserFoo");
        this.storeU.add(user);
        int result = this.storeU.size();
        assertEquals(22, result);
    }
    /**
     * Тестирует update() с типом User.
     */
    @Test
    public void testUpdateUser() {
        String index = Integer.toString(4);
        User expected = new User();
        expected.setName("FakeUser4");
        this.storeU.update(index, expected);
        User result = this.storeU.get(index);
        assertEquals(expected, result);
    }
    /**
     * Тестирует update() с типом Role.
     */
    @Test
    public void testUpdateRole() {
        String index = Integer.toString(10);
        Role expected = new Role();
        expected.setName("FakeRole10");
        this.storeR.update(index, expected);
        Role result = this.storeR.get(index);
        assertEquals(expected, result);
    }
}