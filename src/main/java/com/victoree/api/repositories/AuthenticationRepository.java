package com.victoree.api.repositories;

import com.victoree.api.domains.AuthSession;
import com.victoree.api.domains.AuthenticationResponse;
import com.victoree.api.exceptions.UnauthorizedLoginException;
import com.victoree.api.exceptions.UnauthorizedRequestException;

public interface AuthenticationRepository {

  AuthenticationResponse authenticate(String username, String password)
      throws UnauthorizedLoginException;

  AuthSession findSessionById(String sessionId) throws UnauthorizedRequestException;
}
