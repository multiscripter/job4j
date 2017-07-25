package ru.job4j.threads;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
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
            URI uri = this.getClass().getClassLoader().getResource("textRu.txt").toURI();
            byte[] encoded = Files.readAllBytes(Paths.get(uri));
            this.tc1 = new TextCounter(new String(encoded, "UTF-8"));
            this.tc2 = new TextCounter(new String(encoded, "UTF-8"));
        } catch (URISyntaxException ex) {
            System.out.println("URISyntaxException.");
            ex.printStackTrace();
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
}