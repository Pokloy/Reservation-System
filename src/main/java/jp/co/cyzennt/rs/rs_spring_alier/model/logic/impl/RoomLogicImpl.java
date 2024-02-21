package jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.RoomDao;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.RoomEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.RoomLogic;

/**
 * This class is used for Room Logic Implementation of room. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
@Service
public class RoomLogicImpl implements RoomLogic {
	
	// Declares the roomDao
	@Autowired
	private RoomDao roomDao;
	
	/**
	 * @return roomDao.findAvailRoom() as a list of entity MRoom
	 */
	@Override
	public List<RoomEntity> availRoom() {
		//Finds all of the available room from the database.
		return roomDao.findAvailRoom();
	}
}
