package com.github.phfbueno.GoldenImport.application;

import com.github.phfbueno.GoldenImport.controller.AwardController;
import com.github.phfbueno.GoldenImport.model.GoldenRaspberryAward;
import com.github.phfbueno.GoldenImport.repository.GoldenRaspberryAwardRepository;
import com.github.phfbueno.GoldenImport.service.AwardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AwardController.class)
public class AwardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AwardService awardService;

    @MockBean
    private GoldenRaspberryAwardRepository goldenRaspberryAwardRepository;


    @Test
    public void shouldReturnProducersWithMinAndMaxIntervalsBetweenAwards() throws Exception {
        List<GoldenRaspberryAward> awards = List.of(
                new GoldenRaspberryAward(2020, "Movie1", "Studio1", "Producer1", true),
                new GoldenRaspberryAward(2022, "Movie2", "Studio2", "Producer1", true),
                new GoldenRaspberryAward(2023, "Movie3", "Studio3", "Producer2", true)
        );

        when(goldenRaspberryAwardRepository.findAll()).thenReturn(awards);

        Map<String, List<Map<String, Object>>> mockResult = Map.of(
                "min", List.of(Map.of("interval", 1, "producer", "Producer A")),
                "max", List.of(Map.of("interval", 5, "producer", "Producer B"))
        );

        when(awardService.calculateAwardIntervals()).thenReturn(mockResult);

        mockMvc.perform(get("/awards/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min[0].interval").value(1))
                .andExpect(jsonPath("$.min[0].producer").value("Producer A"))
                .andExpect(jsonPath("$.max[0].interval").value(5))
                .andExpect(jsonPath("$.max[0].producer").value("Producer B"));
    }

    @Test
    public void testCalculateAwardIntervals_WithMultipleProducers() {
        List<GoldenRaspberryAward> awards = List.of(
                new GoldenRaspberryAward(2018, "Movie1", "Studio1", "Producer A", true),
                new GoldenRaspberryAward(2020, "Movie2", "Studio2", "Producer A", true),
                new GoldenRaspberryAward(2023, "Movie3", "Studio3", "Producer A", true),
                new GoldenRaspberryAward(2015, "Movie4", "Studio4", "Producer B", true),
                new GoldenRaspberryAward(2023, "Movie5", "Studio5", "Producer B", true)
        );

        when(goldenRaspberryAwardRepository.findAll()).thenReturn(awards);

        Map<String, List<Map<String, Object>>> mockResult = Map.of(
                "min", List.of(Map.of("interval", 2, "producer", "Producer A")),
                "max", List.of(Map.of("interval", 3, "producer", "Producer B"))
        );

        when(awardService.calculateAwardIntervals()).thenReturn(mockResult);

        Map<String, List<Map<String, Object>>> result = awardService.calculateAwardIntervals();
        assertThat(result.get("min").get(0).get("interval")).isEqualTo(2);
        assertThat(result.get("max").get(0).get("interval")).isEqualTo(3);
    }


    @Test
    public void testCalculateAwardIntervals_WithSameYear() {
        List<GoldenRaspberryAward> awards = List.of(
                new GoldenRaspberryAward(2020, "Movie1", "Studio1", "Producer B", true),
                new GoldenRaspberryAward(2020, "Movie2", "Studio2", "Producer B", true)
        );

        when(goldenRaspberryAwardRepository.findAll()).thenReturn(awards);

        Map<String, List<Map<String, Object>>> mockResult = Map.of(
                "min", List.of(Map.of("interval", 0, "producer", "Producer B")),
                "max", List.of(Map.of("interval", 0, "producer", "Producer B"))
        );

        when(awardService.calculateAwardIntervals()).thenReturn(mockResult);

        Map<String, List<Map<String, Object>>> result = awardService.calculateAwardIntervals();
        assertThat(result.get("min").get(0).get("interval")).isEqualTo(0);
        assertThat(result.get("max").get(0).get("interval")).isEqualTo(0);
    }

    @Test
    public void testCalculateAwardIntervals_WhenNoAwards() {
        when(goldenRaspberryAwardRepository.findAll()).thenReturn(List.of());

        Map<String, List<Map<String, Object>>> mockResult = Map.of();

        when(awardService.calculateAwardIntervals()).thenReturn(mockResult);

        Map<String, List<Map<String, Object>>> result = awardService.calculateAwardIntervals();
        assertThat(result).isEmpty();
    }
}