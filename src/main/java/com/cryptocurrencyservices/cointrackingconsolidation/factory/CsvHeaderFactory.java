package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvHeaderFactory {

    public String[] build(Class aClass) {
        Field[] fields = aClass.getDeclaredFields();
        List<String> header = new ArrayList<>();

        Arrays.stream(fields).forEach(field -> header.add(field.getName()));

        String[] headerArray = header.toArray(new String[header.size()]);
        return headerArray;
    }
}
