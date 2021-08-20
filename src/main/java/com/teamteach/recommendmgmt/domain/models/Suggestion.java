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

public class Suggestion {

    private String suggestion;
    private String url; 
    private String userType;
}