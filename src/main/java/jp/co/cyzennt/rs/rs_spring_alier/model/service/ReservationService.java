package jp.co.cyzennt.rs.rs_spring_alier.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.ReservationEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.dto.ReservationInOutDto;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.ReservationObj;

public interface ReservationService {
	
	
	
	/**
	 * This method is for populating the time name to horizon
	 * @return ReservationInOutDto
	 */
	public ReservationInOutDto populateTime();

	/**
	 * This method is for populating the time name to vertical
	 * @return ReservationInOutDto
	 */
	public ReservationInOutDto populateRoom();
	
	/**
	 * This method is for produces the data thru checkbox check status.
	 * @return ReservationInOutDto
	 * @param model
	 */
	public ReservationInOutDto updateCheckboxStates(LocalDate  currentDate);
	
	/**
	 * This method is for Looking the login's user details
	 * @return ReservationInOutDto
	 */
	public ReservationInOutDto loginUser();
	
	/**
	 * This method is for Looking the login's user details
	 * @return ReservationInOutDto
	 * @param currentDate
	 * @param roomId
	 * @param timeId
	 */
	public ReservationInOutDto findExistReservation(LocalDate currentDate, int roomId, int timeId );
	
	/**
	 * This method is for setting up boolean values to be used on disabling checkboxes.
	 * @return ReservationInOutDto
	 * @param currentDate
	 */
	public ReservationInOutDto disamblingKey(LocalDate currentDate);
	
	/**
	 * This method is for looking for specific reservation
	 * @return ReservationInOutDto
	 * @param currentDate
	 * @param roomId
	 * @param timeId
	 */
	public ReservationInOutDto findSpecificReservationList(LocalDate currentDate, int roomId,int timeId);
	
	/**
	 * This method is for deleting a reservation
	 * @param id
	 */
	public void deleteReservation(int id);
	
	/**
	 * This method is for adding a reservation
	 * @param reservationObj
	 */
	public void insertReservationService(ReservationObj reservationObj);
}



