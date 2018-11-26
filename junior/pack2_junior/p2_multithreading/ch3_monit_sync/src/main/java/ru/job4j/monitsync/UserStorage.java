package ru.job4j.monitsync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс UserStorage реализует сущность Хранилище пользователей.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-25
 */
@ThreadSafe
class UserStorage {
    /**
     * Коллекция пользователей.
     */
    private final ArrayList<User> storage;
    /**
     * Объект блокировки (монитора).
     */
    private Object lock;
    /**
     * Конструктор без параметров.
     */
    UserStorage() {
        this.storage = new ArrayList<>();
        this.lock = new Object();
    }
    /**
     * Конструктор.
     * @param collection коллекция с пользователями.
     */
    UserStorage(Collection<? extends User> collection) {
        this.storage = new ArrayList<>(collection);
        this.lock = new Object();
    }
    /**
     * Добавляет пользователя в хранилище.
     * @param user пользователь.
     * @return true если пользователь добавлен в хранилище. Иначе false.
     */
    @GuardedBy("lock")
    public boolean add(User user) {
        synchronized (this.lock) {
            return this.storage.add(user);
        }
    }
    /**
     * Добавляет коллекцию с пользователями в хранилище.
     * @param collection коллекция с пользователями.
     * @return true если коллекция добавлена в хранилище. Иначе false.
     */
    @GuardedBy("lock")
    public boolean addAll(Collection<User> collection) {
        synchronized (this.lock) {
            return this.storage.addAll(collection);
        }
    }
    /**
     * Очищает коллекцию пользователей.
     */
    @GuardedBy("lock")
    public void clear() {
        synchronized (this.lock) {
            this.storage.clear();
        }
    }
    /**
     * Проверяет, содержит ли хранилище пользователя.
     * @param user объект пользователя.
     * @return true если хранилище содержит пользователя. Иначе false.
     */
    @GuardedBy("lock")
    public boolean contains(User user) {
        synchronized (this.lock) {
            return this.storage.contains((Object) user);
        }
    }
    /**
     * Проверяет, содержит ли хранилище пользователей.
     * @param collection коллекция с пользователями.
     * @return true если хранилище содержит пользователей. Иначе false.
     */
    @GuardedBy("lock")
    public boolean containsAll(Collection<User> collection) {
        synchronized (this.lock) {
            return this.storage.containsAll(collection);
        }
    }
    /**
     * Удаляет пользователя из хранилища.
     * @param user объект пользователя.
     * @return true если пользователь удалён из хранилища. Иначе false.
     */
    @GuardedBy("lock")
    public boolean delete(User user) {
        synchronized (this.lock) {
            return this.storage.remove((Object) user);
        }
    }
    /**
     * Удаляет пользователей из хранилища.
     * @param collection коллекция с пользователями.
     * @return true если пользователи из коллекции удалены из хранилища. Иначе false.
     */
    @GuardedBy("lock")
    public boolean deleteAll(Collection<User> collection) {
        synchronized (this.lock) {
            return this.storage.removeAll(collection);
        }
    }
    /**
     * Получает сумму со счетов всех пользователей из хранилища.
     * @return сумма со счетов всех пользователей из хранилища.
     */
    @GuardedBy("lock")
    public int getTotalAmount() {
        Iterator<User> iter = this.iterator();
        int sum = 0;
        while (iter.hasNext()) {
            sum += iter.next().getAmount();
        }
        return sum;
    }
    /**
     * Проверяет, пусто ли хранилище пользователей.
     * @return true если хранилище пусто. Иначе false.
     */
    @GuardedBy("lock")
    public boolean isEmpty() {
        synchronized (this.lock) {
            return this.storage.isEmpty();
        }
    }
    /**
     * Возвращает итератор по хранилищу пользователей.
     * @return итератор по хранилищу пользователей.
     */
    @GuardedBy("lock")
    public Iterator<User> iterator() {
        synchronized (this.lock) {
            return this.storage.iterator();
        }
    }
    /**
     * Возвращает размер хранилища пользователей.
     * @return размер хранилища пользователей.
     */
    @GuardedBy("lock")
    public int size() {
        synchronized (this.lock) {
            return this.storage.size();
        }
    }
    /**
     * Переводит количество amount со счёта пользователя с fromId на счёт пользователя toId.
     * @param fromId идентификатор пользователя-отправителя.
     * @param toId идентификатор пользователя-получателя.
     * @param amount количество, переводимое между счетами.
     * @return true если перевод успешен. Иначе false.
     */
    @GuardedBy("lock")
    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (this.lock) {
            boolean result = false;
            User from = this.storage.get(fromId);
            if (from.getAmount() >= amount) {
                from.setAmount(from.getAmount() - amount);
                User to = this.storage.get(toId);
                to.setAmount(to.getAmount() + amount);
                result = true;
            }
            return result;
        }
    }
    /**
     * Обновляет пользователя в хранилище.
     * @param user заменяемый пользователь.
     * @return true если пользователь обновлён. Иначе выкидывает IndexOutOfBoundsException.
     */
    @GuardedBy("lock")
    public boolean update(User user) {
        synchronized (this.lock) {
            this.storage.add(user.getId(), user);
            return true;
        }
    }
}