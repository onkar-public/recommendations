package com.teamteach.recommendmgmt.infra.api;

import com.teamteach.recommendmgmt.domain.command.*;
import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.domain.responses.*;
import com.teamteach.recommendmgmt.domain.usecases.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RequestMapping("recommendations")
public interface IRecommendResource {

    @PostMapping("")
    ResponseEntity<ObjectResponseDto> lookUpRecommendation(@RequestBody @Valid RecommendCommand recommendCommand); 
        
    @ApiIgnore
    @GetMapping("/categories")
    ResponseEntity<ObjectListResponseDto> findCategories();
}
