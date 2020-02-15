package com.victoree.api.repositories;

import com.victoree.api.domains.Sprint;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SprintRepository {

  public static final String SPRINT = "sprint";

  @Autowired
  private MongoTemplate mongoTemplate;

  public List<Sprint> findAll() {
    return mongoTemplate.find(new Query(), Sprint.class, SPRINT);
  }

  public List<Sprint> findById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoTemplate.find(query, Sprint.class, SPRINT);
  }
}
