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
 * @version 3
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
            System.err.println("NoSuchFileException.");
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            System.err.println("UnsupportedEncodingException.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.err.println("IOException.");
            ex.printStackTrace();
        }
    }
    /**
     * Тестирует работу методов в многопоточном режиме.
     */
    @Test
    public void testConcurrency() {
        System.out.println("#1017 junior.pack2.p7.ch1.task2 'Create waiting of output'");
        Thread t1 = new Thread() {
            @Override
            public void run() {
                String tname = Thread.currentThread().getName();
                System.out.println(String.format("%s runs.", tname));
                System.out.println(String.format("%s ends. Words: %d.", tname, TextCounterTest.this.tc1.countWords()));
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                String tname = Thread.currentThread().getName();
                System.out.println(String.format("%s runs.", tname));
                System.out.println(String.format("%s ends. Spaces: %d.", tname, TextCounterTest.this.tc2.countWords()));
            }
        };
        t1.setName("Word counting thread");
        t2.setName("Space counting thread");
        t1.start();
        t2.start();
        long start = System.currentTimeMillis();
        while (!t1.isInterrupted() || !t2.isInterrupted()) {
            if (System.currentTimeMillis() > start + 1000) {
                if (!t1.isInterrupted()) {
                    System.out.println(String.format("Execution time of '%s' more then 1 sec.", t1.getName()));
                }
                if (!t2.isInterrupted()) {
                    System.out.println(String.format("Execution time of '%s' more then 1 sec.", t2.getName()));
                }
                t1.interrupt();
                t2.interrupt();
                break;
            }
        }
        System.out.println("End task");
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
