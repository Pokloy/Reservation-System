package jp.co.cyzennt.rs.rs_spring_alier.model.object;

import lombok.Data;

/**
 * This class is used for user list object. 
 * @author Alier Torrenueva
 * 01/26/2024
 */

//Lombok annotation for generating getters, setters, and toString methods.
@Data
public class UserObj {
	
	// String field to store the ID.
	private String id;
	
	// String field to store the name.
	private String name;
	
	// String field to store the password.
	private String password;
}
