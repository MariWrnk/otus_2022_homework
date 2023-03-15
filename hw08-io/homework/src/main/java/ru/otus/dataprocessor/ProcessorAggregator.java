package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {

        // Добавила настройку локали, потому что локально не читаются дробные числа через точку
        Locale.setDefault(Locale.ENGLISH);

        //группирует выходящий список по name, при этом суммирует поля value
        TreeMap<String, Double> resultMap = new TreeMap<>();

        data.forEach(m -> {
            resultMap.computeIfPresent(m.getName(), (k, v) -> v + m.getValue());
            resultMap.computeIfAbsent(m.getName(), k -> m.getValue());
        });
        return resultMap;
    }
}
