package tasks.lift;

import java.util.HashMap;
/**
 * Class LiftLauncher реализует функционал запуска лифта из консоли.
 * Запуск из точки сборки: java -cp ./target/classes/ tasks.lift.LiftLauncher 5 3 1 3
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-01-22
 * @since 2018-01-18
 */
public class LiftLauncher {
    /**
     * Главный метод.
     * @param args массив параметров запуска.
     */
    public static void main(String[] args) {
        if (args.length == 4) {
            try {
                HashMap<String, Integer> liftParams = new HashMap<>();
                liftParams.put("floors", Integer.parseInt(args[0]));
                liftParams.put("height", Integer.parseInt(args[1]));
                liftParams.put("speed", Integer.parseInt(args[2]));
                liftParams.put("dalay", Integer.parseInt(args[3]));
                liftParams.put("port", 11111);
                AdminLiftUI admin = new AdminLiftUI(liftParams, new ConsoleIO());
                admin.init();
            } catch (NumberFormatException ex) {
                System.err.println("All arguments must be an integer.");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } else {
            System.err.println("Number of arguments must be 4.");
        }
    }
}