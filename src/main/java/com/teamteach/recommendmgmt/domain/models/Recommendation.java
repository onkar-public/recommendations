package com.teamteach.recommendmgmt.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "recommendations")
public class Recommendation {

    @Transient
        public static final String SEQUENCE_NAME = "recommendations_sequence";

    @Id
    protected String categoryId;
    private String title;
    private String colour; 
    private boolean isParent;
    private boolean isTeacher;
    private List<Scope> scopes;   
}