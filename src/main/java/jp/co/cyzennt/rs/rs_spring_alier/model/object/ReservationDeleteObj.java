package jp.co.cyzennt.rs.rs_spring_alier.model.object;

import lombok.Data;

/**
 * This class is used for reservation deletion Object. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
@Data
public class ReservationDeleteObj {
	// Declares the Id of reservation
	private int id;
	
	// Declares the userId
	private String user_id;
}
