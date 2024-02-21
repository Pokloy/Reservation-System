package jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.UserDao;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.UserLogic;

/**
 * This class is used for User Logic Implementation of user. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
@Service
public class UserLogicImpl implements UserLogic {
	
	// Declares the UserDao
	@Autowired
	private UserDao userDao;
	
	/**
	 * @return userDao.findLoginUser(id) as a entity MUser
	 * @param id
	 */
	@Override
	public UserEntity getLoginUser(String id) {
		
		// Finds the Login user in the database.
		return userDao.findLoginUser(id);
	}
}
