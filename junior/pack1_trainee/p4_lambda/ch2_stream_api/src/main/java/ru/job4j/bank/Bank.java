package ru.job4j.bank;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
/**
 * Класс Bank реализует сущность Банк.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-12
 * @since 2017-05-15
 */
public class Bank {
    /**
     * Список счетов пользователя.
     */
    private TreeMap<User, List<Account>> accounts;
    /**
     * Конструктор.
     */
    public Bank() {
        this.accounts = new TreeMap<>();
    }
    /**
     * Добавляет счёт пользователя.
     * @param user пользователь.
     * @param account счёт пользователя.
     */
    public void addAccountToUser(User user, Account account) {
        List<Account> userAccounts = this.accounts.get(user);
        if (userAccounts == null) {
            userAccounts = new LinkedList<>();
        }
        userAccounts.add(account);
        this.accounts.put(user, userAccounts);
    }
    /**
     * Добавляет пользователя.
     * @param user пользователь.
     */
    public void addUser(User user) {
        this.accounts.put(user, new LinkedList<>());
    }
    /**
     * Удаляет счёт пользователя.
     * @param user пользователь.
     * @param account счёт пользователя.
     */
    public void deleteAccountFromUser(User user, Account account) {
        List<Account> userAccounts = this.accounts.get(user);
        userAccounts.remove(account);
    }
    /**
     * Удаляет пользователя.
     * @param user пользователь.
     */
    public void deleteUser(User user) {
        this.accounts.remove(user);
    }
    /**
     * Получает пользователей.
     * @return список пользователей.
     */
    public List<User> getUsersAsList() {
        return this.accounts.keySet().stream().collect(Collectors.toList());
    }
    /**
     * Получает пользователя.
     * @param passport паспорт пользователя.
     * @return пользователь.
     */
    public User getUserByPassport(String passport) {
        Optional<User> oUser = this.accounts.keySet().stream().filter(x -> x.getPassport().equals(passport)).findFirst();
        return oUser.isPresent() ? oUser.get() : null;
    }
    /**
     * Получает счёт по паспорту и реквизитам.
     * @param passport паспорт пользователя.
     * @param requisites реквизитам счёта.
     * @return счёт.
     */
    public Account getAccountByRequisitesAndPassport(final String passport, final String requisites) {
        Account acc = null;
        if (passport != null && requisites != null) {
            User user = this.getUserByPassport(passport);
            if (user != null) {
                Optional<Account> oAcc = this.accounts.get(user).stream().filter(x -> x.getRequisites().equals(requisites)).findFirst();
                acc = oAcc.isPresent() ? oAcc.get() : null;
            }
        }
        return acc;
    }
    /**
     * Получает список со счетами.
     * @return список со счетами.
     */
    public List<Account> getAccounts() {
        List<Account> accounts = new LinkedList<>();
        this.accounts.values().forEach(accounts::addAll);
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
        Account srcAcc = this.getAccountByRequisitesAndPassport(srcUser.getPassport(), srcAccount.getRequisites());
        Account dstAcc = this.getAccountByRequisitesAndPassport(dstUser.getPassport(), dstAccount.getRequisites());
        if (srcAcc != null && dstAcc != null && srcAcc.getValue() >= amount) {
            srcAcc.setValue(srcAcc.getValue() - amount);
            dstAcc.setValue(dstAcc.getValue() - amount);
            success = true;
        }
        return success;
    }
}