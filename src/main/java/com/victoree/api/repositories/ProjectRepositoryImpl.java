package com.victoree.api.repositories;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.victoree.api.domains.Project;
import java.util.List;
import java.util.Map;
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
public class ProjectRepositoryImpl implements ProjectRepository {

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public Page<Project> findAll(Query query, Pageable pageable) {
    query.with(pageable);
    List<Project> projects =  mongoTemplate.find(query,Project.class,"project");
    return PageableExecutionUtils.getPage(projects,pageable,
        () -> mongoTemplate.count(query,Project.class));
  }

  @Override
  public Project findOne(String userName, Map<String, String> filters) {
    Query query = new Query();
    query.addCriteria(Criteria.where("owner").is(userName));

    filters.keySet()
        .stream()
        .forEach(k -> query.addCriteria(Criteria.where(k).is(filters.get(k))));

    Project project = mongoTemplate.findOne(query,Project.class,"project");
    return project;
  }

  @Override
  public Project save(Project project) {
    return mongoTemplate.save(project,"project");
  }

  @Override
  public long update(String userName, Project project) {
    Update update = new Update()
        .set("name",project.getName())
        .set("owner", project.getOwner())
        .set("description", project.getDescription())
        .set("isActive",project.isActive());
    UpdateResult updateResult =  mongoTemplate.upsert(new Query().addCriteria(Criteria.where("owner").is(userName))
        .addCriteria(Criteria.where("id").is(project.getId())),update,Project.class,"project");
        return updateResult.getModifiedCount();
  }

  @Override
  public long delete(String userName, String id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("owner").is(userName))
        .addCriteria(Criteria.where("_id").is(id));
    DeleteResult deleteResult = mongoTemplate.remove(query,Project.class,"project");
    return deleteResult.getDeletedCount();
  }
}
