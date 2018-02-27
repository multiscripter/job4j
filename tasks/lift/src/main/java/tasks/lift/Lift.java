package tasks.lift;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Lift реализует сущность Лифт.
 *
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-27
 * @since 2018-01-18
 */
class Lift extends Thread implements ILift {
    /**
     * Ёмкость буфера.
     */
    private final int capacity;
    /**
     * Канал сокета сервера.
     */
    private ServerSocketChannel servSockCh = null;
    /**
     * Очередь комманд.
     */
    private CommandQueue cmdQueue;
    /**
     * Текущий этаж.
     */
    private int curFloor;
    /**
     * Хранилище данных.
     */
    private final Map<SocketChannel, Integer> dataStorage;
    /**
     * Состояние дверей лифта.
     */
    private boolean doorsOpened;
    /**
     * Время.
     */
    private int doorTimer;
    /**
     * Параметры лифта.
     */
    private final HashMap<String, Integer> liftParams;
    /**
     * Очередь вызовов.
     */
    private final BoundedQueue floorsQueue;
    /**
     * Используется ли лифт в данный момент каким-либо пользователем.
     */
    private boolean inUse;
    /**
     * Селектор.
     */
    private Selector selector = null;
    /**
     * Таймер.
     */
    private Timer timer = null;
    /**
     * Состояние лифта (в работе или нет).
     */
    private boolean working;
    /**
     * Конструктор.
     * @param liftParams параметры лифта.
     * @throws IllegalArgumentException неверный аргумент.
     */
    Lift(final HashMap<String, Integer> liftParams) throws IllegalArgumentException {
        for (Integer val : liftParams.values()) {
            if (val < 1 || val > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("Incorrect parameter");
            }
        }
        this.capacity = 8;
        this.cmdQueue = new CommandQueue();
        this.curFloor = 1;
        this.dataStorage = new HashMap<>();
        this.doorsOpened = false;
        this.doorTimer = 0;
        this.liftParams = liftParams;
        this.floorsQueue = new BoundedQueue(liftParams.get("floors"));
        try {
            this.selector = Selector.open();
            this.servSockCh = ServerSocketChannel.open();
            this.servSockCh.configureBlocking(false);
            this.servSockCh.socket().bind(new InetSocketAddress("localhost", this.liftParams.get("port")));
            this.servSockCh.register(this.selector, SelectionKey.OP_ACCEPT);
            this.inUse = false;
            this.working = false;
            System.out.println("Lift initialized.");
        } catch (BindException ex) {
            System.err.println("Address already in use.");
        } catch (IOException ex) {
            System.err.println("IO exception in constructor.");
            ex.printStackTrace();
        }
    }
    /**
     * Обрабатывает операцию SelectionKey.OP_ACCEPT.
     * @param key ключ операции канала сокета клиента.
     */
    private void accept(SelectionKey key) {
        try {
            SocketChannel clientCh = ((ServerSocketChannel) key.channel()).accept();
            clientCh.configureBlocking(false);
            clientCh.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException ex) {
            System.err.println("IO exception in accept().");
            ex.printStackTrace();
        }
    }
    /**
     * Выполняет действие: Вызов лифта. Добавляет этаж в очередь лифта если этажа нет в очереди.
     * @param floor номер этажа.
     * @return 1 если вызвавший этаж добавлен в очередь лифта, 2 - если неверный этаж. Иначе 0.
     */
    public int actionCall(Integer floor) {
        //System.err.println("actionCall(). floorsQueue: " + this.floorsQueue.toString() + ", this.cmdQueue: " + this.cmdQueue.toString());
        int result;
        if (floor < 1 || floor > this.liftParams.get("floors")) {
            this.removeCmd("Call:" + floor);
            result = 2;
        } else if (floor == this.curFloor) {
            result = 3;
            this.removeCmd("Call:" + floor);
            this.cmdQueue.addFirst("Door:1");
            this.execute();
        } else {
            if (!this.floorsQueue.contains(floor)) {
                if (this.inUse) {
                    result = this.floorsQueue.addFirst(floor) ? 1 : 0;
                } else {
                    result = this.floorsQueue.add(floor) ? 1 : 0;
                }
            } else {
                result = 1;
            }
            if (result == 1) {
                this.removeCmd("Call:" + floor);
                this.execute();
            }
        }
        return result;
    }
    /**
     * Открывает и закрывает двери лифта.
     * @param code код операции двери: 0 - закрыть, 1 - открыть.
     * @return 0 если двери закрыты, 1 если двери открыты.
     */
    public int actionDoor(final Integer code) {
        if (!this.working) {
            this.working = true;
            if (this.doorsOpened && code == 0) {
                System.out.println("Lift doors are closing.");
            } else if (!this.doorsOpened && code == 1) {
                System.out.println("Lift doors are opening.");
            }
            Thread doors = new Thread() {
                /**
                 * Переопределёный метод.
                 */
                @Override
                public void run() {
                    synchronized (this) {
                        //System.err.println("actionDoor.run(). floorsQueue: " + Lift.this.floorsQueue.toString() + ", this.cmdQueue: " + Lift.this.cmdQueue.toString());
                        try {
                            while (true) {
                                if (Lift.this.doorTimer == Lift.this.liftParams.get("doorTimer")) {
                                    Lift.this.doorTimer = 0;
                                    String message = code == 0 ? "closed" : "opened";
                                    Lift.this.doorsOpened = code == 1;
                                    System.out.println(String.format("Lift doors %s.", message));
                                    Lift.this.removeCmd("Door:" + code);
                                    if (code == 1) {
                                        Lift.this.timer = new Timer();
                                        Lift.this.timer.schedule(new TimerTask() {
                                            /**
                                             * Переопределёный метод.
                                             */
                                            @Override
                                            public void run() {
                                                //System.err.println("TimerTask.run(). floorsQueue: " + Lift.this.floorsQueue.toString() + ", this.cmdQueue: " + Lift.this.cmdQueue.toString());
                                                Lift.this.cmdQueue.addFirst("Door:0");
                                                Lift.this.inUse = false;
                                                Lift.this.execute();
                                            }
                                        }, Lift.this.liftParams.get("delay") * 1000);
                                    }
                                    break;
                                }
                                Lift.this.doorTimer++;
                                sleep(1000);
                            }
                            Lift.this.working = false;
                            Lift.this.execute();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            };
            doors.start();
        }
        return code;
    }
    /**
     * Перемещает лифт на указанный этаж.
     * @param floor номер этажа.
     * @return 1 (лифт начал движение на указанный этаж).
     */
    public int actionMove(final Integer floor) {
        int result = 0;
        if (!this.working) {
            if (this.curFloor != floor) {
                Thread move = new Thread() {
                    /**
                     * Переопределёный метод.
                     */
                    @Override
                    public void run() {
                        synchronized (this) {
                            try {
                                Lift.this.setWorking(true);
                                int curFloor = Lift.this.getCurrentFloor();
                                if (Lift.this.isDoorsOpened()) {
                                    Lift.this.addFirstCmd("Door:0");
                                } else if (curFloor != floor) {
                                    boolean direction = curFloor < floor;
                                    int height = 0;
                                    int floorHeight = Lift.this.getFloorHeight();
                                    while (true) {
                                        height++;
                                        if (height == floorHeight) {
                                            height = 0;
                                            if (direction) {
                                                curFloor++;
                                            } else {
                                                curFloor--;
                                            }
                                            Lift.this.setCurrentFloor(curFloor);
                                        }
                                        System.out.println("Lift at floor: " + curFloor);
                                        if (curFloor == floor) {
                                            break;
                                        }
                                        this.wait(1000);
                                    }
                                    Lift.this.removeFloorsQueueHead();
                                    Lift.this.removeCmd("Move:" + floor);
                                    Lift.this.removeCmd("Call:" + floor);
                                }
                                Lift.this.setWorking(false);
                                Lift.this.execute();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                };
                move.start();
                result = 1;
            }
        }
        return result;
    }
    /**
     * Возвращает состояние дверей.
     * @param dummy заглушка.
     * @return состояние дверей.
     */
    public int actionStatusDoor(final Integer dummy) {
        this.removeCmd("StatusDoor:" + dummy);
        this.execute();
        return this.doorsOpened ? 1 : 0;
    }
    /**
     * Возвращает текущий этаж.
     * @param dummy заглушка.
     * @return текущий этаж.
     */
    public int actionStatusFloor(final Integer dummy) {
        this.removeCmd("StatusFloor:" + dummy);
        this.execute();
        return this.curFloor;
    }
    /**
     * Добавляет команду в начало очереди комманд.
     * @param cmd добавляемая команда.
     */
    public void addFirstCmd(String cmd) {
        if (cmd != null) {
            this.cmdQueue.addFirst(cmd);
        }
    }
    /**
     * Вызывает метод, имя которого передано в параметре.
     * @param command команда, содержащая имя метода и параметр вызова.
     * @return результат вызова.
     * @throws IllegalAccessException несанкционированный доступ.
     * @throws java.lang.reflect.InvocationTargetException цель вызова.
     * @throws NoSuchMethodException нет такого метода.
     */
    private int callMethod(String command) throws IllegalAccessException, InvocationTargetException,  NoSuchMethodException {
        String[] strs = command.split(":");
        int num = Integer.parseInt(strs[1].trim());
        java.lang.reflect.Method method = Lift.class.getMethod("action" + strs[0], Integer.class);
        return (int) method.invoke(Lift.this, num);
    }
    /**
     * Выполняет команды из очереди.
     * @return результат вызова.
     */
    public int execute() {
        int result = 0;
        try {
            String command = null;
            if (!this.cmdQueue.isEmpty()) {
                command = this.cmdQueue.peek();
                if (!command.startsWith("Status") && this.working) {
                    command = null;
                }
            } else if (!this.working && !this.inUse) {
                if (!this.floorsQueue.isEmpty()) {
                    this.inUse = true;
                    if (this.doorsOpened) {
                       this.cmdQueue.add("Door:0");
                    }
                    this.removeCmd("Call:" + this.floorsQueue.peek());
                    command = "Move:" + this.floorsQueue.peek();
                    this.cmdQueue.add(command);
                    this.cmdQueue.add("Door:1");
                    //System.err.println("execute(). floorsQueue: " + this.floorsQueue.toString() + ", this.cmdQueue: " + this.cmdQueue.toString());
                }
            }
            if (command != null) {
                result = this.callMethod(command);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    /**
     * Получает текущий этаж.
     * @return текущий этаж.
     */
    @Override
    public int getCurrentFloor() {
        return this.curFloor;
    }
    /**
     * Получает высоту этажа.
     * @return высота этажа.
     */
    public int getFloorHeight() {
        return this.liftParams.get("height");
    }
    /**
     * Проверяет открыты ли двери лифта.
     * @return true если двери лифта открыты. Иначе false.
     */
    public boolean isDoorsOpened() {
        return this.doorsOpened;
    }
    /**
     * Главный метод.
     * Запуск из точки сборки: java -cp ./target/classes/ tasks.lift.Lift 5 3 1 3
     * @param args массив параметров запуска.
     */
    public static void main(String[] args) {
        if (args.length == 4) {
            try {
                HashMap<String, Integer> liftParams = new HashMap<>();
                liftParams.put("floors", Integer.parseInt(args[0]));
                liftParams.put("height", Integer.parseInt(args[1]));
                liftParams.put("speed", Integer.parseInt(args[2]));
                liftParams.put("delay", Integer.parseInt(args[3]));
                liftParams.put("port", 11111);
                //Время в секундах, за которое открываются или закрываются двери лифта.
                liftParams.put("doorTimer", 3);
                Lift lift = new Lift(liftParams);
                lift.start();
            } catch (NumberFormatException ex) {
                System.err.println("All arguments must be an integer.");
            }
        } else {
            System.err.println("Number of arguments must be 4.");
        }
    }
    /**
     * Обрабатывает данные.
     * @param key ключ операции канала сокета клиента.
     * @param command строка с командой.
     */
    private void process(SelectionKey key, String command) {
        int result = -1;
        try {
            if (command.startsWith("Status")) {
                this.cmdQueue.offerFirst(command);
            } else if (!this.cmdQueue.contains(command)) {
                this.cmdQueue.add(command);
            }
            result = this.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            this.dataStorage.put((SocketChannel) key.channel(), result);
            key.interestOps(SelectionKey.OP_WRITE);
        }
    }
    /**
     * Читает данные из канала сокета клиента.
     * @param key - ключ операции канала сокета клиента.
     * @throws java.nio.channels.CancelledKeyException исключение Отмененный ключ.
     */
    private void read(SelectionKey key) throws CancelledKeyException {
        SocketChannel clientCh = (SocketChannel) key.channel();
        ByteBuffer bBuf = ByteBuffer.allocate(capacity);
        int count;
        byte[] data;
        ArrayList<Byte> list = new ArrayList<>();
        try {
            while ((count = clientCh.read(bBuf)) != -1) {
                if (count > 0) {
                    bBuf.flip();
                    while (bBuf.hasRemaining()) {
                        list.add(bBuf.get());
                    }
                    if (bBuf.get(count - 1) == '\0') {
                        data = new byte[list.size()];
                        for (int b = 0, size = list.size(); b < size; b++) {
                            data[b] = list.get(b);
                        }
                        list.clear();
                        this.process(key, new String(Arrays.copyOf(data, data.length - 1)));
                        return;
                    }
                    bBuf.clear();
                    bBuf.rewind();
                }
            }
            clientCh.close();
            key.cancel();
        } catch (IOException ex) {
            try {
                clientCh.close();
                key.cancel();
                //ex.printStackTrace();
            } catch (IOException ex1) {
                System.err.println("IO exception in IOException ex1.");
                ex1.printStackTrace();
            }
        }
    }
    /**
     * Удаляет головной элемент из очереди комманд.
     * @param cmd удаляемая команда.
     */
    public synchronized void removeCmd(String cmd) {
        if (this.cmdQueue.size() > 0) {
            this.cmdQueue.remove(cmd);
        }
    }
    /**
     * Удаляет головной элемент из очереди этажей.
     */
    public void removeFloorsQueueHead() {
        if (this.floorsQueue.size() > 0) {
            this.floorsQueue.remove();
        }
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public  void run() {
        try {
            Iterator<SelectionKey> keyIter;
            SelectionKey key;
            while (!Thread.currentThread().isInterrupted()) {
                this.selector.select();
                Set<SelectionKey> sKeys = this.selector.selectedKeys();
                if (sKeys.isEmpty()) {
                    continue;
                }
                keyIter = this.selector.selectedKeys().iterator();
                try {
                    while (keyIter.hasNext()) {
                        key = keyIter.next();
                        if (key.isAcceptable()) {
                            this.accept(key);
                        }
                        if (key.isReadable()) {
                            this.read(key);
                        }
                        if (key.isWritable()) {
                            this.write(key);
                        }
                        keyIter.remove();
                    }
                } catch (CancelledKeyException ex) {
                    System.out.println("Invalid key. Client disconnected.");
                }
            }
            System.out.println("after !Thread.currentThread().isInterrupted()");
        } catch (Exception ex) {
           ex.printStackTrace();
        } finally {
            System.out.println("server stopped.");
            this.interrupt();
        }
    }
    /**
     * Устанавливает текущий этаж.
     * @param floor текущий этаж.
     */
    public void setCurrentFloor(final int floor) {
        this.curFloor = floor;
    }
    /**
     * Устанавливает свойство working.
     * @param working свойство.
     */
    public void setWorking(final boolean working) {
        this.working = working;
    }
    /**
     * Пишет данные в канала сокета клиента.
     * @param key - ключ операции канала сокета клиента.
     * @throws java.nio.channels.CancelledKeyException исключение Отмененный ключ.
     */
    private void write(SelectionKey key) throws CancelledKeyException {
        SocketChannel clientCh = (SocketChannel) key.channel();
        String data = this.dataStorage.get(clientCh).toString();
        this.dataStorage.remove(clientCh);
        key.interestOps(SelectionKey.OP_READ);
        ByteBuffer bBuf = ByteBuffer.wrap((data + '\0').getBytes());
        try {
            while (bBuf.hasRemaining()) {
                clientCh.write(bBuf);
            }
        } catch (IOException ex) {
            System.err.println("IO exception in write().");
            ex.printStackTrace();
        }
    }
}