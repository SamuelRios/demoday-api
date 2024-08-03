package com.demodayapi.controllers;

import com.demodayapi.models.EvalRating;
import com.demodayapi.services.EvalRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EvalRatingController {
    @Autowired
    private EvalRatingService evalRatingService;

    @GetMapping("/totalRatings")
    public ResponseEntity<Map<Integer, Double>> getAllRatings() {
        try {
            Map<Integer, Double> allRatings = evalRatingService.getAllProjectRatings();
            Map<Integer, Double> decimalRatings = allRatings.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> Math.round(entry.getValue() * 100.0) / 100.0,
                            (e1, e2) -> e1, LinkedHashMap::new));
            return new ResponseEntity<>(decimalRatings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
