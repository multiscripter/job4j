package tasks.lift;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * Класс Lift реализует сущность Лифт.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-18
 * @since 2018-01-18
 */
@ThreadSafe
class Lift extends Thread {
    /**
     * Ёмкость буфера.
     */
    private final int capacity;
    /**
     * Текущая команда.
     */
    private String command;
    /**
     * Текущий этаж.
     */
    private int curFloor;
    /**
     * Параметры лифта.
     */
    private final HashMap<String, Integer> liftParams;
    /**
     * Объект блокировки (монитор).
     */
    private final ReentrantLock lock;
    /**
     * Очередь вызовов.
     */
    private final BoundedQueue queue;
    /**
     * Состояние лифта (включён или нет).
     */
    private boolean working;
    /**
     * Конструктор.
     * @param liftParams параметры лифта.
     * @throws IllegalArgumentException неверный аргумент.
     */
    Lift(final HashMap<String, Integer> liftParams) throws IllegalArgumentException {
        if (dalay < 1 || dalay > Integer.MAX_VALUE || height < 1 || height > Integer.MAX_VALUE || floors < 2 || floors > Integer.MAX_VALUE || speed < 1 || speed > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Incorrect parameter");
        }
        this.capacity = 8;
        this.curFloor = 1;
        this.liftParams = liftParams;
        this.lock = new ReentrantLock();
        this.queue = new BoundedQueue(floors);
        this.working = false;
    }
    /**
     * Добавляет этаж в очередь лифта.
     * @param floor номер этажа.
     * @return true если этаж добавлен в очередь лифта. Иначе false.
     */
    @GuardedBy("lock")
    public boolean add(int floor) {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                result = this.working && this.queue.add(floor);
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Обрабатывает входящие соединения.
     * @param incomSockCh канал сокета входящего соединения.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    private void handler(SocketChannel incomSockCh) throws IOException {
        System.out.println("Lift.handler()");
        ByteBuffer bBuf = ByteBuffer.allocate(this.capacity);
        int count;
        byte[] inData, outData;
        ArrayList<Byte> list = new ArrayList<>();
        int a;
        while ((count = incomSockCh.read(bBuf)) != -1) {
            if (count > 0) {
                bBuf.flip();
                while (bBuf.hasRemaining()) {
                    list.add(bBuf.get());
                }
                if (bBuf.get(count - 1) == '\0') {
                    inData = new byte[list.size()];
                    for (int b = 0, size = list.size(); b < size; b++) {
                        inData[b] = list.get(b);
                    }
                }
            }
        }
        this.command = new String(inData);
    }
    /**
     * Проверяет включён ли лифт.
     * @return true если лифт включён. Иначе false.
     */
    @GuardedBy("lock")
    public boolean isOn() {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                result = this.working;
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
    /**
     * Включает лифт.
     * @return true если лифт включился. Иначе false.
     */
    @GuardedBy("lock")
    public boolean on() {
        boolean result = false;
        try {
            if (this.lock.tryLock()) {
                if (Thread.State.NEW == this.getState()) {
                    this.start();
                    result = true;
                }
                this.lock.unlock();
            }
        } catch (IllegalThreadStateException ex) {
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
        try (ServerSocketChannel servSockChannel = ServerSocketChannel.open()) {
            servSockChannel.socket().bind(new InetSocketAddress("localhost", this.liftParams.get("port")));
            servSockChannel.configureBlocking(false);
            try {
                while (true) {
                    SocketChannel incomSockCh = servSockCh.accept();
                    if (incomSockCh == null) {
                        System.out.println("Waiting.");
                        this.wait(100);
                    } else {
                        this.handler(incomSockCh);
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                servSockChannel.socket().close();
                System.out.println("ServerSocketChannel closed.");
            }
            this.working = true;
            System.out.println("Turned on.");
            System.out.println("Standing by.");
            while (this.working) {
                Thread.currentThread().sleep(100);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            if (this.working) {
                this.working = false;
                System.out.println("turned off");
            }
        }
    }
    /**
     * Выключает лифт.
     * @return true если лифт выключился. Иначе false.
     */
    @GuardedBy("lock")
    public boolean off() {
        boolean result = false;
        if (this.lock.tryLock()) {
            try {
                if (this.working) {
                    Thread.currentThread().interrupt();
                    this.working = false;
                    result = true;
                }
            } finally {
                this.lock.unlock();
            }
        }
        return result;
    }
}