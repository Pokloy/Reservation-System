package jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity;

import javax.persistence.*;

import lombok.Data;


/**
 * This class is used for DAO entity for room
 * @author Alier Torrenueva
 * 01/24/2024
 */

@Data // Lombok annotation for generating getters, setters, toString, etc.
@Table(name = "m_room") // Specifies the table name in the database.
@Entity // Specifies that this class is an entity mapped to a database table.
public class RoomEntity {
    @Id // Specifies the primary key of the entity.
    private int id; // Room ID
    
    private String name; // Room name
    
    private enum isDeleted { // Enum for indicating whether the room is deleted or not
        DELETED('1'), // Indicates the room is deleted
        NOT_DELETED(null); // Indicates the room is not deleted
        
        private final Character code; // Code representing the room's deletion status
        
        isDeleted(Character code) { // Constructor for enum
            this.code = code;
        }
        
        @SuppressWarnings("unused")
        public Character getCode() { // Getter for the deletion status code
            return code;
        }
    }
    
    @Enumerated(EnumType.STRING) // Specifies how the enum is persisted in the database
    private isDeleted isDeleted; // Deletion status of the room
}













