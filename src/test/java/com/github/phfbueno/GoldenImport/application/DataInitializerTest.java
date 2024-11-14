package com.github.phfbueno.GoldenImport.application;

import com.github.phfbueno.GoldenImport.config.DataInitializer;
import com.github.phfbueno.GoldenImport.service.CsvDataLoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;


@SpringBootTest
public class DataInitializerTest {

    @Autowired
    private DataInitializer dataInitializer;

    @MockBean
    private CsvDataLoaderService csvDataLoaderService;

    @MockBean
    private File file;

    @Test
    public void testRun_InitializesDataCorrectly() throws Exception {
        doNothing().when(csvDataLoaderService).loadCsvData();

        reset(csvDataLoaderService);

        dataInitializer.run();

        verify(csvDataLoaderService, times(1)).loadCsvData();
    }

}