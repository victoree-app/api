package com.victoree.api.services;

import com.victoree.api.domains.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoriesService {

  Page<Story> getAll(String username, Boolean linked, Pageable pageable);

  Story save(Story story);
}