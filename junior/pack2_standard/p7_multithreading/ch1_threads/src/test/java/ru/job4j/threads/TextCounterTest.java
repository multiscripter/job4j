package ru.job4j.threads;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.nio.file.Files;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Класс TextCounterTest тестирует класс TextCounter.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 2017-07-17
 */
public class TextCounterTest {
    /**
     * Объект TextCounter.
     */
    private TextCounter tc1;
    /**
     * Объект TextCounter.
     */
    private TextCounter tc2;
    /**
     * Действия перед тестом.
     */
    @Before
    public void beforeTest() {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get("src/main/java/ru/job4j/threads/textRu.txt"));
            this.tc1 = new TextCounter(new String(encoded, "UTF-8"));
            this.tc2 = new TextCounter(new String(encoded, "UTF-8"));
        } catch (NoSuchFileException ex) {
            System.out.println("NoSuchFileException.");
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            System.out.println("UnsupportedEncodingException.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IOException.");
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует работу методов в многопоточном режиме.
     */
    @Test
    public void testConcurrency() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread 1. Words: " + TextCounterTest.this.tc1.countWords());
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread 2: Spaces: " + TextCounterTest.this.tc2.countSpaces());
            }
        };
        t1.start();
        t2.start();
    }
    /**
     * Тестирует int countSpaces().
     */
    @Test
    public void testCountSpaces() {
        assertEquals(67, this.tc1.countSpaces());
    }
    /**
     * Тестирует int countWords().
     */
    @Test
    public void testCountWords() {
        assertEquals(67, this.tc1.countWords());
    }
    /**
     * Тестирует int length().
     */
    @Test
    public void testLength() {
        assertEquals(552, this.tc1.length());
    }
    /**
     * Тестирует void setString(String str) и конструктор TextCounter(String str).
     */
    @Test
    public void testSetString() {
        TextCounter tc3 = new TextCounter();
        String str = new String("Appends the specified element to the end of this list.");
        tc3.setString(str);
        assertEquals(str.length(), tc3.length());
    }
}