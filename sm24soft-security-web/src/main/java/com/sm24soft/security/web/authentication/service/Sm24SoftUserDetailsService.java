package com.sm24soft.security.web.authentication.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface Sm24SoftUserDetailsService extends UserDetailsService {

	public final String SERVICE_ID = "userDetailsService";
}
