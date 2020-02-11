package com.victoree.api.repositories;


import static com.victoree.api.constants.VictoreeConstants.CN_PERMISSION;

import com.victoree.api.domains.Permission;
import com.victoree.api.domains.User;
import com.victoree.api.domains.UserDetail;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserOpsRepository {


  @Autowired
  MongoTemplate mongoTemplate;

  public Set<String> getPermissionsForUser(String username) {
    Query query = new Query();
    query.addCriteria(Criteria.where("username").is(username));
    UserDetail userDetail = mongoTemplate.find(query, UserDetail.class, "userdetail").get(0);
    String permissionGroup = userDetail.getPermissionGroup();

    query = new Query();
    query.addCriteria(Criteria.where("group").is(permissionGroup));
    List<Permission> permissions = mongoTemplate.find(query, Permission.class, CN_PERMISSION);

    return permissions.stream()
        .map(permission -> permission.getPermission())
        .collect(Collectors.toSet());
  }

  public List<User> findActiveUsers() {
    Query query = new Query();
    query.addCriteria(Criteria.where("status").is("A"));
    List<User> users = mongoTemplate.find(query, User.class, "user");
    return users;
  }
}
