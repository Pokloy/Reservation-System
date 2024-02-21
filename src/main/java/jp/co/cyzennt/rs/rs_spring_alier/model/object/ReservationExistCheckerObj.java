package jp.co.cyzennt.rs.rs_spring_alier.model.object;

import lombok.Data;

/**
 * This class is used for Reservation existance checker Object . 
 * @author Alier Torrenueva
 * 01/26/2024
 */
@Data
public class ReservationExistCheckerObj {
	// declares a boolean named "reservChecker"
	private boolean reservChecker;
	
	// declares String named "ownerChecker"
	private String ownerChecker;
}
