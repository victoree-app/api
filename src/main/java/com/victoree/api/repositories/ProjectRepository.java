package com.victoree.api.repositories;

import com.victoree.api.domains.Project;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

public interface ProjectRepository{

  Page<Project> findAll(Query query, Pageable pageable);

  Project findOne(String userName, Map<String, String> filters);

  Project save(Project project);

  long update(String userName, Project project);

  long delete(String userName, String id);
}
