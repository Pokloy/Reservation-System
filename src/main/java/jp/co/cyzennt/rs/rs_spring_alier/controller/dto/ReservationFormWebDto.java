package jp.co.cyzennt.rs.rs_spring_alier.controller.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.Data;


/**
 * This class is a web dto for reservation
 * @author Alier Torrenueva
 * 01/18/2024
 */

//Lombok annotation for generating getters, setters, and toString methods.
@Data
public class ReservationFormWebDto {

	// Identifies the reservation with an integer ID.
    @Id
    private int id;
    
    // Stores the user's ID as a string.
    private String user_Id;
    
    // Represents the reservation date as a LocalDate object.
    private LocalDate res_Date;
    
    // Holds the ID of the reserved room.
    private int room_Id;
    
    // Stores the ID of the reserved time slot.
    private int time_Id;
}

