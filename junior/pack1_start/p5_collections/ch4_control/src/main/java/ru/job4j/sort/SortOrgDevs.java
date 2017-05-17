package ru.job4j.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
/**
 * Class SortOrgDevs. Сортировка подразделений организации.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-17
 */
class SortOrgDevs {
    /**
     * Массив строк с названиями подразделений организации.
     */
    private String[] orgDevsNames;
    /**
     * Деревянное множество с названиями подразделений, педставляющих иерархию организации.
     */
    private TreeSet<String> orgDevsHierarchy;
    /**
     * Конструктор.
     * @param orgDevsNames массив строк с названиями подразделений организации.
     */
    SortOrgDevs(String[] orgDevsNames) {
        this.orgDevsNames = orgDevsNames;
        this.orgDevsHierarchy = new TreeSet<>();
        this.fillTree(this.orgDevsNames);
    }
    /**
     * Получает массив строк с названиями подразделений организации.
     * @return массив строк с названиями подразделений организации.
     */
    String[] getOrgDevsNames() {
        return this.orgDevsNames;
    }
    /**
     * Заполняет деревянное множество названиями подразделений организации.
     * @param strsNames массив строк с названиями подразделений организации.
     */
    private void fillTree(String[] strsNames) {
        for (String strNames : strsNames) {
            String[] names = strNames.split("\\\\");
            StringBuilder fullName = new StringBuilder();
            for (int a = 0; a < names.length; a++, fullName.append("\\")) {
                fullName.append(names[a]);
                this.orgDevsHierarchy.add(fullName.toString());
            }
        }
    }
    /**
     * Возвращает массив названий, отсортированных в порядке возрастания.
     * @return strsNames массив строк с названиями подразделений организации.
     */
    public String[] getNamesOrderAsc() {
        return this.orgDevsHierarchy.toArray(new String[this.orgDevsHierarchy.size()]);
    }
    /**
     * Возвращает массив названий, отсортированных в порядке убывания.
     * @return strsNames массив строк с названиями подразделений организации.
     */
    public String[] getNamesOrderDesc() {
        List<String> list = Arrays.asList(this.getNamesOrderAsc());
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String name1, String name2) {
                int result = 0, name1L = name1.length(), name2L = name2.length();
                if (name1L == name2L) {
                    result = name2.compareTo(name1);
                } else if (name1L < name2L) {
                    for (int a = 0; a < name1L; a++) {
                        if (name1.charAt(a) != name2.charAt(a)) {
                            result = name1.charAt(a) < name2.charAt(a) ? 1 : -1;
                            break;
                        }
                    }
                } else {
                    for (int a = 0; a < name2L; a++) {
                        if (name1.charAt(a) != name2.charAt(a)) {
                            result = name1.charAt(a) < name2.charAt(a) ? 1 : -1;
                            break;
                        }
                    }
                }
                result = result != 0 ? result : 1;
                return result;
            }
        });
        return list.toArray(new String[list.size()]);
    }
}