package com.teamteach.recommendmgmt.infra.rest;

import com.teamteach.recommendmgmt.domain.command.RecommendCommand;
import com.teamteach.recommendmgmt.domain.models.Category;
import com.teamteach.recommendmgmt.domain.ports.in.IRecommendMgmt;
import com.teamteach.recommendmgmt.domain.responses.ObjectListResponseDto;
import com.teamteach.recommendmgmt.domain.responses.ObjectResponseDto;
import com.teamteach.recommendmgmt.infra.api.IRecommendResource;
import com.teamteach.recommendmgmt.shared.AbstractAppController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
class RecommendResource extends AbstractAppController implements IRecommendResource {

    @Autowired
    private IRecommendMgmt recommendMgmt;

    @Override
    @ApiOperation(value = "Finds recommendations", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectResponseDto> lookUpRecommendation(RecommendCommand recommendCommand) {
        return ResponseEntity.ok(recommendMgmt.findRecommendation(recommendCommand));
    }

    @Override
    @ApiOperation(value = "Finds categories", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<ObjectListResponseDto<Category>> findCategories() {
        return ResponseEntity.ok(recommendMgmt.findCategories());
    }
}
