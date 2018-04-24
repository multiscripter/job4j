package ru.job4j.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
/**
 * Класс StubServletOutputStream подменяет в тестах ServletOutputStream.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-04-23
 * @since 2018-04-23
 */
public class StubServletOutputStream extends ServletOutputStream {
    /**
     * Массив байтов.
     */
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    /**
     * Получает массив байтов.
     * @return массив байтов.
     */
    public byte[] getByteArray() {
        return this.baos.toByteArray();
    }
    /**
     * Переопределённый метод.
     * @param i
     * @throws java.io.IOException исключение ввода-вывода.
     */
    @Override
    public void write(int i) throws IOException {
       baos.write(i);
    }
    /**
     * Переопределённый метод.
     * @return true если поток готов. Иначе false;
     */
    @Override
    public boolean isReady() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * Переопределённый метод.
     * @param wl слушаетль.
     */
    @Override
    public void setWriteListener(WriteListener wl) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}