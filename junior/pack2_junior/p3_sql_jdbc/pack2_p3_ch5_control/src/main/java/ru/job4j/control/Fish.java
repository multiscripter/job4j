package ru.job4j.control;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс Fish реализует сущность Рыба.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-10-09
 */
@ThreadSafe
class Fish extends Thread {
    /**
     * Дата рождения.
     */
    private final int birthDate;
    /**
     * Последнеe значение идентификатора особи.
     */
    private static int counter = 0;
    /**
     * Объект аквариума.
     */
    private final Aquarium aq;
    /**
     * Пол особи. 0 - самец, 1 - самка.
     */
    private final int gender;
    /**
     * Идентификатор особи.
     */
    private final int id;
    /**
     * Продолжительность жизни.
     */
    private final int lifetime;
    /**
     * Объект блокировки (монитор).
     */
    private final ReentrantLock lock;
    /**
     * Свойства вида.
     */
    private final Properties props;
    /**
     * Таймер.
     */
    private final Timer timer;
    /**
     * Корструктор.
     * @param aq объект аквариума.
     * @param gender пол.
     * @param lifetime продолжительность жизни.
     * @param props свойства вида.
     * @throws IOException ошибка ввода-вывода.
     */
    Fish(Aquarium aq, int gender, int lifetime, final Properties props) throws IOException {
        this.aq = aq;
        this.birthDate = (int) (System.currentTimeMillis() / 1000);
        this.gender = gender;
        this.id = ++Fish.counter;
        this.props = props;
        this.lifetime = lifetime;
        this.lock = new ReentrantLock();
        this.timer = new Timer();
    }
    /**
     * Родить детей.
     * @param fish самец.
     * @throws IOException исключение ввода-вывода.
     * @return true если дети добавлены в аквариум. Иначе false.
     */
    @GuardedBy("lock")
    private boolean birthKids(Fish fish) throws IOException {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                Fish[] fishes = new Fish[this.getKidsMin() + ThreadLocalRandom.current().nextInt(this.getKidsMax() - this.getKidsMin())];
                for (int a = 0; a < fishes.length; a++) {
                    fishes[a] = new Fish(this.aq, ThreadLocalRandom.current().nextInt(2), this.getLifeTime(), this.props);
                }
                List<Fish> fishesList = this.aq.addAll(Arrays.asList(fishes));
                if (fishesList != null) {
                    System.out.format("%s meets with %s%n", this.toString(), fish.toString());
                    //System.out.println("fishesList.size(): " + fishesList.size());
                    this.aq.live(fishesList);
                }
            } catch (IOException ex) {
                throw ex;
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Умереть.
     * @throws InterruptedException исключение прерывания потока.
     */
    private void die() throws InterruptedException {
        try {
            this.timer.cancel();
            while (!this.aq.getFishList().remove(this)) {
                Thread.currentThread().sleep(1);
            }
            System.out.format("%s died%n", this.toString());
        } catch (InterruptedException ex) {
            throw ex;
        }
    }
    /**
     * Получает возраст полового созревания.
     * @return возраст полового созревания.
     */
    public int getAdult() {
        return Integer.parseInt(this.props.getProperty("adult"));
    }
    /**
     * Получает возраст.
     * @return возраст.
     */
    public int getAge() {
        return (int) (System.currentTimeMillis() / 1000) - this.birthDate;
    }
    /**
     * Получает год рождения особи.
     * @return год рождения особи.
     */
    public int getBirthDate() {
        return this.birthDate;
    }
    /**
     * Получает пол особи.
     * @return пол особи.
     */
    public int getGender() {
        return this.gender;
    }
    /**
     * Получает идентификатор особи.
     * @return идентификатор особи.
     */
    public int getIdentity() {
        return this.id;
    }
    /**
     * Получает последнеe значение идентификатора особи.
     * @return последнеe значение идентификатора особи.
     */
    public static int getLastId() {
        return counter;
    }
    /**
     * Получает минимальный возраст жизни в годах.
     * @return минимальный возраст жизни в годах.
     */
    public int getLifeMin() {
        return Integer.parseInt(this.props.getProperty("lifeMin"));
    }
    /**
     * Получает максимальный возраст жизни.
     * @return максимальный возраст жизни.
     */
    public int getLifeMax() {
        return Integer.parseInt(this.props.getProperty("lifeMax"));
    }
    /**
     * Получает продолжительность жизни.
     * @return продолжительность жизни.
     */
    private int getLifeTime() {
        return Integer.parseInt(this.props.getProperty("lifeMin")) + ThreadLocalRandom.current().nextInt(Integer.parseInt(this.props.getProperty("lifeMax")) - Integer.parseInt(this.props.getProperty("lifeMin")));
    }
    /**
     * Получает минимальное количество икринок.
     * @return минимальное количество икринок.
     */
    public int getKidsMin() {
        return Integer.parseInt(this.props.getProperty("kidsMin"));
    }
    /**
     * Получает максимальное количество икринок.
     * @return максимальное количество икринок.
     */
    public int getKidsMax() {
        return Integer.parseInt(this.props.getProperty("kidsMax"));
    }
    /**
     * Получает продолжительность жизни.
     * @return продолжительность жизни.
     */
    public int getLifetime() {
        return this.lifetime;
    }
    /**
     * Получает название вида.
     * @return название вида.
     */
    public String getType() {
        return this.props.getProperty("type");
    }
    /**
     * Взрослая ли особь.
     * @return true если особь взрослая. Иначе false.
     */
    public boolean isAdult() {
        return (int) (System.currentTimeMillis() / 1000) - this.birthDate >= this.getAdult() ? true : false;
    }
    /**
     * Наделать детей.
     * @throws IOException исключение ввода-вывода.
     * @return true если самка оплодотворена. Иначе false.
     */
    private boolean makeKids() throws IOException {
        try {
            boolean result = false;
            FishList list = this.aq.getFishList();
            Fish fish = null;
            try {
                do {
                    fish = list.get(ThreadLocalRandom.current().nextInt(list.size()));
                } while (fish == null || fish.getGender() == this.getGender());
                result = fish.birthKids(this);
            } catch (IndexOutOfBoundsException | FishListIsFullException | IllegalArgumentException ex) {
                result = false;
            }
            return result;
        } catch (IOException ex) {
            throw ex;
        }
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        System.out.format("%s was born%n", Fish.this.toString());
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (Fish.this.getAge() > Fish.this.getLifetime()) {
                        Fish.this.die();
                    } else if (Fish.this.isAdult() && Fish.this.getGender() == 0) {
                        Fish.this.makeKids();
                    }
                } catch (InterruptedException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }, 0, 1000);
    }
    /**
     * Генерирует строковое представление объекта.
     * @return строковое представление объекта.
     */
    @Override
    public String toString() {
        return String.format("%s#%d", this.getType(), this.getIdentity());
    }
}