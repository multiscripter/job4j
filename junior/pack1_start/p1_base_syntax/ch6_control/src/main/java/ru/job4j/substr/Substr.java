package ru.job4j.substr;

/**
 * Class Substr поиск строки в строке.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-10
 */
public class Substr {
    /**
     * Ищет строку sub в строке origin.
     * @param origin строка, в которой происходит поиск.
     * @param sub строка поиска.
     * @return true в случае успеха, иначе false.
     */
    public boolean contains(String origin, String sub) {
        char[] originChars = origin.toCharArray();
        char[] subChars = sub.toCharArray();
        boolean contains = false;
        int b = 0;
        for (int a = 0; a < originChars.length; a++) {
            if (originChars[a] == subChars[b]) {
                contains = true;
                b++;
                if (subChars.length == b) {
                    break;
                }
            } else {
                contains = false;
                b = 0;
            }
        }
        if (b != subChars.length) {
            contains = false;
        }
        return contains;
    }
}