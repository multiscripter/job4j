package ru.job4j.generic;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
/**
 * Класс UserStoreTest тестирует класс UserStore.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-26
 */
public class UserStoreTest {
    /**
     * Хранилище пользователей.
     */
    private UserStore storeU;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        int size = 11;
        this.storeU = new UserStore(size);
        for (int a = 0; a < size; a++) {
            User user = new User();
            user.setName("FakeUser" + a);
            this.storeU.add(user);
        }
    }
    /**
     * Тестирует add().
     */
    @Test
    public void testAdd() {
        User user = new User();
        user.setName("FakeUserFoo");
        this.storeU.add(user);
    }
    /**
     * Тестирует count().
     */
    @Test
    public void testCount() {
        for (int a = 15; a < 20; a++) {
            User user = new User();
            user.setName("FakeUser" + a);
            this.storeU.add(user);
        }
        int result = this.storeU.count();
        assertEquals(16, result);
    }
    /**
     * Тестирует delete().
     */
    @Test
    public void testDelete() {
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
     * Тестирует get().
     */
    @Test
    public void testGet() {
        User expected = new User();
        expected.setName("FakeUser4");
        User result = this.storeU.get(Integer.toString(4));
        assertEquals(expected, result);
    }
    /**
     * Тестирует index().
     */
    @Test
    public void testIndex() {
        assertEquals(11, this.storeU.index());
    }
    /**
     * Тестирует size().
     */
    @Test
    public void testSize() {
        User user = new User();
        user.setName("FakeUserFoo");
        this.storeU.add(user);
        int result = this.storeU.size();
        assertEquals(22, result);
    }
    /**
     * Тестирует update().
     */
    @Test
    public void testUpdate() {
        String index = Integer.toString(4);
        User expected = new User();
        expected.setName("FakeUser4");
        this.storeU.update(index, expected);
        User result = this.storeU.get(index);
        assertEquals(expected, result);
    }
}