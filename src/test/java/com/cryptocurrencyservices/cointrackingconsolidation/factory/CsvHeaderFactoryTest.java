package com.cryptocurrencyservices.cointrackingconsolidation.factory;

import com.cryptocurrencyservices.cointrackingconsolidation.domain.CointrackingTransaction;
import com.cryptocurrencyservices.cointrackingconsolidation.junit.extension.mockito.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class CsvHeaderFactoryTest {

    @InjectMocks
    private CsvHeaderFactory csvHeaderFactory;

    private class HeaderTestClass{
        private String testField;
    }

    private HeaderTestClass headerTestClass = new HeaderTestClass();

    @Test
    public void build_returnsHeader(){

        String[] headerArray = csvHeaderFactory.build(headerTestClass.getClass());

        assertNotNull(headerArray);

        assertEquals("testField", headerArray[0]);
    }

}
