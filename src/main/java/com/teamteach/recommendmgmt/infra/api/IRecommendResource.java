package com.teamteach.recommendmgmt.infra.api;

import com.teamteach.recommendmgmt.domain.command.*;
import com.teamteach.recommendmgmt.domain.models.Category;
import com.teamteach.recommendmgmt.domain.responses.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@RequestMapping("recommendations")
public interface IRecommendResource {

    @PostMapping("")
    ResponseEntity<ObjectResponseDto> lookUpRecommendation(@RequestBody @Valid RecommendCommand recommendCommand, @RequestHeader HttpHeaders headers); 

    // @PostMapping("/save")
    //     ResponseEntity<ObjectResponseDto> storeRecommendation(@RequestBody @Valid RecommendCommand recommendCommand, @RequestHeader HttpHeaders headers);
        
    @GetMapping("/categories")
    ResponseEntity<ObjectListResponseDto<Category>> findCategories();

    @GetMapping("/getAll")
    ResponseEntity<ObjectListResponseDto<RecommendationDashboardResponse>> getAllRecommendations();

    @GetMapping("/get/{recommendationId}")
    ResponseEntity<ObjectResponseDto> getRecommendation(@PathVariable String recommendationId);
}