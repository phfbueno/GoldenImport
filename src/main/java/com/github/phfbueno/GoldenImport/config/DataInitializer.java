package com.github.phfbueno.GoldenImport.config;

import com.github.phfbueno.GoldenImport.service.CsvDataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CsvDataLoaderService csvDataLoaderService;

    @Override
    public void run(String... args) throws Exception {

        csvDataLoaderService.loadCsvData();

    }
}
