package ru.job4j.loop;

/**
 * Class Paint строит пирамиду в псевдографике.
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-08
 */
public class Paint {
    /**
     * Строит пирамиду из символов ^ и пробелов.
     * @param h высота пирамиды.
     * @return строка, представляющая пирамиду.
     */
    public String piramid(int h) {
        StringBuilder str = new StringBuilder();
        int width = h * 2 - 1;
        for (int tier = 0, curWidth = 1; tier < h; tier++, curWidth += 2) {
            int space = (width - curWidth) / 2;
            for (int left = space; left > 0; left--) {
                str.append(' ');
            }
            for (int block = 0; block < curWidth; block++) {
                str.append('^');
            }
            for (int right = space; right > 0; right--) {
                str.append(' ');
            }
            str.append('\n');
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }
}
