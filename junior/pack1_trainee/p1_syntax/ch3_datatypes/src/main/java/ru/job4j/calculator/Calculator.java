package ru.job4j.calculator;

/**
 * Class Calculator выполняет простейщие математические операции:
 * сложение, вычитание, умножение и деление.
 *
 * @author Goureev Ilya (mailto:ill-jah@yandex.ru)
 * @version 1
 * @since 0.1
 */
public class Calculator {
    /**
     * Хранит результат вычислительной операции.
     */
    private double result;
    /**
     * Производит операцию сложения над двумя числами.
     * @param first первое слагаемое.
     * @param second второе слагаемое.
     */
    public void add(double first, double second) {
        this.result = first + second;
    }
    /**
     * Производит операцию вычитания над двумя числами.
     * @param first уменьшаемое.
     * @param second вычитаемое.
     */
    public void substruct(double first, double second) {
        this.result = first - second;
    }
    /**
     * Производит операцию умножения над двумя числами.
     * @param first первый множитель.
     * @param second второй множитель.
     */
    public void multiple(double first, double second) {
        this.result = first * second;
    }
    /**
     * Производит операцию деления над двумя числами.
     * @param first делимое.
     * @param second делитель.
     */
    public void div(double first, double second) {
        this.result = first / second;
    }
    /**
     * Возвращает результат вычислительной операции.
     * @return результат.
     */
    public double getResult() {
        return this.result;
    }
}
