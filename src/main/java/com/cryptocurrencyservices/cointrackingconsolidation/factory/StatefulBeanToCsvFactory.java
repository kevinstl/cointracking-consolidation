package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@Component
public class StatefulBeanToCsvFactory {

    public <T>StatefulBeanToCsv<T> build(Writer writer) {

        StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder<>(writer);
        StatefulBeanToCsv<T> statefulBeanToCsv = builder.build();

        return statefulBeanToCsv;
    }

}
