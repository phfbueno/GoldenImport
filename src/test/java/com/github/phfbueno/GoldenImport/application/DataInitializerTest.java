package com.github.phfbueno.GoldenImport.application;

import com.github.phfbueno.GoldenImport.config.DataInitializer;
import com.github.phfbueno.GoldenImport.service.CsvDataLoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;

import static org.mockito.Mockito.*;


@SpringBootTest
public class DataInitializerTest {

    @Autowired
    private DataInitializer dataInitializer;

    @MockBean
    private CsvDataLoaderService csvDataLoaderService;


    @Test
    public void testRun_InitializesDataCorrectly() throws Exception {
        doNothing().when(csvDataLoaderService).loadCsvData();

        reset(csvDataLoaderService);

        dataInitializer.run();

        verify(csvDataLoaderService, times(1)).loadCsvData();
    }

}