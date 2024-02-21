package jp.co.cyzennt.rs.rs_spring_alier.login.dto;


import lombok.Data;


/**
 * This class is a webDTO for Log-in
 * @author Alier Torrenueva
 * 01/19/2024
 */

@Data
public class LoginFormWebDto {
	
	// Declares the userId
	private String id;	
	
	// Declares the password
	private String password;	
}

