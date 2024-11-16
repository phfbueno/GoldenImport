package com.github.phfbueno.GoldenImport.service;

import com.github.phfbueno.GoldenImport.config.DataInitializer;
import com.github.phfbueno.GoldenImport.model.GoldenRaspberryAward;
import com.github.phfbueno.GoldenImport.repository.GoldenRaspberryAwardRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
public class CsvDataLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

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

        String resourcePath = filePath + fileName;

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                logger.warn("Arquivo não encontrado no classpath: {}", resourcePath);
                return;
            }

            try (InputStreamReader reader = new InputStreamReader(inputStream);
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withHeader())) {

                processGoldenRaspberryAwardsFromCSV(csvParser);

            } catch (IOException e) {
                logger.error("Erro ao processar o arquivo CSV: {}", e.getMessage());
                throw e;
            }
        }
    }


    private void processGoldenRaspberryAwardsFromCSV(CSVParser csvParser) throws IOException {
        List<CSVRecord> records = csvParser.getRecords();
        for (CSVRecord record : records) {
            GoldenRaspberryAward goldenRaspberryAward = new GoldenRaspberryAward();

            goldenRaspberryAward.setYearMovie(Integer.parseInt(record.get("year")));
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
