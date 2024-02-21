package jp.co.cyzennt.rs.rs_spring_alier.model.dto;

import java.util.HashMap;
import java.util.List;

import jp.co.cyzennt.rs.rs_spring_alier.model.object.CheckStatusObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.DisamblingKeyObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.ReservationDeleteObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.ReservationExistCheckerObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.ReservationObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.RoomObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.TimeObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.UserObj;
import lombok.Data;

/**
 * This class is used for in and out DTO.
 * @author Alier Torrenueva
 * 01/26/2024
 */
@Data // Lombok annotation for generating getters, setters, toString, etc.
public class ReservationInOutDto {
	// List of RoomObj objects
    List<RoomObj> roomObj; 
    // List of TimeObj objects
    List<TimeObj> timeObj; 
    // List of CheckStatusObj objects
    List<CheckStatusObj> checkStatusObj;
    // List of ReservationObj objects
    List<ReservationObj> reserveObj; 
    // UserObj object
    UserObj userObj; 
    // DisamblingKeyObj object
    public DisamblingKeyObj disamblingKeyObj; 
    // ReservationExistCheckerObj object
    ReservationExistCheckerObj reservationExistChecker; 
    // List of ReservationDeleteObj objects
    List<ReservationDeleteObj> deleteListReservation;
	public ReservationInOutDto setUserObj(String string, ReservationInOutDto user) {
		// TODO Auto-generated method stub
		return null;
	} 
}

