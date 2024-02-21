package jp.co.cyzennt.rs.rs_spring_alier.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.RoomEntity;

/**
 * This class is used for querying in databases for room. 
 * @author Alier Torrenueva
 * 01/25/2024
 */
public interface RoomDao extends JpaRepository<RoomEntity, Integer> {
	// This String is used for querying on finding all roooms with the condition of is_deleted = NULL
	final String SQL_FIND_ROOM_AVAILABLE = " SELECT * "
											+ " FROM m_room "
											+ " WHERE is_deleted is NULL ";
	
	// Binds the SQL_FIND_ROOM_AVAILABLE to the method "findAvailRoom"
	@Query(value=SQL_FIND_ROOM_AVAILABLE, nativeQuery= true)
	
	/**
	 * @return "List<RoomEntity>"
	 */
	public List<RoomEntity> findAvailRoom() throws DataAccessException;
	
}

