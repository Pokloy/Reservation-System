package jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.TimeDao;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.TimeEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.TimeLogic;

/**
 * This class is used for Room Logic Implementation of room. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
@Service
public class TimeLogicImpl implements TimeLogic   {
	
	// Declares the timeDao
	@Autowired
	private TimeDao timeDao;

	
	/**
	 * @return timeDao.findAvailTime() as a list of entity MTime
	 */
	@Override
	public List<TimeEntity> availTime() {
		
		// Finds all of the available time in the database.
		return timeDao.findAvailTime();
	}
	
	
}
