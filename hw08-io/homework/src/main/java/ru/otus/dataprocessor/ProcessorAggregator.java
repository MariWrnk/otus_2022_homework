package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {

        // Добавлена настройка локали, потому что локально не читаются дробные числа через точку
        Locale.setDefault(Locale.ENGLISH);

        //группирует выходящий список по name, при этом суммирует поля value
        return new TreeMap<>(data.stream().sorted(Comparator.comparing(Measurement::getName)).collect(Collectors.toMap(Measurement::getName, Measurement::getValue, Double::sum)));
    }
}
