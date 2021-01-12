package com.info.finder.repository.sequence;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class NextSequenceService {

    private MongoOperations mongo;

    public NextSequenceService(MongoOperations mongo) {
        this.mongo = mongo;
    }

    public long getNextSequence(String seqName) {
        CustomSequence counter = mongo.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                CustomSequence.class
        );
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

}
