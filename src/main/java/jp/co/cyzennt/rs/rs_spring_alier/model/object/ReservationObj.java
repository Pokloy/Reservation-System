package jp.co.cyzennt.rs.rs_spring_alier.model.object;

import java.time.LocalDate;

import lombok.Data;

/**
 * This class is used for reservation list objec. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
@Data
public class ReservationObj {
	// declares the id of the reservation
	private int id;
	
	// declares the user_id
	private String user_id;
	
	// declares the date of the reservation
	public LocalDate res_date ;
	
	// declares the room_id of the reservation
	private int room_id;
	
	// declares the time_id of the reservation
	private int time_id;
	
}
