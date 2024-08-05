package com.demodayapi.controllers;

import com.demodayapi.enums.DemodayStatusEnum;
import com.demodayapi.enums.PhaseEvalRateEnum;
import com.demodayapi.models.Demoday;
import com.demodayapi.models.EvalRating;
import com.demodayapi.services.DemodayService;
import com.demodayapi.services.EvalRatingService;

import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private DemodayService demodayService;

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


    @GetMapping("/totalRating")
    public ResponseEntity<?> getAllRatings(@RequestParam(defaultValue = "phase") Integer phase, @RequestParam(defaultValue = "demodayId") Integer demodayId ) {
        System.out.println(phase);
        System.out.println("aaaalooo");

        Map<Integer, Double> allRatings;
        
        if(phase == 3){
            
            if(this.demodayService.isAfterDemodayPhase(demodayId, DemodayStatusEnum.PHASE3)){
                allRatings = evalRatingService.getProjectRatingsByDemodayAndPhase(demodayId, PhaseEvalRateEnum.PHASE3);
            } else return new ResponseEntity<>("Fase 3 ainda não foi finalizada.", HttpStatus.UNAUTHORIZED);
            
        } else if(phase == 4){

            if(this.demodayService.isAfterDemodayPhase(demodayId, DemodayStatusEnum.PHASE4)){
                allRatings = evalRatingService.getProjectRatingsByDemodayAndPhase(demodayId, PhaseEvalRateEnum.PHASE4);
            } else return new ResponseEntity<>("Fase 4 ainda não foi finalizada.", HttpStatus.UNAUTHORIZED);

        } else return new ResponseEntity<>("Só é possível calcular as notas para a fase 3 ou para fase 4.", HttpStatus.BAD_REQUEST);

        Map<Integer, Double> decimalRatings = allRatings.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Math.round(entry.getValue() * 100.0) / 100.0,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return new ResponseEntity<>(decimalRatings, HttpStatus.OK);
    }


}
