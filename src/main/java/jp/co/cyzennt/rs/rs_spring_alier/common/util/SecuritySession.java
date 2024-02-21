package jp.co.cyzennt.rs.rs_spring_alier.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


/**
 * This class get the username of the login user thru security springboot.
 * @author Alier Torrenueva
 * 01/17/2024
 */
@Component
public class SecuritySession {
	
	
	/**
	 * This method will get the username of the log-in user
	 * @return null"
	 */
	public String getUsername() {
		
		// retrieves the current user
	    Authentication authentication = SecurityContextHolder.getContext()							
	            .getAuthentication();							
	    
	    // validate if the current user is null
	    if (authentication != null) {			
	    	
	    	// This code retrieves the principal (user) object from the authentication information.
	        Object principal = authentication.getPrincipal();							
	        
	        // This condition checks if the principal object is an instance of UserDetails.
	        if (principal instanceof UserDetails) {							
	        	
	        	// This returns the username associated with the UserDetails object in the principal.
	            return ((UserDetails) principal).getUsername();	
	            
	        }			
	        
	        // This returns the string representation of the principal if it is not an instance of UserDetails.
	        return (String) principal.toString();							
	    }	
	    
	    // returns null
	    return null;							

	}
}
