package com.github.phfbueno.GoldenImport.application;

import com.github.phfbueno.GoldenImport.repository.GoldenRaspberryAwardRepository;
import com.github.phfbueno.GoldenImport.service.AwardService;
import com.github.phfbueno.GoldenImport.service.CsvDataLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileBasedAwardControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AwardService awardService;

    @Autowired
    private CsvDataLoaderService csvDataLoaderService;

    @Autowired
    private GoldenRaspberryAwardRepository goldenRaspberryAwardRepository;

    @BeforeEach
    public void setUp() throws IOException {
        goldenRaspberryAwardRepository.deleteAll();

        csvDataLoaderService.loadCsvData();
    }

    @Test
    public void testCalculateAwardIntervals() {
        ResponseEntity<Map> response = restTemplate.getForEntity("/awards/intervals", Map.class);

        assertEquals(200, response.getStatusCodeValue());

        Map<String, List<Map<String, Object>>> result = response.getBody();
        List<Map<String, Object>> minIntervals = result.get("min");
        List<Map<String, Object>> maxIntervals = result.get("max");

        assertEquals(1, minIntervals.size());
        assertEquals(1991, minIntervals.get(0).get("followingWin"));
        assertEquals(1990, minIntervals.get(0).get("previousWin"));
        assertEquals("Joel Silver", minIntervals.get(0).get("producer"));
        assertEquals(1, minIntervals.get(0).get("interval"));

        assertEquals(1, maxIntervals.size());
        assertEquals(2015, maxIntervals.get(0).get("followingWin"));
        assertEquals(2002, maxIntervals.get(0).get("previousWin"));
        assertEquals("Matthew Vaughn", maxIntervals.get(0).get("producer"));
        assertEquals(13, maxIntervals.get(0).get("interval"));
    }
}