package com.victoree.api.services;

import com.victoree.api.domains.User;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.repositories.UserOpsRepository;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserOpsServiceImpl implements UserOpsService {

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  UserOpsRepository userOpsRepository;

  @Autowired
  AuthenticationService authenticationService;

  @Override
  public List<User> getAllUsers(String sessionId) throws UnauthorizedRequestException {

    String username = authenticationService.getUserNameFromSessionId(sessionId);
    Set<String> permissions = userOpsRepository.getPermissionsForUser(username);
    if(permissions.contains("viewusers")){
      List<User> users = userOpsRepository.findActiveUsers();
      return users;
    }

    return Collections.EMPTY_LIST;
  }

  @Override
  public String getUserNameFromSessionId(String sessionId) throws UnauthorizedRequestException {
    return authenticationService.getUserNameFromSessionId(sessionId);
  }
}
