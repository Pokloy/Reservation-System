package jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity;

import javax.persistence.*;

import lombok.Data;


/**
 * This class is used for DAO entity for time
 * @author Alier Torrenueva
 * 01/24/2024
 */
@Data // Lombok annotation for generating getters, setters, toString, etc.
@Table(name="m_time") // Specifies the table name in the database.
@Entity // Specifies that this class is an entity mapped to a database table.
public class TimeEntity {
    @Id // Specifies the primary key of the entity.
    private int id; // Time ID
    
    private String name; // Time name
    
    private enum isDeleted { // Enum for indicating whether the time slot is deleted or not
        DELETED('1'), // Indicates the time slot is deleted
        NOT_DELETED(null); // Indicates the time slot is not deleted
        
        private final Character code; // Code representing the time slot's deletion status
        
        isDeleted(Character code) { // Constructor for enum
            this.code = code;
        }
        
        @SuppressWarnings("unused")
        public Character getCode() { // Getter for the deletion status code
            return code;
        }
    }
    
    @Enumerated(EnumType.STRING) // Specifies how the enum is persisted in the database
    private isDeleted isDeleted; // Deletion status of the time slot
}

