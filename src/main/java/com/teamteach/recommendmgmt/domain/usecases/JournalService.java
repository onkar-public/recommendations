package com.teamteach.recommendmgmt.domain.usecases;

import java.util.*;
import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

@Service
public class JournalService {
    @Value("${gateway.url}")
    String gateway;

    RestTemplate restTemplate = new RestTemplate();

    public Integer getLastSuggestionIndex(String recoId, String accessToken){
        try {
            String url = gateway+"/journals/entry/lastEntry/"+recoId;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", accessToken); 
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity <String> entity = new HttpEntity <> (null, headers);
            ResponseEntity <String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            JsonNode respoJsonNode = new ObjectMapper().readTree(response.getBody());
            boolean success = respoJsonNode.get("success").asBoolean();
            if (success) {
                JsonNode journalEntry = respoJsonNode.get("object");
                String suggestionIndex = journalEntry.get("suggestionIndex").asText();
                return Integer.valueOf(suggestionIndex);
            } else {
                return 0;
            }
        }
        catch (IOException e) {
            return 0;
        }
    }    
}