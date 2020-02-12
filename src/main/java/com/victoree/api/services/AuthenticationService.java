package com.victoree.api.services;

import com.victoree.api.exceptions.UnauthorizedLoginException;
import com.victoree.api.exceptions.UnauthorizedRequestException;
import com.victoree.api.io.AuthenticationResponse;
import com.victoree.api.io.LoginRequest;

public interface AuthenticationService {

  AuthenticationResponse authenticate(LoginRequest loginRequest) throws UnauthorizedLoginException;

  String getUserNameFromSessionId(String sessionId) throws UnauthorizedRequestException;
}
