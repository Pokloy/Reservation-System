package jp.co.cyzennt.rs.rs_spring_alier.model.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.co.cyzennt.rs.rs_spring_alier.common.util.SecuritySession;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.ReservationEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.RoomEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.TimeEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.dto.ReservationInOutDto;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.ReservationLogic;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.RoomLogic;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.TimeLogic;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.UserLogic;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.CheckStatusObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.DisamblingKeyObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.ReservationDeleteObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.ReservationExistCheckerObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.ReservationObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.RoomObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.TimeObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.UserObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.service.ReservationService;


/**
 * This class is the service implementation for the reservations.
 * @author Alier Torrenueva
 * 01/23/2024
 */
@Service
public class ReservationServiceImpl implements ReservationService {
	// Declaration of property room service
	@Autowired
	private RoomLogic roomLogic;
	
	// Declaration of property time service
	@Autowired
	private TimeLogic timeLogic;
	
    // Declaration of property user logic
	@Autowired
	private UserLogic userService;
	
	// Declaration of property security session
	@Autowired
	private SecuritySession secSession;
	
	// Declaration of property reservation service
	@Autowired
	private ReservationLogic reservationLogic;
	

	/**
	 * @return timeIO from ReservationInOutDto
	 */
	public ReservationInOutDto populateTime() {
		
		// Create a new instance of ReservationInOutDto. 	
		ReservationInOutDto timeIO = new ReservationInOutDto();
	
		// Get a list of available times.
		List<TimeEntity> availTime = timeLogic.availTime();
		
		// Create a new list to hold time objects
		List<TimeObj> timeObjList = new ArrayList<>();
		
		// Check if the list of available times is not null and not empty.
		if(availTime != null && availTime.size() != 0) {
			
			// Iterate through each available time.
			for(TimeEntity time : availTime) {
				
				// Create a new TimeObj instance.
				TimeObj timeObj = new TimeObj();
				
				// Set the ID of the TimeObj from the MTime object
				timeObj.setId(time.getId());
				
				// Set the name of the TimeObj from the MTime object.
				timeObj.setName(time.getName());
				
				// Add the TimeObj to the list of time objects.
				timeObjList.add(timeObj);
			}
			// Set the list of time objects in the ReservationInOutDto.
			timeIO.setTimeObj(timeObjList);
		}
		// Return the ReservationInOutDto containing the list of time objects.
		return timeIO;
	}
	
	/**
	 * @return roomIO from ReservationInOutDto
	 */
	public ReservationInOutDto populateRoom() {
		// Create a new instance of ReservationInOutDto to hold room information.
		ReservationInOutDto roomIO = new ReservationInOutDto();
		
		// Get a list of available rooms.
	    List<RoomEntity> availRooms = roomLogic.availRoom();
	    
	    // Create a new list to hold room objects.
	    List<RoomObj> roomObjList = new ArrayList<>();
	    
	    // Check if the list of available rooms is not null and not empty.
	    if(availRooms != null && availRooms.size() != 0) {
	    	
	    	// Iterate through each available room.
	    	for(RoomEntity room : availRooms) {
	    		
	    	  // Create a new RoomObj instance.
	    	  RoomObj roomObj = new RoomObj();
	    	  
	    	  // Set the ID of the RoomObj from the MRoom object.
	    	  roomObj.setId(room.getId());
	    	 
	    	  // Set the name of the RoomObj from the MRoom object.
	    	  roomObj.setName(room.getName());
	    	  
	    	  //  Add the RoomObj to the list of room objects.
	    	  roomObjList.add(roomObj);
	    	}
	    	
	    	// Set the list of room objects in the ReservationInOutDto.
	    	roomIO.setRoomObj(roomObjList);
	    }
	    // Return the ReservationInOutDto containing the list of room objects.
	    return roomIO;
	}
	
	
	/**
	 * @return return a List boolean used to calculate checks for checkboxes"
	 * @param availRoom
	 * @param availTime
	 * @param availReservation
	 * @param currentDate
	 */
	public List<Boolean> calculateShouldCheckList(List<RoomEntity> availRooms, List<TimeEntity> availTime, List<ReservationEntity> availReservation, LocalDate  currentDate)
	{
		// I choose this data type in displaying data on checkbox because it prevents duplication issue of data.
		// Create a HashSet to store checked combinations.
	    Set<String> checkedCombinations = new HashSet<>();
	    
	    // For each MReservation in availReservation.
	    for (ReservationEntity reservation : availReservation) {
	    	
	    	// Add a checked combination to the HashSet.
	        checkedCombinations.add(reservation.getRoom_id() + "-" + reservation.getTime_id() + "-" + reservation.getRes_date());
	    }
	    
	    // Add a boolean attribute to the model indicating whether each checkbox should be checked
	    List<Boolean> shouldCheckList = new ArrayList<>();
	    
		 // For each MRoom in availRooms.
		    for (RoomEntity room : availRooms) {
		    	
		    	// For each MTime in availTime.
		        for (TimeEntity time : availTime) {
		        	
		        	// Add a boolean value to shouldCheckList based on checkedCombinations.
		        	// returns boolean result on the hashset.
		            shouldCheckList.add(checkedCombinations.contains(room.getId() + "-" + time.getId() + "-" + currentDate));
		        }
		    }
		// Return the shouldCheckList a list of boolean.
	    return shouldCheckList;
	}
	

	
	/**
	 * Used to update the checkbox when the date is changed.
	 * @param model
	 * @param currentDate
	 */
	public ReservationInOutDto updateCheckboxStates(LocalDate  currentDate) {
	    // Retrieve available rooms using roomService.
	    List<RoomEntity> availRooms = roomLogic.availRoom();

	    // Retrieve available times using timeService.
	    List<TimeEntity> availTime = timeLogic.availTime();

	    // Retrieve available reservations using reservationLogic.
	    List<ReservationEntity> availReservation = reservationLogic.findAvailReserved();

	    // Create a new instance of ReservationInOutDto to hold checkbox status information.
		 ReservationInOutDto checkboxStatusIO = new ReservationInOutDto();
		 
		 // Recalculate shouldCheckList based on the new date
		 List<Boolean> shouldCheckList = calculateShouldCheckList(availRooms, availTime, availReservation, currentDate);
		 
		 // Create a new list to hold checkbox status objects.
		 List<CheckStatusObj> checkStatusObjList = new ArrayList<>();
		 

			 // Iterate through each boolean status in the list.
		     for (Boolean status : shouldCheckList) {
	
		         // Create a new CheckStatusObj for each boolean value in shouldCheckList
		         CheckStatusObj statusObj = new CheckStatusObj();
		         
		         // Set the status of the CheckStatusObj.
		         statusObj.setStatus(status);
		         
		         // Add the CheckStatusObj to the list
		         checkStatusObjList.add(statusObj);
		     }
		     // Set the list of CheckStatusObj in checkboxStatusIO
		     checkboxStatusIO.setCheckStatusObj(checkStatusObjList);
		     
		 // Return the ReservationInOutDto containing the list of checkbox status objects.
		  return checkboxStatusIO;    
	}

	
	/**
	 * @return reservationOwnershipMap a key/value pair to be used on disabling the checkbox
	 * @param availReservation 
	 * @param user
	 * @param currentDate
	 */
    public Map<String ,Boolean> generateReservationOwnershipMap(List<ReservationEntity> availReservation, UserEntity user, LocalDate  currentDate) {
    	  
    	// Create a HashMap for reservation ownership status.
        Map<String , Boolean> reservationOwnershipMap = new HashMap<>();
        boolean ownsReservationDate;
        
        // For each MReservation in availReservation.
        for (ReservationEntity reservation : availReservation) {
        	
        	// Create a key for the reservationOwnershipMap.
            String key = reservation.getRoom_id() + "-" + reservation.getTime_id();
            
            // Calculate ownership status for the reservation.
            ownsReservationDate = !reservation.getUser_id().equals(user.getId()) && reservation.getRes_date().equals(currentDate);
            
            boolean currentOwnership;
            
            try {
                currentOwnership = reservationOwnershipMap.get(key);
            } catch (NullPointerException e) {
            	currentOwnership = false;
            }
            
            if (!currentOwnership) {
                // Put the ownership status into the reservationOwnershipMap.
                reservationOwnershipMap.put(key, ownsReservationDate);
            }
        }
       
        // Return the reservationOwnershipMap.
        return reservationOwnershipMap;
    }
    
	/**
	 * @return reservationIO from ReservationInOutDto
	 * @param timeId 
	 * @param roomId
	 * @param currentDate
	 */
    public ReservationInOutDto findSpecificReservationList(LocalDate currentDate, int roomId,int timeId){
    	
    	// Create a new instance of ReservationInOutDto to hold reservation information.
    	ReservationInOutDto reservationIO = new ReservationInOutDto();
    	
        // Find the specific reservation ID for the user.
    	List<ReservationEntity> reservationsAvailable = reservationLogic.findUserSpecificReserve(currentDate, roomId, timeId);
    	
    	// Create a new list to hold reservation delete objects
    	List<ReservationDeleteObj> reservationsDeleteObj = new ArrayList<>();
    	
    	// Check if the list of available reservations is not null and not empty.
    	if(reservationsAvailable != null && reservationsAvailable.size() != 0) {
    		
    		// Iterate through each available reservation.
    		for(ReservationEntity reservAvail : reservationsAvailable) {
    			
    			// Create a new ReservationDeleteObj instance.
    			ReservationDeleteObj reserveDelete = new ReservationDeleteObj();
    			
    			// Set the ID of the ReservationDeleteObj from the MReservation object.
    			reserveDelete.setId(reservAvail.getId());
    			
    			// Set the user ID of the ReservationDeleteObj from the MReservation object.
    			reserveDelete.setUser_id(reservAvail.getUser_id());
    			
    			// Add the ReservationDeleteObj to the list of reservation delete objects.
    			reservationsDeleteObj.add(reserveDelete);
    		}
    		// Set the list of reservation delete objects in the ReservationInOutDto.
    		reservationIO.setDeleteListReservation(reservationsDeleteObj);
    	}
    	// Return the ReservationInOutDto containing the list of reservation delete objects.
    	return reservationIO;
    }
   
	/**
	 * method used to delete reservation
	 * @param id 
	 */
    public void deleteReservation(int id) {
    	
    	//Delete a reservation using the reservationLogic.
    	reservationLogic.deleteReservation(id);
    }


	/**
	 * @return reservationIO from ReservationInOutDto
	 */
    public ReservationInOutDto retrieveReservation () {
    	// Create a new instance of ReservationInOutDto.
    	ReservationInOutDto reservationIO = new ReservationInOutDto();
    	
    	// Retrieve a list of available reservations.
    	List<ReservationEntity> reservations = reservationLogic.findAvailReserved();
    	
    	// Create a new list to hold reservation objects.
    	List<ReservationObj> reservationObjList = new ArrayList<>();
    	
    	// Check if the list of reservations is not null and not empty.
    	if(reservations != null && reservations.size() != 0) {
    		
    		// Iterate through each reservation in the list.
    		for(ReservationEntity mreserv : reservations) {
    			
    			// Create a new ReservationObj instance.
    			ReservationObj reserveObj = new ReservationObj();
    			
    			// Set the ID of the ReservationObj from the MReservation object.
    			reserveObj.setId(mreserv.getId());
    			
    			// Set the reservation date of the ReservationObj from the MReservation object.
    			reserveObj.setRes_date(mreserv.getRes_date());
    			
    			// Set the room ID of the ReservationObj from the MReservation object.
    			reserveObj.setRoom_id(mreserv.getRoom_id());
    			
    			// Set the time ID of the ReservationObj from the MReservation object.
    			reserveObj.setTime_id(mreserv.getTime_id());
    			
    			// Set the user ID of the ReservationObj from the MReservation object.
    			reserveObj.setUser_id(mreserv.getUser_id());
    			
    			// Add the ReservationObj to the list of reservation objects.
    			reservationObjList.add(reserveObj);
    		}
    		// Set the list of reservation objects in the ReservationInOutDto.
    		reservationIO.setReserveObj(reservationObjList);
    	}
    	// Return the ReservationInOutDto containing the list of reservation objects.
    	return reservationIO;
    }
    
	/**
	 * @return reservationExistIO from ReservationInOutDto
	 * @param currentDate
	 * @param roomId
	 * @param timeId
	 */
    public ReservationInOutDto findExistReservation(LocalDate currentDate, int roomId, int timeId ) {
    	// Create a new instance of ReservationInOutDto.
    	ReservationInOutDto reservationExistIO = new ReservationInOutDto();
    	
    	// Stores boolean result of the reservationExist method from logic.
    	boolean reservationsExist = reservationLogic.reservationExists(currentDate, roomId, timeId);
    	
		// Instantiate ReservationExistCheckerObj object.
		ReservationExistCheckerObj reserveExistObj = new ReservationExistCheckerObj();
		
		// Set the reservChecker attribute.
		reserveExistObj.setReservChecker(reservationsExist);
    		
    		
    	// Set reservationExistChecker attribute of reservationExistIO.
    	reservationExistIO.setReservationExistChecker(reserveExistObj);
    	
    	// Return reservationExistIO object.
    	return reservationExistIO;
    }
   
	/**
	 * @return userIO from ReservationInOutDto
	 */ 
    public ReservationInOutDto loginUser() {
    	// Create a new ReservationInOutDto object.
    	ReservationInOutDto userIO = new ReservationInOutDto();
    	// Retrieve the login user from userService.
    	UserEntity loginUser = userService.getLoginUser(secSession.getUsername());
    	// Create a new UserObj object.

		// Create a new UserObj object.
		UserObj userObj = new UserObj();
		// Set id, name, and password attributes of userObj.
		userObj.setId(loginUser.getId());
		userObj.setName(loginUser.getName());
		userObj.setPassword(loginUser.getPassword());
		// Assign userObj to userObjList.

    	// Set userObjList attribute of userIO.
    	userIO.setUserObj(userObj);
    	// Return userIO object.
    	return userIO;
    }
    
	/**
	 * @return keyIO from ReservationInOutDto
	 * @param currentDate
	 */
    public ReservationInOutDto disamblingKey(LocalDate currentDate) {
    	// Create a new ReservationInOutDto object.
    	ReservationInOutDto keyIO = new ReservationInOutDto();
    	// Retrieve a list of available reservations.
    	List<ReservationEntity> availReservation = reservationLogic.findAvailReserved();
    	// Retrieve the logged-in user.
    	UserEntity user = userService.getLoginUser(secSession.getUsername());
    	// Generate a reservation ownership map based on the available reservations, user, and current date.
    	Map<String, Boolean> updatedMap = generateReservationOwnershipMap(availReservation, user, currentDate);
    	// Create a new DisamblingKeyObj object.
    	DisamblingKeyObj disamblingKeyObjList = new DisamblingKeyObj();
	    // Assign disambkeyObj to disamblingKeyObjList.
	    disamblingKeyObjList.setKey(new HashMap<>(updatedMap));
    	// Set disamblingKeyObjList attribute of keyIO.
    	keyIO.setDisamblingKeyObj(disamblingKeyObjList);
    	// Return keyIO object.
    	return keyIO;
    }

	/**
	 * Used for adding data in database
	 * @param reservationObj
	 */
    public void insertReservationService(ReservationObj reservationObj) {
        // Create a new list to hold the reservation to be inserted
        List<ReservationEntity> insertReservations = new ArrayList<>();

        // Convert the ReservationObj into a single MReservation object
        ReservationEntity insertReservation = new ReservationEntity();
        insertReservation.setRes_date(reservationObj.getRes_date());
        insertReservation.setRoom_id(reservationObj.getRoom_id());
        insertReservation.setTime_id(reservationObj.getTime_id());

        // Add the single reservation to the list
        insertReservations.add(insertReservation);

        // Insert the reservation(s) into the database
        reservationLogic.insertReservation(insertReservations);
    }
}
