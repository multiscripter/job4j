package ru.job4j.sort;

import java.util.List;
/**
 * Class Departure реализует сущность Департамент организации.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-18
 */
class Departure {
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
    public void addSubdepName(String name) {
        this.deps.add(name);
    }
    /**
     * Получает список названий департаментов, являющихся прямыми потомками текущего.
     * @return список названий департаментов, являющихся прямыми потомками текущего.
     */
    public List<String> getSubdepNames() {
        return this.deps;
    }
}