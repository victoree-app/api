package com.victoree.api.services;

import com.victoree.api.domains.Story;
import com.victoree.api.repositories.StoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class StoriesServiceImpl implements StoriesService {

  @Autowired
  StoriesRepository storiesRepository;

  @Override
  public Page<Story> getAll(String username, Boolean linked, Pageable pageable) {

    Query query = new Query();
    query
        //.addCriteria(Criteria.where("owner").is(username))
        .addCriteria(Criteria.where("orphan").is(true));
    return storiesRepository.findAll(query, pageable);
  }

  @Override
  public Story save(Story story) {
    return storiesRepository.save(story);
  }

  @Override
  public Story getOne(String id) {
    return storiesRepository.findOne(id);
  }

  @Override
  public long update(Story story) {
    return storiesRepository.update(story);
  }

  @Override
  public long delete(String id) {
    return storiesRepository.delete(id);
  }
}
