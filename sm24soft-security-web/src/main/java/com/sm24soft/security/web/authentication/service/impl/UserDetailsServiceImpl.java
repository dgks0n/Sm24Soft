/**
 * 
 */
package com.sm24soft.security.web.authentication.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sm24soft.security.web.authentication.service.Sm24SoftUserDetailsService;

/**
 * @author sondn
 *
 */
@Service(Sm24SoftUserDetailsService.SERVICE_ID)
public class UserDetailsServiceImpl implements Sm24SoftUserDetailsService {

	public UserDetailsServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
