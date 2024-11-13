package com.github.phfbueno.GoldenImport.service;

import com.github.phfbueno.GoldenImport.model.GoldenRaspberryAward;
import com.github.phfbueno.GoldenImport.repository.GoldenRaspberryAwardRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CsvDataLoaderService {

    @Autowired
    private GoldenRaspberryAwardRepository goldenRaspberryAwardRepository;

    @Value("${csv.filepath}")
    private String filePath;

    @Value("${csv.filename}")
    private String fileName;


    public void loadCsvData(MultipartFile file) throws IOException {
        try(InputStreamReader reader = new InputStreamReader(file.getInputStream());

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withHeader())){

            processGoldenRaspberryAwardsFromCSV(csvParser);
        }
    }

    public void loadCsvData() throws IOException {

        String fullPath = Paths.get(filePath, fileName).toString();

        File file = new File(fullPath);

        if (!file.exists()) {
            System.out.println("Arquivo " + fullPath + " não encontrado. O sistema continuará sem importar os dados.");
            return;
        }

        try (FileReader reader = new FileReader(fullPath);

             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withHeader())){

             processGoldenRaspberryAwardsFromCSV(csvParser);
        }
    }


    private void processGoldenRaspberryAwardsFromCSV(CSVParser csvParser) throws IOException {
        List<CSVRecord> records = csvParser.getRecords();
        for (CSVRecord record : records) {
            GoldenRaspberryAward goldenRaspberryAward = new GoldenRaspberryAward();

            goldenRaspberryAward.setYear_g(Integer.parseInt(record.get("year")));
            goldenRaspberryAward.setTitle(record.get("title"));
            goldenRaspberryAward.setStudios(record.get("studios"));
            goldenRaspberryAward.setProducers(record.get("producers"));

            String winnerColumnName = record.isMapped("winner") ? "winner" : "winner,,,,,,";
            String winnerValue = record.get(winnerColumnName).trim();

            boolean winner = false;
            String cleanedValue = winnerValue != null ? winnerValue.replaceAll(",", "") : "";

            if ("yes".equalsIgnoreCase(cleanedValue)) {
                winner = true;
            }
            goldenRaspberryAward.setWinner(winner);
            goldenRaspberryAwardRepository.save(goldenRaspberryAward);
        }
    }


}
