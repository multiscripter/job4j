package ru.job4j.funciface;

import java.util.function.BiFunction;
import java.util.function.Consumer;
/**
 * Класс Calculator демострирует работу функциональных интерфейсов.
 * Вычисляет операцию с числом value и чиселами в диапазоне от start до finish.
 * @see https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-10
 * @since 2018-09-10
 */
public class Calculator {
    /**
     * Вычисляет результат операции над операндами.
     * @param start начало диапазона.
     * @param finish конец диапазона.
     * @param value число.
     * @param op операция.
     * @param media функция, в которую передаётся результат операции.
     */
    public void calculate(int start, int finish, int value, BiFunction<Integer, Integer, Double> op, Consumer<Double> media) {
        for (int index = start; index != finish; index++) {
            media.accept(op.apply(value, index));
        }
    }
    /**
     * Главный метод. Точка входа в приложение.
     * @param args массив параметров запуска.
     */
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        calc.calculate(0, 10, 2, (value, index) -> {
            double result = value * index;
            System.out.println("Pyphagoras table for number 2 from 0 to 10'");
            System.out.printf("Multiply %s * %s = %s %n", value, index, result);
            return result;
        },
        result -> System.out.println(result));
    }
}
