package com.teamteach.recommendmgmt.domain.command;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@Getter
public class RecommendationCommand extends ValidatingCommand{
    private String recommendationId;
    private String categoryId;
    private String keyword;
    private String[] synonyms;
}
