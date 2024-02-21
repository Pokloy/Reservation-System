package jp.co.cyzennt.rs.rs_spring_alier.model.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.ReservationEntity;

/**
 * This class is used for querying in databases for reservation. 
 * @author Alier Torrenueva
 * 01/24/2024
 */
public interface ReservationDao extends JpaRepository<ReservationEntity, Integer> {
	
	// This String is used for querying the existing reservation which uses the is_deleted as condition
	final String SQL_FIND_NOT_RESERVED = " SELECT * "
										+ " FROM t_reservation "
										+ " WHERE is_deleted = 1";
	
	// This String is used for querying for adding a new reservation
	final String SQL_ADDING_RESERVATION = "INSERT INTO t_reservation (user_id, res_date, room_id, time_id,is_deleted)"
			+ "  VALUES(:user_id,:res_date,:room_id,:time_id,1)";
	
	
	// This String is used for querying a delete of a reservation
	final String SQL_DELETE_RESERVATION = " DELETE "
										+ " FROM t_reservation "
										+ " WHERE id = :id ";
	
	// This String is used for querying an is_deleted status of a certain reservation which uses the roomId,timeId and date as condition
	final String SQL_FIND_EXISTING_RESERVATION = "SELECT is_deleted "
													+ " FROM t_reservation "
													+ " WHERE res_date = :resDate "
													+ " AND room_id = :roomId "
													+ " AND time_id = :timeId ";
	
	// This String is used for querying a specific reservation with the condition of date, roomid and timeid
	final String SQL_FIND_USER_SPECIFIC_RESERVATION = " SELECT * "
													+ " FROM t_reservation "
													+ " WHERE res_date = :resDate "
													+ " AND room_id = :roomId "
													+ " AND time_id = :timeId ";
	
	
	// Binds the SQL_FIND_NOT_RESERVED to the method "findNotAvailReserved"
	@Query(value=SQL_FIND_NOT_RESERVED, nativeQuery=true)
	
	/**
	 * @return "List<ReservationEntity>"
	 */
	public List<ReservationEntity> findAvailReserved() throws DataAccessException;
	
	// Binds the SQL_ADDING_RESERVATION to the method "addReservation"
	@Query(value=SQL_ADDING_RESERVATION, nativeQuery=true)
	
	/**
	 * @return "ReservationEntity"
	 * @param user_id
	 * @param res_date
	 * @param room_id
	 * @param time_id
	 */
	public ReservationEntity addReservation (String user_id,LocalDate res_date, int room_id, int time_id);
	
	// Binds the SQL_DELETE_RESERVATION to the method "deleteReservation"
	@Query(value=SQL_DELETE_RESERVATION, nativeQuery = true)
	
	/**
	 * @param id
	 */
	public void deleteReservation(int id) throws DataAccessException;

	// Binds the SQL_FIND_EXISTING_RESERVATION to the method "reservationExists"
	@Query(value=SQL_FIND_EXISTING_RESERVATION, nativeQuery=true)
	
	/**
	 * @return "Character"
	 * @param resDate
	 * @param room_id
	 * @param time_id
	 */
	public Character reservationExists(LocalDate resDate, int roomId, int timeId);
	
	// Binds the SQL_FIND_USER_SPECIFIC_RESERVATION to the method "findUserSpecificReserve"
	@Query(value = SQL_FIND_USER_SPECIFIC_RESERVATION, nativeQuery = true)
	
	/**
	 * @return "List<ReservationEntity>"
	 * @param resDate
	 * @param room_id
	 * @param time_id
	 */
	public List<ReservationEntity> findUserSpecificReserve(LocalDate resDate, int roomId, int timeId);
}
