package com.victoree.api.services.impl;

import com.victoree.api.domains.Project;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.repositories.ProjectRepository;
import com.victoree.api.services.ProjectService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

  @Autowired
  ProjectRepository projectRepository;

  @Override
  public Page<Project> getAll(String username, int pageNum, int pageSize, String sortBy,
      Integer order)
      throws UnauthorizedRequestException {
    Query query = new Query()
        .addCriteria(Criteria.where("owner").is(username));
    Pageable pageRequest = PageRequest.of(pageNum, pageSize, Direction.ASC, "_id");
    if (sortBy != null) {
      pageRequest = PageRequest
          .of(pageNum, pageSize, order == 1 ? Direction.ASC : Direction.DESC, sortBy);
    }
    return projectRepository.findAll(query, pageRequest);
  }

  @Override
  public Project save(Project project) {
    return projectRepository.save(project);
  }

  @Override
  public long update(String username, Project project) {
    long updateCount = projectRepository.update(username, project);
    log.info("{} rows updated", updateCount);
    return updateCount;
  }

  @Override
  public Project getOne(String username, String id) {
    Map<String, String> filters = new LinkedHashMap<>();
    filters.put("_id", id);
    Project project = projectRepository.findOne(username, filters);
    return project;
  }

  @Override
  public long delete(String username, String id) {
    return projectRepository.delete(username, id);
  }

  @Override
  public List<Project> getAll(String username) {
    return projectRepository.findAll(username);
  }
}
