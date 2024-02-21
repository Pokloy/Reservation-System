package jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.cyzennt.rs.rs_spring_alier.common.util.SecuritySession;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.ReservationDao;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.ReservationEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.ReservationLogic;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.UserLogic;

/**
 * This class is used for Reservation Logic Implementation of reservation. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
@Service
public class ReservationLogicImpl implements ReservationLogic {
	
	// Injects the ReservationDao bean.
	@Autowired
	private ReservationDao reservationDao;
	// Injects the SecuritySession bean.
	@Autowired
	private SecuritySession secSession;
	// Injects the UserLogic bean.
	@Autowired
	private UserLogic userService;

	/**
	 * @return reservationDao.findNotAvailReserved() as a list of entity MReservation
	 */
	@Override
	public List<ReservationEntity> findAvailReserved(){
		
		// Retrieves a list of available reservations.
		return reservationDao.findAvailReserved();
	}
	
	/**
	 * @return boolean result of isDeleted as null or 1 
	 * @param resDate
	 * @param roomId
	 * @param timeId
	 */
	@Override
	public boolean reservationExists(LocalDate resDate, int roomId, int timeId) {
		// declare a character named "isDeleted" that can store the result of reservationExists with the parameters of resDate, roomId and timeId
	    Character isDeleted = reservationDao.reservationExists(resDate, roomId, timeId);
	    
	    // return a boolean.
	    return isDeleted != null && isDeleted.equals('1');
	}
	
	/**
	 * @param mReserved
	 */
	@Override 
	public void insertReservation(List<ReservationEntity>  mReserved) {
		
		// Retrieve user data based on the session username.
		UserEntity user = userService.getLoginUser(secSession.getUsername());
		
		// Extract reservation details from the first reservation object.
		// Extracts the date
		LocalDate currentDate = mReserved.get(0).getRes_date();
		// Extracts the roomId
		int roomId = mReserved.get(0).getRoom_id();
		// Extracts the timeId
		int timeId = mReserved.get(0).getTime_id();
		
		// Add a reservation using user ID, date, room ID, and time ID.
		reservationDao.addReservation(user.getId(), currentDate, roomId, timeId);
	} 
	
	/** 
	 * @param id
	 */
	@Override
	public void deleteReservation(int id) {
		// Delete a reservation with the parameters of reservation id int his case its int id.
	    reservationDao.deleteReservation(id);
	}

	/**
	 * @return "List<ReservationEntity>"
	 * @param resDate
	 * @param roomId
	 * @param timeId
	 */
	@Override 
	public List<ReservationEntity> findUserSpecificReserve(LocalDate resDate, int roomId, int timeId) {
		
		// Retrieve reservations for a specific user, date, room, and time.
	    return reservationDao.findUserSpecificReserve(resDate,roomId,timeId);
	}
}
