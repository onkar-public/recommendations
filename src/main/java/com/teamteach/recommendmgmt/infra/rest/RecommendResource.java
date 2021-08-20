package com.teamteach.recommendmgmt.infra.rest;

import com.teamteach.recommendmgmt.domain.command.RecommendCommand;
import com.teamteach.recommendmgmt.domain.command.RecommendationCommand;
import com.teamteach.recommendmgmt.domain.command.SuggestionCommand;
import com.teamteach.recommendmgmt.domain.models.Category;
import com.teamteach.recommendmgmt.domain.ports.in.IRecommendMgmt;
import com.teamteach.recommendmgmt.domain.responses.ObjectListResponseDto;
import com.teamteach.recommendmgmt.domain.responses.ObjectResponseDto;
import com.teamteach.recommendmgmt.domain.responses.RecommendationDashboardResponse;
import com.teamteach.recommendmgmt.infra.api.IRecommendResource;
import com.teamteach.recommendmgmt.shared.AbstractAppController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
class RecommendResource extends AbstractAppController implements IRecommendResource {

    @Autowired
    private IRecommendMgmt recommendMgmt;

    @Override
    @ApiOperation(value = "Finds recommendations", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectResponseDto> lookUpRecommendation(RecommendCommand recommendCommand, HttpHeaders headers) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(recommendMgmt.findRecommendation(recommendCommand, token));
    }

    @Override
    @ApiOperation(value = "Finds categories", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectListResponseDto<Category>> findCategories() {
        return ResponseEntity.ok(recommendMgmt.findCategories());
    }

    @Override
    @ApiOperation(value = "Saves a recommendation", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectResponseDto> storeRecommendation(@Valid RecommendationCommand recommendationCommand, HttpHeaders headers) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(recommendMgmt.storeRecommendation(recommendationCommand,token));
    }

    @Override
    @ApiOperation(value = "Saves a suggestion for a recommendation", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectResponseDto> storeSuggestion(@Valid SuggestionCommand suggestionCommand, HttpHeaders headers) {
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(recommendMgmt.storeSuggestion(suggestionCommand,token));
    }

    @Override
    @ApiOperation(value = "Finds all recommendations", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectListResponseDto<RecommendationDashboardResponse>> getAllRecommendations() {
        return ResponseEntity.ok(recommendMgmt.getAllRecommendations());
    }

    @Override
    @ApiOperation(value = "Finds a recommendation", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectResponseDto> getRecommendation(String recommendationId) {
        return ResponseEntity.ok(recommendMgmt.getRecommendation(recommendationId));
    }
}
