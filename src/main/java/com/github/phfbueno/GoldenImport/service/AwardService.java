package com.github.phfbueno.GoldenImport.service;

import com.github.phfbueno.GoldenImport.model.GoldenRaspberryAward;
import com.github.phfbueno.GoldenImport.repository.GoldenRaspberryAwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AwardService {

    @Autowired
    private GoldenRaspberryAwardRepository goldenRaspberryAwardRepository;

    public Map<String, List<Map<String, Object>>> calculateAwardIntervals(){

        List<GoldenRaspberryAward> awards = goldenRaspberryAwardRepository.findAll()
            .stream()
            .filter(GoldenRaspberryAward::getWinner)
            .collect(Collectors.toList());

        Map<String, List<GoldenRaspberryAward>> producerAwardsMap = new HashMap<>();
        for(GoldenRaspberryAward award : awards) {
            Arrays.stream(award.getProducers().split(","))
                    .map(String::trim)
                    .forEach(producer -> producerAwardsMap.computeIfAbsent(producer, k -> new ArrayList<>()).add(award));
        }

        List<Map<String, Object>> minIntervals = new ArrayList<>();
        List<Map<String, Object>> maxIntervals = new ArrayList<>();
        int minInterval = Integer.MAX_VALUE;
        int maxInterval = 0;

        for(String producer : producerAwardsMap.keySet()){
            List<GoldenRaspberryAward> producerAwards = producerAwardsMap.get(producer);
            producerAwards.sort(Comparator.comparingInt(GoldenRaspberryAward::getYearMovie));

            for (int i = 1; i < producerAwards.size() ; i++) {
                int interval = producerAwards.get(i).getYearMovie() - producerAwards.get(i -1).getYearMovie();

                if (interval < minInterval){
                    minInterval = interval;
                    minIntervals.clear();
                }

                if (interval == minInterval){
                    minIntervals.add(createIntervalEntry(producer, interval, producerAwards.get(i -1).getYearMovie(), producerAwards.get(i).getYearMovie()));
                }

                if (interval > maxInterval) {
                    maxInterval = interval;
                    maxIntervals.clear();
                }
                if (interval == maxInterval) {
                    maxIntervals.add(createIntervalEntry(producer, interval, producerAwards.get(i - 1).getYearMovie(), producerAwards.get(i).getYearMovie()));
                }

            }

        }

        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        result.put("min", minIntervals);
        result.put("max", maxIntervals);
        return result;
    }

    private Map<String, Object> createIntervalEntry(String producer, int interval, int previousWin, int followingWin) {
        Map<String, Object> intervalEntry = new HashMap<>();
        intervalEntry.put("producer", producer);
        intervalEntry.put("interval", interval);
        intervalEntry.put("previousWin", previousWin);
        intervalEntry.put("followingWin", followingWin);
        return intervalEntry;
    }
}
