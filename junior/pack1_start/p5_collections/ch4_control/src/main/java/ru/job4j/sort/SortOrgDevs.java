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
     * Деревянное множество с названиями подразделений, педставляющих иерархию организации.
     */
    private TreeSet<Departure> orgDevsHierarchy;
    /**
     * Конструктор.
     * @param orgDevsNames массив строк с названиями подразделений организации.
     */
    SortOrgDevs(String[] orgDevsNames) {
        this.orgDevsHierarchy = new TreeSet<>();
        this.fillTree(orgDevsNames);
    }
    /**
     * Заполняет деревянное множество названиями подразделений организации.
     * @param strsNames массив строк с названиями подразделений организации.
     */
    private void fillTree(String[] strsNames) {
        for (String strNames : strsNames) {
            String[] names = strNames.split("\\\\");
            //StringBuilder fullName = new StringBuilder();
            for (int a = 0; a < names.length; a++) {
                //fullName.append(names[a]);
                this.orgDevsHierarchy.add(new Departure(names[a]));
            }
        }
        System.out.println("this.orgDevsHierarchy.size(): " + this.orgDevsHierarchy.size());
    }
    /**
     * Возвращает массив названий, отсортированных в порядке возрастания.
     * @return strsNames массив строк с названиями подразделений организации.
     */
    public String[] getNamesOrderAsc() {
        Departure[] deps = this.orgDevsHierarchy.toArray(new Departure[this.orgDevsHierarchy.size()]);
        String[] depsNames = new String[deps.length];
        for (int a = 0; a < deps.length; a++) {
            depsNames[a] = deps[a].getName();
        }
        return depsNames;
        //return this.orgDevsHierarchy.toArray(new String[this.orgDevsHierarchy.size()]);
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
