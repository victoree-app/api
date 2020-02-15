package com.victoree.api.repositories;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.victoree.api.domains.Epic;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class EpicRepository {

  public static final String EPIC = "epic";
  @Autowired
  private MongoTemplate mongoTemplate;

  public Page<Epic> findAll(String projectId, PageRequest of) {
    Query query = new Query();
    query.addCriteria(Criteria.where("projectid").is(projectId));
    query.with(of);

    List<Epic> epics = mongoTemplate.find(query, Epic.class, EPIC);
    return PageableExecutionUtils.getPage(epics, of,
        () -> mongoTemplate.count(query, Epic.class));
  }

  public Epic createEpic(Epic epic) {
    return mongoTemplate.insert(epic);
  }

  public Epic findOne(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoTemplate.findOne(query, Epic.class, EPIC);
  }

  public long updateEpic(Epic epic) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(epic.getId()));
    Update update = new Update();
    update.set("name", epic.getName())
        .set("startdate", epic.getStartdate())
        .set("startsprint", epic.getStartsprint())
        .set("associatedstories", epic.getAssociatedstories())
        .set("status", epic.getStatus())
        .set("projectid", epic.getProjectid());

    UpdateResult updateResult = mongoTemplate.upsert(query, update, Epic.class, EPIC);
    return updateResult.getModifiedCount();
  }

  public long delete(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    DeleteResult deleteResult = mongoTemplate.remove(query, Epic.class, EPIC);
    return deleteResult.getDeletedCount();
  }

  public List<Epic> findAll() {
    return mongoTemplate.findAll(Epic.class, "epic");
  }
}
