package jp.co.cyzennt.rs.rs_spring_alier.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;

/**
 * This class is used for querying in databases for user. 
 * @author Alier Torrenueva
 * 01/25/2024
 */
public interface UserDao extends JpaRepository<UserEntity, String> {
	
	// This String is used for querying on finding the user with the condition of id that matches the log-in id
	final String JPQL_FIND_LOGIN_USER = " SELECT u "																										
										+ " FROM UserEntity u " 																
										+ " WHERE u.id = :id ";
	
	
	// Binds the JPQL_FIND_LOGIN_USER to the method "findAvailTime"
	@Query(JPQL_FIND_LOGIN_USER)																										
	
	/**
	 * @return "UserEntity"
	 * @param id
	 */
	public UserEntity findLoginUser(String id) throws DataAccessException;
}
