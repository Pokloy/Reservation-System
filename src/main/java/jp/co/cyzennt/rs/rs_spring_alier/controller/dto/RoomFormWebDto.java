package jp.co.cyzennt.rs.rs_spring_alier.controller.dto;

import lombok.Data;
import javax.persistence.Id;


/**
 * This class is a web dto for room
 * @author Alier Torrenueva
 * 01/18/2024
 */

//Lombok annotation for generating getters, setters, and toString methods.
@Data
public class RoomFormWebDto {
	
	// Integer field representing the ID.
	@Id
	private int Id;
	
	// String field to store the name.
	private String name;
	
	// Boolean field indicating deletion status.
	private boolean is_delete;
}
