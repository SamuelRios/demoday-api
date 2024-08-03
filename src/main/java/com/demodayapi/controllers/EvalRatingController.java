package com.demodayapi.controllers;

import com.demodayapi.models.EvalRating;
import com.demodayapi.services.EvalRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class EvalRatingController {
    @Autowired
    private EvalRatingService evalRatingService;

    @GetMapping("/totalRating/{id}")
    public ResponseEntity<Double> getTotalRating(@PathVariable int id){
        try{
            double totalRatingByProject = evalRatingService.getTotalRatingByProject(id);
            double decimalTotalRatingByProject = Math.round(totalRatingByProject * 100.0) / 100.0;
            return new ResponseEntity<>(decimalTotalRatingByProject, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
