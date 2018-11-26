package ru.job4j.mathfunc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
/**
 * Класс Calculator вычисляет значения функций.
 * @author Gureyev Ilya (mailto:ill-jah@yandex.ru)
 * @version 2018-09-13
 * @since 2018-09-12
 */
public class Calculator {
    /**
     * Вовращает список значений, вычисленных бифункцией для диапазона.
     * @param diapasonX диапазон значений х (икс).
     * @param props объект свойств функции.
     * @param func математическая функция.
     * @return список с диапазоном вычисленных значений.
     */
    List<Double> diapason(List<Double> diapasonX, FuncProps props, BiFunction<FuncProps, Double, Double> func) {
        List<Double> result = new ArrayList<>();
        diapasonX.forEach(
                x -> result.add(func.apply(props, x))
        );
        return result;
    }
}
