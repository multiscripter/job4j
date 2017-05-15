package ru.job4j.bank;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
/**
 * Class Bank реализует сущность Банк.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-15
 */
class Bank {
    /**
     * Список счетов пользователя.
     */
    private TreeMap<String, User> users;
    /**
     * Список счетов пользователя.
     */
    private TreeMap<User, List<Account>> accounts;
    /**
     * Конструктор.
     */
    Bank() {
        this.users = new TreeMap<>();
        this.accounts = new TreeMap<>();
    }
    /**
     * Добавляет пользователя.
     * @param user пользователь.
     */
    public void addUser(User user) {
        this.users.put(user.getPassport(), user);
    }
    /**
     * Получает пользователя.
     * @param passport паспорт пользователя.
     * @return пользователь.
     */
    public User getUserByPassport(String passport) {
        return this.users.get(passport);
    }
    /**
     * Удаляет пользователя.
     * @param user пользователь.
     */
    public void deleteUser(User user) {
        this.users.remove(user.getPassport());
    }
    /**
     * Получает пользователей.
     * @return дерево пользователей.
     */
    public TreeMap<String, User> getUsers() {
        return this.users;
    }
    /**
     * Получает пользователей.
     * @return список пользователей.
     */
    public List<User> getUsersAsList() {
        return new LinkedList(this.users.values());
    }
    /**
     * Добавляет счёт пользователя.
     * @param user пользователь.
     * @param account счёт пользователя.
     */
    public void addAccountToUser(User user, Account account) {
        List<Account> userAccounts = this.accounts.get(user);
        if (userAccounts == null) {
            userAccounts = new LinkedList();
        }
        userAccounts.add(account);
        this.accounts.put(user, userAccounts);
    }
    /**
     * Удаляет счёт пользователя.
     * @param user пользователь.
     * @param account счёт пользователя.
     */
    public void deleteAccountFromUser(User user, Account account) {
        List<Account> userAccounts = this.accounts.get(user);
        userAccounts.remove(account);
        this.accounts.put(user, userAccounts);
    }
    /**
     * Получает список со счетами.
     * @return список со счетами.
     */
    public List<Account> getAccounts() {
        List<Account> accounts = new LinkedList();
        for (List<Account> userAccounts : this.accounts.values()) {
            accounts.addAll(userAccounts);
        }
        return accounts;
    }
    /**
     * Получает список со счетами пользователя.
     * @param user пользователь.
     * @return список со счетами пользователя.
     */
    public List<Account> getUserAccounts(User user) {
        return this.accounts.get(user);
    }
    /**
     * Переводит деньги между счетами.
     * @param srcUser пользователь-источник.
     * @param srcAccount счёт списания.
     * @param dstUser пользователь-получатель.
     * @param dstAccount счёт получения.
     * @param amount сумма перевода.
     * @return true в случае успешного перевода денег, иначе false.
     */
    public boolean transferMoney(User srcUser, Account srcAccount, User dstUser, Account dstAccount, double amount) {
        boolean success = false;
        List<Account> srcAccounts = this.getUserAccounts(srcUser);
        List<Account> dstAccounts = this.getUserAccounts(dstUser);
        if (srcAccounts != null && dstAccounts != null) {
            int srcIndex = srcAccounts.indexOf(srcAccount);
            int dstIndex = dstAccounts.indexOf(dstAccount);
            if (srcIndex != -1 && dstIndex != -1) {
                Account srcAcc = srcAccounts.get(srcIndex);
                Account dstAcc = dstAccounts.get(dstIndex);
                if (srcAcc.getValue() >= amount) {
                    srcAcc.setValue(srcAcc.getValue() - amount);
                    srcAccounts.set(srcIndex, srcAcc);
                    this.accounts.put(srcUser, srcAccounts);
                    dstAcc.setValue(dstAcc.getValue() - amount);
                    dstAccounts.set(dstIndex, dstAcc);
                    this.accounts.put(dstUser, dstAccounts);
                    success = true;
                }
            }
        }
        return success;
    }
}