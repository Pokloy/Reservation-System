package jp.co.cyzennt.rs.rs_spring_alier.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.TimeEntity;

/**
 * This class is used for querying in databases for time. 
 * @author Alier Torrenueva
 * 01/25/2024
 */
public interface TimeDao extends JpaRepository<TimeEntity, Integer> {
	// This String is used for querying on finding all time with the condition of is_deleted = NULL
	final String SQL_FIND_TIME_AVAILABLE = "SELECT * "
										+ "	FROM m_time "
										+ " WHERE is_deleted is NULL ";

	// Binds the SQL_FIND_TIME_AVAILABLE to the method "findAvailTime"
	@Query(value=SQL_FIND_TIME_AVAILABLE, nativeQuery=true)
	
	/**
	 * @return "List<TimeEntity>"
	 */
	public List<TimeEntity> findAvailTime() throws DataAccessException;
}
