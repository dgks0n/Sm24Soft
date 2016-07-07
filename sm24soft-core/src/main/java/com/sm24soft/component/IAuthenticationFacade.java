package com.sm24soft.component;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {

	Authentication getAuthentication();
}
