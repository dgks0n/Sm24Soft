/**
 * 
 */
package com.sm24soft.security.web.authentication.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sm24soft.security.web.authentication.Sm24SoftSimpleAccount;
import com.sm24soft.security.web.authentication.service.Sm24SoftUserDetailsService;

/**
 * @author sondn
 *
 */
@Service(Sm24SoftUserDetailsService.SERVICE_ID)
public class UserDetailsServiceImpl implements Sm24SoftUserDetailsService {

	private Sm24SoftSimpleAccount simpleAccount;
	
	@Autowired
	public UserDetailsServiceImpl(Sm24SoftSimpleAccount simpleAccount) {
		this.simpleAccount = simpleAccount;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isEmpty(username)
				|| !simpleAccount.getUsername().equals(username)) {
			throw new UsernameNotFoundException("Username is incorrect. It must not be empty and NULL.");
		}
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		// Ideally it should be fetched from database and populated instance of
        // #org.springframework.security.core.userdetails.User should be returned from this method
        UserDetails user = new User(username, simpleAccount.getPassword(), true, true, true, true, authorities);
        return user;
	}
}
