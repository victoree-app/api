package com.victoree.api.repositories;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.victoree.api.domains.Story;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class StoriesRepository {

  public static final String STORY = "story";
  @Autowired
  MongoTemplate mongoTemplate;

  public Page<Story> findAll(Query query, Pageable pageable) {
    query.with(pageable);
    List<Story> stories = mongoTemplate.find(query, Story.class, STORY);
    return PageableExecutionUtils.getPage(stories, pageable,
        () -> mongoTemplate.count(query, Story.class));

  }

  public Story save(Story story) {
    return mongoTemplate.save(story, STORY);
  }

  public long update(Story story) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(story.getId()));
    Update update = new Update()
        .set("title", story.getTitle())
        .set("description", story.getDescription())
        .set("sprint", story.getSprint())
        .set("assignee", story.getAssignee())
        .set("epic", story.getEpic())
        .set("orphan", story.isOrphan());

    UpdateResult updateResult = mongoTemplate.upsert(query, update, Story.class, STORY);
    return updateResult.getModifiedCount();
  }

  public Story findOne(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return mongoTemplate.findOne(query, Story.class, STORY);
  }

  public long delete(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    DeleteResult deleteResult = mongoTemplate.remove(query, Story.class, STORY);
    return deleteResult.getDeletedCount();
  }

  public List<Story> findAll(Query query) {
    return mongoTemplate.find(query, Story.class, STORY);
  }
}
