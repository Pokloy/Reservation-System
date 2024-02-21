package jp.co.cyzennt.rs.rs_spring_alier.model.logic;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.RoomEntity;

/**
 * This class is used for Room Logic Service of Room. 
 * @author Alier Torrenueva
 * 01/26/2024
 */
public interface RoomLogic {
	/**
	 * @return List<RoomEntity>
	 */
	public List<RoomEntity> availRoom();
}
