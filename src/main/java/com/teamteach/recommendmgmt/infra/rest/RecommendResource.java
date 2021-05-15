package com.teamteach.recommendmgmt.infra.rest;

import com.teamteach.recommendmgmt.domain.command.*;
import com.teamteach.recommendmgmt.domain.command.*;
import com.teamteach.recommendmgmt.domain.ports.in.*;
import com.teamteach.recommendmgmt.domain.responses.*;
import com.teamteach.recommendmgmt.domain.usecases.*;
import com.teamteach.recommendmgmt.domain.models.*;
import com.teamteach.recommendmgmt.infra.api.*;
import com.teamteach.recommendmgmt.shared.AbstractAppController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
class RecommendResource extends AbstractAppController implements IRecommendResource {

    final IRecommendMgmt recommendMgmt;

    @Override
    @ApiOperation(value = "Finds recommendations", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectResponseDto> lookUpRecommendation(RecommendCommand recommendCommand) {
        return ResponseEntity.ok(recommendMgmt.findRecommendation(recommendCommand));
    }

    @Override
    @ApiOperation(value = "Finds categories", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectListResponseDto> findCategories() {
        return ResponseEntity.ok(recommendMgmt.findCategories());
    }
}
