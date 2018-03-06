package tasks.lift;

import java.util.Iterator;
import java.util.LinkedList;
/**
 * Класс CommandQueue реализует сущность Очередь комманд.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-27
 * @since 2018-02-21
 */
class CommandQueue extends LinkedList<String> {
    /**
     * Добавляет команду в конец очереди.
     * @param cmd команда.
     * @return true если команда добавлена в конец очереди. Иначе false.
     */
    @Override
    public synchronized boolean add(String cmd) {
        boolean result = false;
        if (cmd != null && !this.contains(cmd)) {
            result = super.add(cmd);
        }
        return result;
    }
    /**
     * Добавляет команду в начало очереди.
     * @param cmd команда.
     */
    @Override
    public synchronized void addFirst(String cmd) {
        if (cmd != null && !this.contains(cmd)) {
            super.addFirst(cmd);
        }
    }
    /**
     * Добавляет команду в начало очереди.
     * @param cmd команда.
     * @return true если команда добавлена в конец очереди.
     */
    @Override
    public synchronized boolean offerFirst(String cmd) {
        this.addFirst(cmd);
        return true;
    }
    /**
     * Удаляет комманду из очереди комманд.
     * @param cmd удаляемая команда.
     * @return true если команда удалена из очереди. Иначе false.
     */
    public synchronized boolean remove(String cmd) {
        boolean result = false;
        if (cmd != null && this.size() > 0) {
            Iterator<String> iter = this.iterator();
            String cur;
            while (iter.hasNext()) {
                cur = iter.next();
                if (cur.compareTo(cmd) == 0) {
                    iter.remove();
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}