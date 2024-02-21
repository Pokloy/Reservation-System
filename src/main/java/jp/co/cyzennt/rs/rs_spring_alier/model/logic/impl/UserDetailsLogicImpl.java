package jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.UserLogic;

/**
 * This class is used for retrieving username
 * @author Alier Torrenueva
 * 01/26/2024
 */

@Service
public class UserDetailsLogicImpl implements UserDetailsService {
	// Declares the UserLogic
	@Autowired
	private UserLogic userservice;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		
		// Calls the "getLoginUser" method from the "userservice" bean to retrieve a user by username, and assigns it to the "loginUser" variable of type MUser.
		UserEntity loginUser = userservice.getLoginUser(id);

		
		// Creates a security authority named "GENERAL."
		GrantedAuthority authority = new SimpleGrantedAuthority("GENERAL");
		
		// Creates an empty list for security authorities.
        List<GrantedAuthority> authorities = new ArrayList<>();			
        
        
        // Adds a security authority to the list.
        authorities.add(authority);			
			
        // UserDetails Creation			
        UserDetails userDetails = new User(loginUser.getId(), loginUser.getPassword(), authorities);	
      
			
        // Returns the authenticated user details.
        return userDetails;			
	}
}
