package com.teamteach.recommendmgmt.domain.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
public class SequenceGeneratorService {

    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public String generateSequence(String seqName) {
        DocumentIdSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("sequence",1), options().returnNew(true).upsert(true),
                DocumentIdSequence.class);
        return String.valueOf(!Objects.isNull(counter) ? counter.getSequence() : 1);
    }
}
