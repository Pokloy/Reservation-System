package jp.co.cyzennt.rs.rs_spring_alier.model.logic;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.ReservationEntity;

/**
 * This class is used for Reservation Logic Service of Reservation. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
public interface ReservationLogic {
	
	/**
	 * @return List<ReservationEntity>
	 */
	public List<ReservationEntity> findAvailReserved();

	/**
	 * @param insertReservations
	 */
	public void insertReservation(List<ReservationEntity> insertReservations);
	
	/**
	 * @param id
	 */
	public void deleteReservation(int id);

	/**
	 * @return boolean
	 * @param resDate
	 * @param roomId
	 * @param timeId
	 */
	boolean reservationExists(LocalDate resDate, int roomId, int timeId);
	
	/**
	 * @return List<ReservationEntity>
	 * @param resDate
	 * @param roomId
	 * @param timeId
	 */
	public List<ReservationEntity> findUserSpecificReserve(LocalDate resDate, int roomId, int timeId);

}