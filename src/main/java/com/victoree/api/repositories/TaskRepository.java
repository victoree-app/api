package com.victoree.api.repositories;

import com.mongodb.client.result.DeleteResult;
import com.victoree.api.domains.Task;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

  @Autowired
  MongoTemplate mongoTemplate;

  public List<Task> getAllTasks(String storyid) {
    Query query = new Query();
    query.addCriteria(Criteria.where("storyid").is(storyid));
    return mongoTemplate.find(query, Task.class, "task");
  }

  public long delete(String taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(taskId));
    DeleteResult deleteResult = mongoTemplate.remove(query, Task.class, "task");
    return deleteResult.getDeletedCount();
  }
}
