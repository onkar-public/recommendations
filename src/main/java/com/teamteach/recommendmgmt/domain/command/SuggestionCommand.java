package com.teamteach.recommendmgmt.domain.command;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@Getter
public class SuggestionCommand extends ValidatingCommand{
    private String recommendationId;
    private String message;
    private String url;
    private String userType;
}
