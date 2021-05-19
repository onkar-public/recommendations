package com.teamteach.recommendmgmt.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Scope {

    private String word;
    private String[] synonyms;
    private List<Suggestion> suggestions;   
}