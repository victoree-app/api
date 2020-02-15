package com.victoree.api.services;

import com.victoree.api.domains.Epic;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface EpicService {

  Page<Epic> getAllEpics(String projectId, PageRequest of);

  Epic saveEpic(Epic epic);

  Epic findOne(String id);

  long update(Epic epic);

  long delete(String id);

  List<Epic> getAllNoFilter();
}
