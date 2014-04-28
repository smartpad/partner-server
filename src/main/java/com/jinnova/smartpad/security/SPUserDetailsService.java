package com.jinnova.smartpad.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SPUserDetailsService implements UserDetailsService {

	/*private abstract class SPSecurityUserDetails implements UserDetails {
		
	}*/
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		//PartnerManager.instance.
		return null;
	}

}