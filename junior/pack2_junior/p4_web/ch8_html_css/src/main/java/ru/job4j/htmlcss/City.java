package ru.job4j.htmlcss;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
/**
 * Класс City реализует сущность Город.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2017-12-08
 * @since 2017-12-08
 */
public class City implements Comparable<City> {
    /**
     * Список идентификаторов стран.
     */
    private LinkedList<Integer> countriesIds;
    /**
     * Идентификатор.
     */
    private int id;
    /**
     * Название.
     */
    private String name;
    /**
     * Конструктор.
     * @param countriesIds список идентификаторов стран.
     * @param id идентификатор.
     * @param name название.
     */
    City(final int id, final String name, final LinkedList<Integer> countriesIds) {
        this.id = id;
        this.name = name;
        this.countriesIds = countriesIds;
    }
    /**
     * Добавляет идентификаторов страны в список.
     * @param countryId идентификаторов страны.
     */
    public void addCountryId(int countryId) {
        this.countriesIds.add(countryId);
    }
    /**
     * Сравнивает текущий объект города с объектом из параметра.
     * @param obj объект города, с которым производится сравнение.
     * @return 0 - если города равны. 1 - если текущий больше другого. -1 - если текущий меньше другого.
     */
    public int compareTo(City obj) {
        int result = 1;
        if (obj != null) {
            result = this.name.compareTo(obj.getName());
        }
        return result;
    }
    /**
     * Сравнивает объекты.
     * @param obj целевой объект, с которым сравнивается текущий объект.
     * @return true если объекты равны. Иначе false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        City city = (City) obj;
        return this.id == city.getId() && this.name.equals(city.getName());
    }
    /**
     * Получает массив стран.
     * @return массив стран.
     */
    public LinkedList<Integer> getCountriesIdsAsList() {
        return this.countriesIds;
    }
    /**
     * Получает массив стран в виде строки.
     * @return массив стран в виде строки.
     */
    public String getCountriesIds() {
        String result = null;
        if (!this.countriesIds.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Iterator iter = this.countriesIds.iterator();
            sb.append(iter.next());
            while (iter.hasNext()) {
                sb.append(',').append(iter.next());
            }
            result = sb.toString();
        }
        return result;
    }
    /**
     * Получает идентификатор.
     * @return идентификатор.
     */
    public int getId() {
        return this.id;
    }
    /**
     * Получает название.
     * @return название.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Возвращает хэш-код.
     * @return хэш-код.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
    /**
     * Получает список идентификаторов стран.
     * @param countriesIds список идентификаторов стран.
     */
    public void setCountries(final LinkedList<Integer> countriesIds) {
        this.countriesIds = countriesIds;
    }
    /**
     * Устанавливает идентификатор.
     * @param id идентификатор.
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * Устанавливает название.
     * @param name название.
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * Возвращает строковое представление.
     * @return строковое представление.
     */
    @Override
    public String toString() {
        return String.format("City[id: %d, name: %s]", this.id, this.name);
    }
}