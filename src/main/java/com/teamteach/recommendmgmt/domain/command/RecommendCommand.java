package com.teamteach.recommendmgmt.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@Getter
public class RecommendCommand extends ValidatingCommand{
    @NotNull
    private String text;
}
