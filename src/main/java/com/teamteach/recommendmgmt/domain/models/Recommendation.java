package com.teamteach.recommendmgmt.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.TextIndexed;
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
    protected String id;
    protected String categoryId;
    
    @TextIndexed(weight=2)
    private String word;
    
    @TextIndexed
    private String[] synonyms;

    private List<Suggestion> suggestions;   
}