package jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * This class is used for DAO entity for users
 * @author Alier Torrenueva
 * 01/24/2024
 */
@Data // Lombok annotation for generating getters, setters, toString, etc.
@Entity // Specifies that this class is an entity mapped to a database table.
@Table(name="users") // Specifies the table name in the database.
public class UserEntity {
	
	@Id // Specifies the primary key of the entity.
	private String id; // User ID
	
	private String name; // User name
	
	private String password; // User password
}

