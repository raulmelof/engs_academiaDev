package br.com.academiaDev.infrastructure.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenericCsvExporter {

    public static <T> void exportToCsv(List<T> data) {
        if (data == null || data.isEmpty()) {
            System.out.println("Lista vazia. Nada para exportar.");
            return;
        }

        Class<?> clazz = data.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        String header = Stream.of(fields)
                .map(Field::getName)
                .collect(Collectors.joining(","));
        System.out.println("--- CSV EXPORT (" + clazz.getSimpleName() + ") ---");
        System.out.println(header);

        for (T item : data) {
            String row = Stream.of(fields)
                    .map(field -> {
                        field.setAccessible(true);
                        try {
                            Object value = field.get(item);
                            return value != null ? value.toString() : "";
                        } catch (IllegalAccessException e) {
                            return "ERROR";
                        }
                    })
                    .collect(Collectors.joining(","));
            System.out.println(row);
        }
        System.out.println("----------------------------------------");
    }
}