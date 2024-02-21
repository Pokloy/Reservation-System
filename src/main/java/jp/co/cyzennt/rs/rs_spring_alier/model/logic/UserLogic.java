package jp.co.cyzennt.rs.rs_spring_alier.model.logic;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;

/**
 * This class is used for User Logic Service of User. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
public interface UserLogic {
	
	/**
	 * @return UserEntity
	 * @param id
	 */
	public UserEntity getLoginUser(String id);
	
}
