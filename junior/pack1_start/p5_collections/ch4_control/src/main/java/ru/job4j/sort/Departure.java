package ru.job4j.sort;

import java.util.List;
/**
 * Class Departure реализует сущность Департамент организации.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-18
 */
class Departure implements Comparable<Departure> {
    /**
     * Название текущего департамента.
     */
    private String name;
    /**
     * Названия департаментов, являющихся прямыми потомками текущего.
     */
    private List<String> deps;
    /**
     * Конструктор.
     * @param name имя департамента.
     */
    Departure(String name) {
        this.name = name;
    }
    /**
     * Получает строку с именем текущего департамента.
     * @return строка с именем текущего департамента.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Добавляет название департамента, являющигося прямым потомком текущего.
     * @param name строка с именем субдепартамента.
     */
    public void addDepsName(String name) {
        this.deps.add(name);
    }
    /**
     * Получает список названий департаментов, являющихся прямыми потомками текущего.
     * @return список названий департаментов, являющихся прямыми потомками текущего.
     */
    public List<String> getDepsNames() {
        return this.deps;
    }
    /**
     * Сравнивает два объекта департамента.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(Departure obj) {
        String[] cName = this.getName().split("K");
        cName[1] = this.getName().substring(this.getName().length() - 2);
        String[] oName = obj.getName().split("K");
        oName[1] = obj.getName().substring(obj.getName().length() - 2);
        int result = cName[1].compareTo(oName[1]);
        System.out.println(this.getName() + " compare " + obj.getName());
        System.out.println("cName[1]: " + cName[1] + " compare oName[1]: " + oName[1] + " result: " + result);
        if (result == 0 && cName[0].length() != oName[0].length()) {
            System.out.println("cName[0].length " + cName[0].length() + " oName[0].length: " + oName[0].length());
            result = cName[0].length() - oName[0].length();
        }
        System.out.println("result: " + result);
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
        return this.name.equals(dep.getName());
    }
    /**
     * Возвращает хэш-код объекта департамента.
     * @return хэш-код объекта департамента.
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
    /**
     * Возвращает строковое представление объект департамента.
     * @return строковое представление объект департамента.
     */
    @Override
    public String toString() {
        return String.format("Dep{name: %s}", this.getName());
    }
}
