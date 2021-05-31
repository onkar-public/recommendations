package com.teamteach.recommendmgmt.domain.command;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@Getter
public class RecommendCommand extends ValidatingCommand{
    private String text;
    private String recommendationId;
    private String suggestionIndex;
}
