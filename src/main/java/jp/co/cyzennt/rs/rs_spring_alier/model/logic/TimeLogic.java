package jp.co.cyzennt.rs.rs_spring_alier.model.logic;

import java.util.List;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.TimeEntity;

/**
 * This class is used for Time Logic Service of Time. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
public interface TimeLogic {
	/**
	 * @return List<TimeEntity>
	 */
	public List<TimeEntity> availTime();
}
