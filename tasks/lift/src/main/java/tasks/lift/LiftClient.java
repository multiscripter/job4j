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
 * LiftClient реализует сущность Пользователь лифта.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-19
 * @since 2018-01-25
 */
class LiftClient extends Thread implements ILift {
    /**
     * Адрес сервера.
     */
    private final String address;
    /**
     * Буфер данных.
     */
    private final ByteBuffer bBuf;
    /**
     * Текущий этаж.
     */
    private int curFloor;
    /**
     * Целевой этаж.
     */
    private int destFloor;
    /**
     * Источник ввода данных.
     */
    private AbstractLiftIO io;
    /**
     * Статус.
     */
    private boolean isCycleEnd;
    /**
     * Проверятель лифта.
     */
    private LiftChecker checker;
    /**
     * Опрашиватель проверятеля лифта.
     */
    private LiftCheckerAsker asker;
    /**
     * Меню лифта.
     */
    private LiftMenu menu;
    /**
     * Номер порта сервера.
     */
    private final int port;
    /**
     * Селектор.
     */
    private final Selector selector;
    /**
     * Канал сокета.
     */
    private final SocketChannel sockCh;
    /**
     * Состояние.
     */
    private int status;
    /**
     * Конструктор.
     * @param address адрес сервера.
     * @param port номер порта сервера.
     * @param io источник ввода и вывода данных.
     * @param curFloor текущий этаж.
     * @throws java.io.IOException исключение ввода-вывода.
     */
    LiftClient(final String address, final int port, final AbstractLiftIO io, final int curFloor) throws IOException {
        this.address = address;
        this.bBuf = ByteBuffer.allocate(8);
        this.curFloor = curFloor;
        this.destFloor = 0;
        this.io = io;
        this.isCycleEnd = false;
        this.status = 0;
        this.menu = new LiftMenu(this, io);
        this.menu.fillActions();
        this.port = port;
        this.selector = Selector.open();
        this.sockCh = SocketChannel.open();
        this.sockCh.configureBlocking(false);
        this.checker = LiftChecker.getInstance(this.address, this.port);
        this.asker = new LiftCheckerAsker(this, this.checker);
    }
    /**
     * Закрывает соединение.
     */
    public void close() {
        try {
            this.io.close();
            this.asker.interrupt();
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
                this.sleep(20);
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
     * Получает текущий этаж.
     * @return текущий этаж.
     */
    public int getCurrentFloor() {
        return this.curFloor;
    }
    /**
     * Получает целевой этаж.
     * @return целевой этаж.
     */
    public int getDestFloor() {
        return this.destFloor;
    }
    /**
     * Получает статус.
     * @return статус.
     */
    public int getStatus() {
        return this.status;
    }
    /**
     * Главнй метод.
     * Запуск из точки сборки: java -cp ./target/classes/ tasks.lift.LiftClient localhost 11111 3
     * @param args массив аргументов запуска.
     */
    public static void main(String[] args) {
        if (args.length == 3) {
            try {
                int floor = Integer.parseInt(args[2]);
                if (floor < 1) {
                    throw new IllegalArgumentException();
                }
                LiftClient client = new LiftClient(args[0], Integer.parseInt(args[1]), new ConsoleIO(), floor);
                client.start();
            } catch (NumberFormatException ex) {
                System.err.println("Second and third arguments must be an integers.");
            } catch (IllegalArgumentException ex) {
                System.err.println("Second and third arguments must be greater than 0");
            } catch (IOException ex) {
                System.err.println("Error in LiftClient constructor.");
                ex.printStackTrace();
            }
        } else {
            System.err.println("Number of arguments must be 4.");
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
        int code = 0;
        Iterator<SelectionKey> keyIter;
        SelectionKey key;
        byte[] outData = (command + '\0').getBytes();
        this.sockCh.register(this.selector, SelectionKey.OP_WRITE);
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
                System.err.println("invalid key");
            }
            if (this.isCycleEnd) {
                break;
            }
        }
        return code;
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
            this.checker.start();
            this.asker.start();
        } catch (Exception ex) {
            System.err.println("Error while starting LiftChecker or LiftCheckerAsker.");
            ex.printStackTrace();
            return;
        }
        try {
            while (true) {
                this.io.write("Please, enter action number:");
                this.menu.show();
                String actionNumStr = "";
                while (this.io.hasData()) {
                    actionNumStr = this.io.read();
                    if (actionNumStr.length() > 0) {
                        break;
                    }
                }
                if (this.validate(actionNumStr)) {
                    int actionNum = Integer.parseInt(actionNumStr, 10);
                    try {
                        String command = this.menu.select(actionNum);
                        if (command.equals("exit")) {
                            break;
                        }
                        if (!command.isEmpty()) {
                            int code = this.exchange(command);
                            this.isCycleEnd = false;
                            this.io.write(this.menu.getMessage(actionNum, code));
                        }
                    } catch (NullPointerException ex) {
                        this.io.write("Please, enter corrent number!");
                    }
                }
            }
            this.close();
        } catch (ClosedChannelException ex) {
            System.err.println("Closed channel exception.");
            ex.printStackTrace();
        } catch (IOException ex) {
           System.err.println("IO exception in run().");
           ex.printStackTrace();
        }
    }
    /**
     * Читает данные из сокета.
     * @return код ответа сервера.
     */
    private int read() {
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
                        //System.out.println("Server response: " + result);
                        listIn.clear();
                        break;
                    }
                }
            }
            this.isCycleEnd = true;
        } catch (IOException | NumberFormatException ex) {
            System.err.println("Exception in read().");
            ex.printStackTrace();
        } finally {
            return result;
        }
    }
    /**
     * Устанавливает целевой этаж.
     * @param floor целевой этаж.
     */
    public void setDestFloor(int floor) {
        this.destFloor = floor;
    }
    /**
     * Устанавливает статус клиента лифта.
     * @param status статус лифта.
     */
    public void setStatus(final int[] status) {
        if (status == null) {
            this.close();
        } else {
            System.err.println(String.format("status    [0]: %d, [1]: %d", status[0], status[1]));
            // Если этаж лифта совпадает с текущим этажом и дверь лифта открыта.
            if (status[0] == this.curFloor && status[1] == 1) {
                this.status = this.status == 0 ? 1 : 0;
                this.menu.fillActions();
                this.menu.show();
            } else if (status[0] == this.destFloor && status[1] == 1) {
                System.err.println(String.format("Current floor is %d", this.destFloor));
                this.curFloor = this.destFloor;
                this.destFloor = 0;
                this.status = this.status == 0 ? 1 : 0;
                this.menu.fillActions();
                this.menu.show();
            } else if (this.status != 0) {
                this.status = 0;
                this.menu.fillActions();
                this.menu.show();
            }
        }
    }
    /**
     * Проверяет корректность введённых данных.
     * @param str проверяемая строка.
     * @return true если данные прошли валидацию. Иначе false.
     */
    public boolean validate(String str) {
        boolean result = false;
        try {
            Integer.parseInt(str, 10);
            result = true;
        } catch (NumberFormatException ex) {
            this.io.write("Please, enter an integer value.");
        } finally {
            return result;
        }
    }
    /**
     * Пишет данные в сокет.
     * @param outData массив байтов сообщения.
     * @throws java.nio.channels.ClosedChannelException исключение Канал закрыт.
     */
    private void write(byte[] outData) throws ClosedChannelException {
        //System.err.println("Request: " + new String(outData));
        int iter = 0;
        try {
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
        } catch (IOException ex) {
            System.err.println("IO exception in write().");
            ex.printStackTrace();
        }
    }
}