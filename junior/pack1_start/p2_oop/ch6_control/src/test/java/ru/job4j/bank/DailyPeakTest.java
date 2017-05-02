package ru.job4j.bank;

import java.util.Date;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Класс DailyPeakTest тестирует класс DailyPeak.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-05-02
 */
public class DailyPeakTest {
    /**
     * Экземпляр класса.
     */
    private static DailyPeak dp;
    /**
     * Начало рабочего дня.
     */
    private int dayStart;
    /**
     * Конец рабочего дня.
     */
    private int dayEnd;
    /**
     * Массив почасовых пиков.
     */
    private int[] visPerHour;
    /**
     * Рандомизатор.
     */
    private Random rnd;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.dayStart = 8;
        this.dayEnd = 20;
        this.visPerHour = new int[this.dayEnd - this.dayStart];
        this.dp = new DailyPeak(this.dayStart, this.dayEnd);
        this.rnd = new Random(new Date().getTime());
        for (int a = 0; a < this.visPerHour.length; a++) {
            if (this.dp.getCurVisitors() != 0) {
                this.visPerHour[a] = this.dp.getCurVisitors();
                int remove = this.rnd.nextInt(this.visPerHour[a]);
                this.visPerHour[a] -= remove;
                this.dp.decrease(remove);
            }
            int add = this.rnd.nextInt(100);
            this.visPerHour[a] += add;
            this.dp.increase(add);
            this.dp.addHour();
        }
        this.dp.decrease(this.dp.getCurVisitors());
    }
    /**
     * Тестирует getInfo().
     */
    @Test
    public void testGetInfo() {
        String result = this.dp.getInfo();
        int peakStart = 0;
        int peak = this.visPerHour[0];
        for (int a = 0; a < this.visPerHour.length; a++) {
            if (this.visPerHour[a] > peak) {
                peak = this.visPerHour[a];
                peakStart = a + this.dayStart;
            }
        }
        int peakEnd = peakStart + 1;
        String expected = String.format("Peak start: %d. Peak end: %d. Quantity: %d", peakStart, peakEnd, peak);
        assertEquals(expected, result);
    }
    /**
     * Тестирует addHour().
     */
    @Test
    public void testAddHour() {
        this.dp.addHour();
        assertEquals(this.dayEnd + 1, this.dp.getHour());
    }
    /**
     * Тестирует getHour().
     */
    @Test
    public void testGetHour() {
        assertEquals(this.dayEnd, this.dp.getHour());
    }
    /**
     * Тестирует getDayEnd().
     */
    @Test
    public void testGetDayEnd() {
        assertEquals(this.dayEnd, this.dp.getDayEnd());
    }
    /**
     * Тестирует getPeakVisitors().
     */
    @Test
    public void testGetPeakVisitors() {
        int peak = this.visPerHour[0];
        for (int a = 0; a < this.visPerHour.length; a++) {
            if (this.visPerHour[a] > peak) {
                peak = this.visPerHour[a];
            }
        }
        assertEquals(peak, this.dp.getPeakVisitors());
    }
    /**
     * Тестирует getCurVisitors().
     */
    @Test
    public void testGetCurVisitors() {
        assertEquals(0, this.dp.getCurVisitors());
    }
    /**
     * Тестирует increase().
     */
    @Test
    public void testIncrease() {
        int expected = this.rnd.nextInt(100);
        this.dp.increase(expected);
        assertEquals(expected, this.dp.getCurVisitors());
    }
    /**
     * Тестирует decrease().
     */
    @Test
    public void testDecrease() {
        int expected = this.rnd.nextInt(100);
        this.dp.increase(expected);
        this.dp.decrease(expected);
        assertEquals(0, this.dp.getCurVisitors());
    }
}