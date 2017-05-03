package ru.job4j.bank;

//import java.util.Date;
//import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Класс DailyPeakTest тестирует класс DailyPeak.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2
 * @since 2017-05-02
 */
public class DailyPeakTest {
    /**
     * Экземпляр класса.
     */
    private static DailyPeak dp;
    /**
     * Массив почасовых пиков.
     */
    private int[] visPerHour;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        this.visPerHour = new int[] {21, 96, 94, 115, 50, 71, 159, 232, 135, 123, 46, 67};
        this.dp = new DailyPeak(8, 20);
        for (int a = 0; a < this.visPerHour.length; a++) {
            if (this.dp.getCurVisitors() != 0) {
            //    this.visPerHour[a] = this.dp.getCurVisitors();
            //    int remove = this.rnd.nextInt(this.visPerHour[a]);
            //    this.visPerHour[a] -= remove;
                this.dp.decrease(this.visPerHour[a - 1]);
            }
            //int add = this.rnd.nextInt(100);
            //this.visPerHour[a] += add;
            this.dp.increase(this.visPerHour[a]);
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
                peakStart = a + 8;
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
        assertEquals(21, this.dp.getHour());
    }
    /**
     * Тестирует getHour().
     */
    @Test
    public void testGetHour() {
        assertEquals(20, this.dp.getHour());
    }
    /**
     * Тестирует getDayEnd().
     */
    @Test
    public void testGetDayEnd() {
        assertEquals(20, this.dp.getDayEnd());
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
        int expected = 97;
        this.dp.increase(expected);
        assertEquals(expected, this.dp.getCurVisitors());
    }
    /**
     * Тестирует increase().
     */
    @Test
    public void testIncrease() {
        int expected = 112;
        this.dp.increase(expected);
        assertEquals(expected, this.dp.getCurVisitors());
    }
    /**
     * Тестирует decrease().
     */
    @Test
    public void testDecrease() {
        int expected = 0;
        this.dp.increase(109);
        this.dp.decrease(109);
        assertEquals(expected, this.dp.getCurVisitors());
    }
}