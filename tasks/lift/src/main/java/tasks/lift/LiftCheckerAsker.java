package tasks.lift;

import java.util.Arrays;
/**
 * LiftCheckerAsker реализует сущность Опрашиватель проверятеля лифта.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-02-27
 * @since 2018-02-05
 */
class LiftCheckerAsker extends Thread {
    /**
     * Проверятель лифта.
     */
    private final LiftChecker checker;
    /**
     * Клиент лифта.
     */
    private final LiftClient client;
    /**
     * Последний статус лифта.
     */
    private int[] lastStatus;
    /**
     * Конструктор.
     * @param client клиент лифта.
     * @param checker проверятель лифта.
     */
    LiftCheckerAsker(final LiftClient client, final LiftChecker checker) {
        this.checker = checker;
        this.client = client;
        this.lastStatus = new int[]{0, 0};
    }
    /**
     * Переопределёный метод.
     */
    @Override
    public void run() {
        synchronized (this) {
            try {
                while (true) {
                    if (this.checker.getState() == Thread.State.TERMINATED) {
                        this.client.setStatus(null);
                        break;
                    }
                    int[] status = this.checker.getStatus();
                    if (!Arrays.equals(status, this.lastStatus)) {
                        this.lastStatus = Arrays.copyOf(status, status.length);
                        this.client.setStatus(status);
                    }
                    sleep(200);
                }
            } catch (InterruptedException ex) {
                this.client.setStatus(null);
            }
        }
    }
}