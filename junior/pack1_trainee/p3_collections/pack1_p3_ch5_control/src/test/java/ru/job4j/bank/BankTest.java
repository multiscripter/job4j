package ru.job4j.bank;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
/**
 * Класс BankTest тестирует класс Bank.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-06
 * @since 2017-05-15
 */
public class BankTest {
    /**
     * Тестовый пользователь.
     */
    private User testUser;
    /**
     * Тестовый пользователь 2.
     */
    private User testUser2;
    /**
     * Тестовый счёт.
     */
    private Account testAccount;
    /**
     * Тестовый счёт 2.
     */
    private Account testAccount2;
    /**
     * Объект Comparator.
     */
    private Comparator<Account> comparator;
    /**
     * Объект банка.
     */
    private Bank bank;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.bank = new Bank();
        this.testUser = new User("TestUser", "TestPassport");
        this.bank.addUser(this.testUser);
        this.testAccount = new Account("0OIKZHGH", 9999.99);
        this.bank.addAccountToUser(this.testUser, this.testAccount);
        this.testUser2 = new User("josa", "josaDokumenti");
        this.bank.addUser(this.testUser2);
        this.testAccount2 = new Account("QP59ys65", 100.0);
        this.bank.addAccountToUser(this.testUser2, this.testAccount2);
        this.comparator = new Comparator<Account>() {
            @Override
            public int compare(Account o1, Account o2) {
                return Integer.compare(o1.getRequisites().hashCode(), o2.getRequisites().hashCode());
            }
        };
    }
    /**
     * Тестирует addUser().
     */
    @Test
    public void testAddUser() {
        boolean result = false;
        String passport = "FooPassport";
        User user = new User("Foo", passport);
        this.bank.addUser(user);
        List<User> users = this.bank.getUsersAsList();
        for (User item : users) {
            if (item.equals(user)) {
                result = true;
                break;
            }
        }
        assertTrue(result);
    }
    /**
     * Тестирует getUserByPassport().
     */
    @Test
    public void testGetUserByPassport() {
        String passport = "GetUPassport";
        User expected = new User("Foo", passport);
        this.bank.addUser(expected);
        User result = this.bank.getUserByPassport(passport);
        assertEquals(expected, result);
    }
    /**
     * Тестирует deleteUser().
     */
    @Test
    public void testDeleteUser() {
        String passport = "DelPassport";
        User delUser = new User("DelUser", passport);
        this.bank.addUser(delUser);
        this.bank.deleteUser(this.bank.getUserByPassport(passport));
        assertNull(this.bank.getUserByPassport(passport));
    }
    /**
     * Тестирует addAccountToUser().
     */
    @Test
    public void testAddAccountToUser() {
        boolean result = false;
        String requisites = "bx86qmxJ";
        Account account = new Account(requisites, 100500.0);
        this.bank.addAccountToUser(this.testUser, account);
        List<Account> accounts = this.bank.getAccounts();
        for (Account item : accounts) {
            if (item.getRequisites().equals(requisites)) {
                result = true;
                break;
            }
        }
        assertTrue(result);
    }
    /**
     * Тестирует deleteAccountFromUser().
     */
    @Test
    public void testDeleteAccountFromUser() {
        boolean result = true;
        this.bank.deleteAccountFromUser(this.testUser, this.testAccount);
        List<Account> accounts = this.bank.getAccounts();
        for (Account item : accounts) {
            if (item.equals(this.testAccount)) {
                result = false;
                break;
            }
        }
        assertTrue(result);
    }
    /**
     * Тестирует getAccounts().
     */
    @Test
    public void testGetAccounts() {
        List<Account> expected = new LinkedList<>();
        expected.add(this.testAccount);
        expected.add(this.testAccount2);
        User bar = new User("Bar", "BarPassport");
        this.bank.addUser(bar);
        Account account = new Account("B0YgF0Se", 9876.5);
        expected.add(account);
        expected.sort(this.comparator);
        this.bank.addAccountToUser(bar, account);
        List<Account> result = this.bank.getAccounts();
        result.sort(this.comparator);
        assertEquals(expected, result);
    }
    /**
     * Тестирует getUserAccounts().
     */
    @Test
    public void testGetUserAccounts() {
        List<Account> expected = new LinkedList<>();
        User baz = new User("Baz", "BazPazzportz");
        this.bank.addUser(baz);
        String[] strs = new String[]{"rlNDuKON", "2Fj9DjAv", "RUVTgzhR", "6nOV9RsH", "DCbeJ9BT"};
        Double[] data = new Double[]{1111.11, 2222.22, 33333.33, 4444.44, 5555.55};
        for (int a = 0; a < strs.length; a++) {
            Account account = new Account(strs[a], data[a]);
            expected.add(account);
            this.bank.addAccountToUser(baz, account);
        }
        List<Account> result = this.bank.getUserAccounts(baz);
        assertEquals(expected, result);
    }
    /**
     * Тестирует transferMoney().
     */
    @Test
    public void testTransferMoney() {
        boolean result = this.bank.transferMoney(this.testUser, this.testAccount, this.testUser2, this.testAccount2, 1000);
        assertTrue(result);
    }
    /**
     * Тестирует transferMoney(). Пользователь-источник не существует.
     */
    @Test
    public void testTransferMoneySrcUserNotExists() {
        User srcUser = new User("Batman", "bbbbbbbb");
        boolean result = this.bank.transferMoney(srcUser, this.testAccount, this.testUser2, this.testAccount2, 1000);
        assertFalse(result);
    }
    /**
     * Тестирует transferMoney(). Cчёта-источник не существует.
     */
    @Test
    public void testTransferMoneySrcAccountNotExists() {
        Account srcAcc = new Account("aaaaaaaaaaaa", 888.88);
        boolean result = this.bank.transferMoney(this.testUser, srcAcc, this.testUser2, this.testAccount2, 1000);
        assertFalse(result);
    }
    /**
     * Тестирует transferMoney(). Пользователя-получатель не существует.
     */
    @Test
    public void testTransferMoneyDstUserNotExists() {
        User dstUser = new User("CoolName", "ccc");
        boolean result = this.bank.transferMoney(this.testUser, this.testAccount, dstUser, this.testAccount2, 1000);
        assertFalse(result);
    }
    /**
     * Тестирует transferMoney(). Счёта-получатель не существует.
     */
    @Test
    public void testTransferMoneyDstAccountNotExists() {
        Account dstAcc = new Account("dddd", 888.88);
        boolean result = this.bank.transferMoney(this.testUser, this.testAccount, this.testUser2, dstAcc, 1000);
        assertFalse(result);
    }
    /**
     * Тестирует transferMoney(). Недостаточно средств на счёте-источнике.
     */
    @Test
    public void testTransferMoneySrcAccountNotEnoughMomey() {
        this.testAccount.setValue(999.99);
        boolean result = this.bank.transferMoney(this.testUser, this.testAccount, this.testUser2, this.testAccount2, 1000);
        assertFalse(result);
    }
}