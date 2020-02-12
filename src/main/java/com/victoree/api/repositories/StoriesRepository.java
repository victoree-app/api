package com.victoree.api.repositories;

import com.victoree.api.domains.Story;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class StoriesRepository {

  @Autowired
  MongoTemplate mongoTemplate;

  public Page<Story> findAll(Query query, Pageable pageable) {
    query.with(pageable);
    List<Story> stories = mongoTemplate.find(query, Story.class, "story");
    return PageableExecutionUtils.getPage(stories, pageable,
        () -> mongoTemplate.count(query, Story.class));

  }

  public Story save(Story story) {
    return mongoTemplate.save(story, "story");
  }
}
