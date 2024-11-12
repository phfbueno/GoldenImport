package com.github.phfbueno.GoldenImport.service;

import com.github.phfbueno.GoldenImport.model.GoldenRaspberryAward;
import com.github.phfbueno.GoldenImport.repository.GoldenRaspberryAwardRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CsvDataLoaderService {

    @Autowired
    private GoldenRaspberryAwardRepository goldenRaspberryAwardRepository;



    public void loadCsvData(MultipartFile file) throws IOException {
        try(InputStreamReader reader = new InputStreamReader(file.getInputStream());

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withHeader())){

            List<CSVRecord> records = csvParser.getRecords();
            System.out.println("Cabeçalhos do CSV: " + csvParser.getHeaderMap().keySet());
            for (CSVRecord record : records) {
                GoldenRaspberryAward goldenRaspberryAward = new GoldenRaspberryAward();

                goldenRaspberryAward.setYear_g(Integer.parseInt(record.get("year")));
                goldenRaspberryAward.setTitle(record.get("title"));
                goldenRaspberryAward.setStudios(record.get("studios"));
                goldenRaspberryAward.setProducers(record.get("producers"));

                String winnerColumnName = record.isMapped("winner") ? "winner" : "winner,,,,,,";
                String winnerValue = record.get(winnerColumnName).trim();

                boolean winner = false;
                c
                winnerValue.replaceAll(",", "");

                if (winnerValue != null || "yes".equalsIgnoreCase(winnerValue)){
                    winner = Boolean.parseBoolean(winnerValue);
                }

                goldenRaspberryAward.setWinner(winner);
                goldenRaspberryAwardRepository.save(goldenRaspberryAward);
            }
        }
    }
}
