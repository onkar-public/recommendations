package com.teamteach.recommendmgmt.infra.api;

import com.teamteach.recommendmgmt.domain.command.*;
import com.teamteach.recommendmgmt.domain.models.Category;
import com.teamteach.recommendmgmt.domain.responses.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RequestMapping("recommendations")
public interface IRecommendResource {

    @PostMapping("")
    ResponseEntity<ObjectResponseDto> lookUpRecommendation(@RequestBody @Valid RecommendCommand recommendCommand, @RequestHeader HttpHeaders headers); 
        
    @ApiIgnore
    @GetMapping("/categories")
    ResponseEntity<ObjectListResponseDto<Category>> findCategories();
}