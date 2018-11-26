package ru.job4j.threads;

//import java.util.ArrayList;
/**
 * Класс TextCounter реализует функционал текстовой статистики.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-07-17
 */
class TextCounter {
    /**
     * Обёртка строки.
     */
    class Str {
        /**
         * Строка с текстом.
         */
        private final String str;
        /**
         * Конструктор.
         * @param str строка с текстом.
         */
        Str(String str) {
            this.str = str.trim();
        }
        /**
         * Возвращает символ по индексу из строки.
         * @param index индекс символа в строке.
         * @return символ.
         */
        public char charAt(int index) {
            return this.str.charAt(index);
        }
        /**
         * Возвращает длину строки.
         * @return длина строки.
         */
        public int length() {
            System.out.println("length()");
            return this.str.length();
        }
    }
    /**
     * Экземпляр объекта обёртки строки.
     */
    private Str str;
    /**
     * Конструктор.
     * @param str строка с текстом.
     */
    TextCounter(String str) {
        this.str = new Str(str);
    }
    /**
     * Возвращает количество пробелов в строке.
     * @return количество слов в строке.
     */
    int countSpaces() {
        int count = 0;
        char cur;
        for (int a = 0, size = this.str.length(); a < size; a++) {
            cur = this.str.charAt(a);
            if (cur == ' ') {
                count++;
            }
        }
        return count;
    }
    /**
     * Возвращает количество слов в строке.
     * @return количество слов в строке.
     */
    int countWords() {
        int count = 0;
        char prev = '\0';
        boolean isPrevNonChar = false;
        char cur;
        boolean isCurNonChar = false;
        String chars = new String(".,., !??()");
        //ArrayList<Character> ch = new ArrayList<>();
        for (int a = 0, size = this.str.length(); a < size; a++) {
            cur = this.str.charAt(a);
            isCurNonChar = chars.contains(String.valueOf(cur));
            //ch.add(cur);
            if (!isPrevNonChar && isCurNonChar && !((prev == '-' && cur == ' ') || (prev == ' ' && cur == '-'))) {
                count++;
                //System.out.println("count: " + count + ch.toString());
                //ch.clear();
            }
            prev = cur;
            isPrevNonChar = isCurNonChar;
        }
        return count;
    }
    /**
     * Возвращает длину строки.
     * @return длина строки.
     */
    int length() {
        return this.str.length();
    }
}
