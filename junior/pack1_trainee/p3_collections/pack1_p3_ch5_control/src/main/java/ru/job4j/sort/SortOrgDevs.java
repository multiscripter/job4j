package ru.job4j.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
/**
 * Класс SortOrgDevs. Сортировка подразделений организации.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-17
 */
class SortOrgDevs {
    /**
     * Деревянное множество с названиями подразделений, педставляющих иерархию организации.
     */
    private TreeSet<Departure> deps;
    /**
     * Конструктор.
     * @param orgDevsNames массив строк с названиями подразделений организации.
     */
    SortOrgDevs(String[] orgDevsNames) {
        this.deps = new TreeSet<>();
        this.fillTree(orgDevsNames);
    }
    /**
     * Получает объект департамента по полному имени.
     * @param fullName название текущего департамента вместе с предками.
     * @return объект департамента.
     */
    public Departure getDepByFullName(String fullName) {
        Departure dep = null;
        Iterator<Departure> iter = this.deps.iterator();
        while (iter.hasNext()) {
            dep = iter.next();
            if (dep.getFullName().equals(fullName)) {
                iter.remove();
                break;
            }
        }
        return dep;
    }
    /**
     * Заполняет деревянное множество названиями подразделений организации.
     * @param strsNames массив строк с названиями подразделений организации.
     */
    private void fillTree(String[] strsNames) {
        for (String strNames : strsNames) {
            String[] names = strNames.split("\\\\");
            StringBuilder fullName = new StringBuilder();
            Departure parentDep = null;
            for (int a = 0; a < names.length; a++, fullName.append("\\")) {
                if (a > 0) {
                    String parentName = fullName.substring(0, fullName.length() - 1);
                    parentDep = this.getDepByFullName(parentName);
                }
                fullName.append(names[a]);
                if (parentDep != null) {
                    parentDep.addSubDepName(fullName.toString());
                    this.deps.add(parentDep);
                }
                Departure dep = new Departure(fullName.toString());
                if (!this.deps.contains(dep)) {
                    this.deps.add(dep);
                }
            }
        }
    }
    /**
     * Возвращает массив названий, отсортированных в порядке возрастания.
     * @return strsNames массив строк с названиями подразделений организации.
     */
    public String[] getNamesOrderAsc() {
        Departure[] deps = this.deps.toArray(new Departure[this.deps.size()]);
        String[] depsNames = new String[deps.length];
        for (int a = 0; a < deps.length; a++) {
            depsNames[a] = deps[a].getFullName();
        }
        return depsNames;
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
