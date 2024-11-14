package com.github.phfbueno.GoldenImport.controller;

import com.github.phfbueno.GoldenImport.repository.GoldenRaspberryAwardRepository;
import com.github.phfbueno.GoldenImport.service.AwardService;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/awards")
public class AwardController {

    @Autowired
    private AwardService awardService;

    public AwardController(GoldenRaspberryAwardRepository goldenRaspberryAwardRepository) {
    }

    @GetMapping("/intervals")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getAwardIntervals() {

        Map<String, List<Map<String, Object>>> intervals = awardService.calculateAwardIntervals();

        return ResponseEntity.ok(intervals);
    }

}
