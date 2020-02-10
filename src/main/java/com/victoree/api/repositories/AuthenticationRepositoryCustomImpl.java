package com.victoree.api.repositories;

import com.victoree.api.domains.AuthSession;
import com.victoree.api.domains.AuthenticationResponse;
import com.victoree.api.domains.UserDetail;
import com.victoree.api.exceptions.UnauthorizedLoginException;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class AuthenticationRepositoryCustomImpl implements AuthenticationRepository {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public AuthenticationResponse authenticate(String username, String password)
      throws UnauthorizedLoginException {
    Query query = new Query();
    query.addCriteria(Criteria.where("username").is(username)).addCriteria(Criteria.where("password").is(password));
    List<UserDetail> userDetails = mongoTemplate.find(query, UserDetail.class, "userdetail");
    if (CollectionUtils.isEmpty(userDetails) || userDetails.size() != 1)
      throw new UnauthorizedLoginException("unauthorized login attempted");

    AuthSession authSession = mongoTemplate.save(AuthSession.builder()
        .sessionId(UUID.randomUUID().toString())
        .time(LocalDateTime.now().plusMinutes(30))
        .username(username)
        .build(),"session");

    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    authenticationResponse.setSessionId(authSession.getSessionId());
    return authenticationResponse;
  }

  @Override
  public AuthSession findSessionById(String sessionId) throws UnauthorizedRequestException {
    Query query = new Query();
    query.addCriteria(Criteria.where("sessionId").is(sessionId));
    AuthSession authSession = mongoTemplate.findOne(query,AuthSession.class,"session");
    if(authSession == null){
      throw new UnauthorizedRequestException("no session active");
    }
    return authSession;
  }


}
