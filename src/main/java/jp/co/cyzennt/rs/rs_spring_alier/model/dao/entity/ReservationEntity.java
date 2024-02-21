package jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity;


import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * This class is used for DAO entity for reservation
 * @author Alier Torrenueva
 * 01/24/2024
 */
@Data // Lombok annotation for generating getters, setters, toString, etc.
@Table(name="t_reservation") // Specifies the table name in the database.
@Entity // Specifies that this class is an entity mapped to a database table.
public class ReservationEntity {
	
	@Id // Specifies the primary key of the entity.
	private int id; // Reservation ID
	
	private String user_id; // User ID associated with the reservation
	
	// Specifies the column name in the database.
	public LocalDate res_date; // Reservation date
	
	private int room_id; // Room ID associated with the reservation
	
	private int time_id; // Time slot ID associated with the reservation
}
