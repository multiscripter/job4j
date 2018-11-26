package ru.job4j.sort;

import java.util.LinkedList;
import java.util.List;
/**
 * Class Departure реализует сущность Департамент организации.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-18
 */
class Departure implements Comparable<Departure> {
    /**
     * Название текущего департамента.
     */
    private String name;
    /**
     * Название текущего департамента вместе с предками.
     */
    private String fullName;
    /**
     * Названия департаментов, являющихся прямыми потомками текущего.
     */
    private List<String> subDeps;
    /**
     * Конструктор.
     * @param fullName название текущего департамента вместе с предками.
     */
    Departure(String fullName) {
        this.fullName = fullName;
        String[] names = this.fullName.split("\\\\");
        this.name = names[names.length - 1];
        this.subDeps = new LinkedList();
    }
    /**
     * Получает строку с именем текущего департамента.
     * @return строка с именем текущего департамента.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Получает строку с именем текущего департамента вместе с предками.
     * @return строка с именем текущего департамента вместе с предками.
     */
    public String getFullName() {
        return this.fullName;
    }
    /**
     * Добавляет название департамента, являющигося прямым потомком текущего.
     * @param name строка с именем субдепартамента.
     */
    public void addSubDepName(String name) {
        if (!this.subDeps.contains(name)) {
            this.subDeps.add(name);
        }
    }
    /**
     * Получает список названий департаментов, являющихся прямыми потомками текущего.
     * @return список названий департаментов, являющихся прямыми потомками текущего.
     */
    public List<String> getSubDepsNames() {
        return this.subDeps;
    }
    /**
     * Сравнивает два объекта департамента.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(Departure obj) {
        int result = this.getFullName().compareTo(obj.getFullName());
        return result;
    }
    /**
     * Проверяет объекты департаментов на равенство.
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
        Departure dep = (Departure) obj;
        return this.fullName.equals(dep.getFullName());
    }
    /**
     * Возвращает хэш-код объекта департамента.
     * @return хэш-код объекта департамента.
     */
    @Override
    public int hashCode() {
        return this.fullName.hashCode();
    }
    /**
     * Возвращает строковое представление объект департамента.
     * @return строковое представление объект департамента.
     */
    @Override
    public String toString() {
        return String.format("Dep{fullName: %s}", this.getFullName());
    }
}
