package tasks.lift;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * LiftChecker реализует сущность Проверятель лифта.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-27
 * @since 2018-02-05
 */
class LiftChecker extends Thread {
    /**
     * Адрес сервера.
     */
    private final String address;
    /**
     * Буфер данных.
     */
    private final ByteBuffer bBuf;
    /**
     * Синглтон.
     */
    private static LiftChecker instance;
    /**
     * Номер порта сервера.
     */
    private final int port;
    /**
     * Селектор.
     */
    private final Selector selector;
    /**
     * Текущий статус лифта.
     */
    private final int[] status;
    /**
     * Канал сокета.
     */
    private final SocketChannel sockCh;
    /**
     * Конструктор.
     * @param address адрес сервера.
     * @param port номер порта сервера.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    private LiftChecker(final String address, final int port) throws IOException {
        this.address = address;
        this.bBuf = ByteBuffer.allocate(8);
        this.port = port;
        this.selector = Selector.open();
        this.sockCh = SocketChannel.open();
        this.sockCh.configureBlocking(false);
        this.setDaemon(true);
        this.status = new int[]{0, 0};
    }
    /**
     * Закрывает соединение.
     */
    public void close() {
        try {
            this.selector.close();
            this.sockCh.close();
        } catch (IOException ex) {
            System.err.println("IO exception in close().");
            ex.printStackTrace();
        }
    }
    /**
     * Создаёт соединение.
     * @return true если соединение установлено. Иначе false.
     */
    private boolean connect() {
        boolean result = false;
        try {
            this.sockCh.connect(new InetSocketAddress(this.address, this.port));
            while (!this.sockCh.finishConnect()) {
                System.err.println("Establishing connection to server.");
                sleep(20);
            }
            result = this.sockCh.isConnected();
        } catch (IllegalMonitorStateException | InterruptedException | IOException ex) {
            System.err.println("Error connecting to server.");
            ex.printStackTrace();
        } finally {
            return result;
        }
    }
    /**
     * Обменивается данными с сервером.
     * @param command комманда серверу.
     * @return код возврата.
     * @throws java.nio.channels.ClosedChannelException исключение Канал закрыт.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    private int exchange(String command) throws ClosedChannelException, IOException {
        Integer code = null;
        Iterator<SelectionKey> keyIter;
        SelectionKey key;
        byte[] outData = (command + '\0').getBytes();
        this.sockCh.register(this.selector, SelectionKey.OP_WRITE);
        //System.err.println("this.selector.select() != 0: " + (this.selector.select() != 0));
        while (this.selector.select() != 0 && !Thread.currentThread().isInterrupted()) {
            keyIter = selector.selectedKeys().iterator();
            try {
                while (keyIter.hasNext()) {
                    key = keyIter.next();
                    keyIter.remove();
                    if (key.isReadable()) {
                        code = this.read();
                    }
                    if (key.isWritable()) {
                        this.write(outData);
                    }
                }
            } catch (CancelledKeyException ex) {
                System.err.println("LiftChecker.exchange() invalid key");
            }
            if (code != null) {
                break;
            }
            //System.err.println("+++++ exchange(command): " + command);
        }
        //System.err.println("+++++ exchange(command): " + command + ". code: " + code);
        return code;
    }
    /**
     * Получает текущий статус лифта.
     * @return текущий статус лифта.
     */
    public int[] getStatus() {
        //System.err.println(String.format("this.status[0]: %d, [1]: %d", this.status[0], this.status[1]));
        return this.status;
    }
    /**
     * Получает синглтон.
     * @param address адрес сервера.
     * @param port номер порта сервера.
     * @throws java.io.IOException исключение ввода-вывода.
     * @return синглтон.
     */
    public static synchronized LiftChecker getInstance(final String address, final int port) throws IOException {
        if (instance == null) {
            instance = new LiftChecker(address, port);
        }
        return instance;
    }
    /**
     * Читает данные из сокета.
     * @return код ответа сервера.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    private int read() throws IOException {
        int result = 0;
        try {
            this.bBuf.clear();
            int count;
            byte[] inData;
            ArrayList<Byte> listIn = new ArrayList<>();
            while ((count = sockCh.read(bBuf)) != -1) {
                if (count > 0) {
                    bBuf.flip();
                    while (bBuf.hasRemaining()) {
                        listIn.add(bBuf.get());
                    }
                    bBuf.clear();
                    bBuf.rewind();
                    if (bBuf.get(count - 1) == '\0') {
                        inData = new byte[listIn.size()];
                        for (int b = 0, size = listIn.size(); b < size; b++) {
                            inData[b] = listIn.get(b);
                        }
                        String str = new String(inData);
                        str = str.subSequence(0, str.indexOf('\0')).toString();
                        result = Integer.parseInt(str);
                        listIn.clear();
                        break;
                    }
                }
            }
        } catch (NumberFormatException ex) {
            System.err.println("Exception in read().");
            ex.printStackTrace();
        } finally {
            return result;
        }
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        if (!this.connect()) {
            return;
        }
        try {
            while (true) {
                this.status[0] = this.exchange("StatusFloor:0");
                //System.err.println("StatusFloor: " + this.status[0]);
                this.status[1] = this.exchange("StatusDoor:0");
                //System.err.println("StatusDoor: " + this.status[1]);
                //System.err.println(String.format("this.status[0]: %d, [1]: %d", this.status[0], this.status[1]));
                sleep(1000);
            }
        } catch (ClosedChannelException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            this.close();
            System.err.println("Lift is broken");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public synchronized void start() {
        if (Thread.State.NEW == this.getState()) {
            super.start();
        }
    }
    /**
     * Пишет данные в сокет.
     * @param outData массив байтов сообщения.
     * @throws java.nio.channels.ClosedChannelException исключение Канал закрыт.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    private void write(byte[] outData) throws ClosedChannelException, IOException {
        int iter = 0;
        while (true) {
            bBuf.put(outData[iter++]);
            if (bBuf.position() == bBuf.limit() || iter == outData.length) {
                bBuf.flip();
                while (bBuf.hasRemaining()) {
                    sockCh.write(bBuf);
                }
                bBuf.clear();
                bBuf.rewind();
                if (iter == outData.length) {
                    break;
                }
            }
        }
        this.sockCh.register(this.selector, SelectionKey.OP_READ);
    }
}