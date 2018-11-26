package ru.job4j.mathfunc;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
/**
 * Класс CalculatorTest Тестирует работу класса Calculator.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-13
 * @since 2018-09-12
 */
public class CalculatorTest {
    /**
     * Тестирует List<Double> diapason(List<Double> diapasonX, IFuncProps props, BiFunction<IFuncProps, Double, Double> func) на линейной функции.
     */
    @Test
    public void testDiapasonWithLinearFunction() {
        Calculator calc = new Calculator();
        List<Double> diapasonX = Arrays.asList(-3D, -1D, 0D, 1D, 5D);
        FuncProps props = new FuncProps();
        props.setProp("k", 2D);
        props.setProp("b", 1D);
        List<Double> expectedDiapasonY  = Arrays.asList(-5D, -1D, 1D, 3D, 11D);
        List<Double> actualDiapasonY = calc.diapason(diapasonX, props, (p, x) ->
            p.getProp("k") * x + p.getProp("b")
        );
        assertEquals(expectedDiapasonY, actualDiapasonY);
    }
    /**
     * Тестирует List<Double> diapason(List<Double> diapasonX, IFuncProps props, BiFunction<IFuncProps, Double, Double> func) на квадратичной функции.
     */
    @Test
    public void testDiapasonWithQuadraticFunction() {
        Calculator calc = new Calculator();
        List<Double> diapasonX = Arrays.asList(-3D, -1D, 0D, 1D, 5D);
        FuncProps props = new FuncProps();
        props.setProp("a", 2D);
        props.setProp("b", 3D);
        props.setProp("c", 1D);
        List<Double> expectedDiapasonY  = Arrays.asList(10D, 0D, 1D, 6D, 66D);
        List<Double> actualDiapasonY = calc.diapason(diapasonX, props, (p, x) ->
                p.getProp("a") * Math.pow(x, 2) + p.getProp("b") * x + p.getProp("c")
        );
        assertEquals(expectedDiapasonY, actualDiapasonY);
    }
    /**
     * Тестирует List<Double> diapason(List<Double> diapasonX, IFuncProps props, BiFunction<IFuncProps, Double, Double> func) на логарифмическая функции по основанию 3.
     */
    @Test
    public void testDiapasonWithLogarithmicFunction() {
        Calculator calc = new Calculator();
        List<Double> diapasonX = Arrays.asList(1D, 3D, 9D, 27D, 81D);
        FuncProps props = new FuncProps();
        props.setProp("b", 3D);
        List<Double> expectedDiapasonY  = Arrays.asList(0D, 1D, 2D, 3D, 4D);
        List<Double> actualDiapasonY = calc.diapason(diapasonX, props, (p, x) ->
                Math.log(x) / Math.log(p.getProp("b"))
        );
        assertEquals(expectedDiapasonY, actualDiapasonY);
    }
}
