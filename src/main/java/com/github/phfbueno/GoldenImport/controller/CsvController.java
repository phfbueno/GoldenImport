package com.github.phfbueno.GoldenImport.controller;

import com.github.phfbueno.GoldenImport.service.CsvDataLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/csv")
public class CsvController {

    @Autowired
    private CsvDataLoaderService csvDataLoaderService;

    @PostMapping("upload")
    public ResponseEntity<String> uploadCsv(@RequestParam("file")MultipartFile file) {
        try {
            csvDataLoaderService.loadCsvData(file);
            return ResponseEntity.ok("Arquivo CSV carregado com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erro ao processar o arquivo CSV.");
        }
    }
}
