package com.victoree.api.repositories;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.victoree.api.domains.Task;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository {

  public static final String TASK = "task";
  @Autowired
  private MongoTemplate mongoTemplate;

  public List<Task> getAllTasks(String storyid) {
    Query query = new Query();
    query.addCriteria(Criteria.where("storyid").is(storyid));
    return mongoTemplate.find(query, Task.class, TASK);
  }

  public long delete(String taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(taskId));
    DeleteResult deleteResult = mongoTemplate.remove(query, Task.class, TASK);
    return deleteResult.getDeletedCount();
  }

  public List<Task> getAllTasksById(String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is(id));
    return mongoTemplate.find(query, Task.class, TASK);
  }

  public long update(Task task) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(task.getId()));

    Update update = new Update();
    update
        .set("title", task.getTitle())
        .set("description", task.getDescription())
        .set("status", task.getStatus())
        .set("storyid", task.getStoryid());

    UpdateResult updateResult = mongoTemplate.upsert(query, update, Task.class, TASK);
    return updateResult.getModifiedCount();
  }

  public Task create(Task task) {
    return mongoTemplate.save(task, TASK);
  }
}
