package ru.job4j.bank;

import java.util.Objects;
/**
 * Класс Account реализует сущность Банковский счёт.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-12-11
 * @since 2017-05-15
 */
class Account {
    /**
     * Номер счёта.
     */
    private String requisites;
    /**
     * Количество денег на счёте.
     */
    private double value;
    /**
     * Конструктор.
     * @param value количество денег.
     * @param requisites номер счёта.
     */
    Account(String requisites, double value) {
        this.requisites = requisites;
        this.value = value;
    }
    /**
     * Сравнивает объекты банковского счёта.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты одинаковые, иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Account account = (Account) obj;
        return this.requisites.equals(account.getRequisites()) && Double.compare(this.value, account.getValue()) == 0;
    }
    /**
     * Возвращает номер счёта.
     * @return номер счёта.
     */
    public String getRequisites() {
        return this.requisites;
    }
    /**
     * Возвращает количество денег на счёте.
     * @return количество денег на счёте.
     */
    public double getValue() {
        return this.value;
    }
    /**
     * Возвращает хэш-код банковского счёта.
     * @return хэш-код банковского счёта.
     */
    @Override
    public int hashCode() {
        return Objects.hash(requisites, value);
    }
    /**
     * Устанавливает количество денег на счёте.
     * @param value количество денег на счёте.
     */
    public void setValue(double value) {
        this.value = value;
    }
    /**
     * Генерирует строковое представление объектa банковского счёта.
     * @return строковое представление объектa банковского счёта.
     */
    @Override
    public String toString() {
        return String.format("Account{requisites: %s, value: %.2f}", this.getRequisites(), this.getValue());
    }
}
