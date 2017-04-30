package ru.job4j.bank;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Класс DailyPeak вычисляет период дневного пика посещений банка.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-30
 */
class DailyPeak {
    /**
     * Экземпляр класса.
     */
    private static DailyPeak instance;
    /**
     * Начало рабочего дня.
     */
    private int dayStart;
    /**
     * Конец рабочего дня.
     */
    private int dayEnd;
    /**
     * Начало пика посещения.
     */
    private int peakStart;
    /**
     * Конец пика посещения.
     */
    private int peakEnd;
    /**
     * Количество поситителей банка во время пика.
     */
    private int peakVisitors;
    /**
     * Количество поситителей банка в текущий момент.
     */
    private int curVisitors;
    /**
     * Конструктор.
     * @param dayStart начало рабочего дня.
     * @param dayEnd конец рабочего дня.
     */
    private DailyPeak(int dayStart, int dayEnd) {
        this.peakStart = dayStart;
        this.peakEnd = dayStart;
        this.peakVisitors = 0;
        this.curVisitors = 0;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
    }
    /**
     * Возвращает экземпляр класса.
     * @param dayStart начало рабочего дня.
     * @param dayEnd конец рабочего дня.
     * @return экземпляр класса.
     */
    public static DailyPeak getInstance(int dayStart, int dayEnd) {
        if (instance == null) {
            instance = new DailyPeak(dayStart, dayEnd);
        }
        return instance;
    }
    /**
     * Прибавляет час.
     */
    public void addHour() {
        this.dayStart++;
    }
    /**
     * Возвращает текущий час.
     * @return текущий час.
     */
    public int getHour() {
        return this.dayStart;
    }
    /**
     * Возвращает час, отмечающий конец рабочего дня.
     * @return час.
     */
    public int getDayEnd() {
        return this.dayEnd;
    }
    /**
     * Возвращает количество поситителей в пике.
     * @return количество поситителей в пике.
     */
    public int getPeakVisitors() {
        return this.peakVisitors;
    }
    /**
     * Возвращает количество текущих поситителей.
     * @return количество текущих поситителей.
     */
    public int getCurVisitors() {
        return this.curVisitors;
    }
    /**
     * Добавляет поситителей в список когда тот пришёл в банк.
     * @param visitors количество поситителей.
     */
    public void increase(int visitors) {
        this.curVisitors += visitors;
        if (this.curVisitors > this.peakVisitors) {
            this.peakVisitors = this.curVisitors;
            this.peakStart = this.dayStart;
        }
    }
    /**
     * Удаляет поситителей из списка когда тот ушёл из банка.
     * @param visitors количество поситителей.
     */
    public void decrease(int visitors) {
        this.curVisitors -= visitors;
        if (this.peakStart >= this.peakEnd) {
            this.peakEnd = this.dayStart;
        }
    }
    /**
     * Возвращает информацию о пике поситителей за день.
     * @return информация о пике.
     */
    public String getInfo() {
        StringBuilder str = new StringBuilder();
        str.append("Начало пика: ");
        str.append(this.peakStart);
        str.append(". Конец пика: ");
        str.append(this.peakEnd);
        str.append(". Количество: ");
        str.append(this.peakVisitors);
        return str.toString();
    }
    /**
     * Точка входа в программу.
     * @param args массив аргументов запуска.
     */
    public static void main(String[] args) {
        DailyPeak dp = DailyPeak.getInstance(8, 20);
        Timer timer = new Timer();
        VisGenTask vgt = new VisGenTask();
        vgt.setDailyPeak(dp);
        timer.schedule(vgt, 0, 1000);
    }
}

/**
 * Класс VisGenTask реализует "Генератор поситителей".
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-04-30
 */
class VisGenTask extends TimerTask {
    /**
     * Экземпляр класса Timer.
     */
    private Timer timer;
    /**
     * Экземпляр класса DailyPeak.
     */
    private DailyPeak dp;
    /**
     * Устанавливет экземпляр класса Timer.
     * @param timer экземпляр класса Timer.
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    /**
     * Устанавливет экземпляр класса DailyPeak.
     * @param dp экземпляр класса DailyPeak.
     */
    public void setDailyPeak(DailyPeak dp) {
        this.dp = dp;
    }
    /**
     * Переопределение run().
     */
    @Override
    public void run() {
        if (this.dp.getHour() >= this.dp.getDayEnd()) {
            this.dp.decrease(this.dp.getCurVisitors());
            this.cancel();
            System.out.println(this.dp.getInfo());
            System.exit(0);
        }
        Random rnd = new Random(new Date().getTime());
        if (this.dp.getCurVisitors() != 0) {
            this.dp.decrease(rnd.nextInt(this.dp.getCurVisitors()));
        }
        this.dp.increase(rnd.nextInt(100));
        StringBuilder str = new StringBuilder();
        str.append("Period: ");
        str.append(String.format("%2d-%-2d", this.dp.getHour(), this.dp.getHour() + 1));
        str.append(". cur: ");
        str.append(String.format("%3d", this.dp.getCurVisitors()));
        str.append(". peak: ");
        str.append(String.format("%3d", this.dp.getPeakVisitors()));
        System.out.println(str.toString());
        this.dp.addHour();
    }
}
