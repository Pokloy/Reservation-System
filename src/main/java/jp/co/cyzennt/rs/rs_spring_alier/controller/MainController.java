package jp.co.cyzennt.rs.rs_spring_alier.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.cyzennt.rs.rs_spring_alier.controller.dto.ReservationFormWebDto;
import jp.co.cyzennt.rs.rs_spring_alier.model.dto.ReservationInOutDto;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.ReservationDeleteObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.ReservationObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.service.ReservationService;
import lombok.Data;

/**
 * This class log-outs the user.
 * @author Alier Torrenueva
 * 01/19/2024
 */
@Controller
@RequestMapping(value="/main")
public class MainController {
	// Declaration of property room service
	@Autowired
	private ReservationService reservationService;
	
	// Declaration of model mapper
	@Autowired
	private ModelMapper modelMapper;

	// Declaration of time did not set to Autowire due to certain errors.
	public LocalDate currentDate = LocalDate.now();
	

	/**
	 * @return return a URL for "/main/main"
	 * @param model
	 * @param reservationForm
	 * @param fromInsert
	 */
	@GetMapping(value="/main")
	public String getMain(Model model, 
			@ModelAttribute ReservationFormWebDto reservationForm) 
		{
		// call all checked checkboxes and display all data available
		ReservationInOutDto allDataAndCheckStatus = reservationService.updateCheckboxStates(currentDate);	    
		
		// calls the populateTime from the reservationService and place it on the availTime.
		ReservationInOutDto availTime = reservationService.populateTime();
	
		// calls the populateRoom from the reservationService and place it on the availRoom.
		ReservationInOutDto availRoom = reservationService.populateRoom();

		// gets the user's data thru security session using username 
	    ReservationInOutDto user = reservationService.loginUser();

	    // This part if for dissAsembling the checkbox that the user log-in not owned the reservation.
	    ReservationInOutDto updatedMap = reservationService.disamblingKey(currentDate);
	    
		
		// Add the "allDataAndCheckStatus" object to the model attribute.
		model.addAttribute("allDataAndCheckStatus", allDataAndCheckStatus);
	
	    // Add currentDate attribute to the model.
	    model.addAttribute("currentDate", currentDate);
	    
		// Add the "availTime.getTimeObj()" to the model attribute.
		model.addAttribute("availTime", availTime.getTimeObj());
		
		
		// Add the "availRoom.getRoomObj()" to the model attribute.
		model.addAttribute("availRooms", availRoom.getRoomObj());
		
	    // Add log-in username attribute to the model.
	    model.addAttribute("user", user.getUserObj());
	    
	    // Stores the hashed map result values of "updatedMap" to object "reservationOwnershipMap" 
	    model.addAttribute("reservationOwnershipMap", updatedMap.disamblingKeyObj.getKey());
	    
	    // returns a string that can be used as a part of URL
    	return "/main/main"; 
		}

	
	
	/**
	 * @return return a URL that redirects to "/main/main"
	 * @param model
	 * @param a request parameter of "currentDate" named "currentDateStr" datatype of String
	 * @param a request parameter of "selectedHours" named "selectedHours" datatype of ArrayList=String
	 * @param reservationForm
	 */
	@PostMapping(value = "/reserved")
	public String insertReserve(Model model, 
	        @RequestParam(name = "currentDate", required = false) String currentDateStr,
	        @RequestParam(name = "selectedHours", required = false) ArrayList<String> selectedHours,
	        ReservationFormWebDto reservationForm) throws ParseException {
	    
	    //Declare currentDate
	    LocalDate currentDate = null;
	    
	    // Converts the currentDateStr to local date and store it to "currentDate"
	    currentDate = LocalDate.parse(currentDateStr);
	    

			// gets the user's data thru security session using username 
		    ReservationInOutDto user = reservationService.loginUser();
	        
	        // For each String in selectedHours.
	        for (String selectedHour : selectedHours) {
	        	
	        	// Extract room ID from selectedHour string.
	            int roomId = Integer.parseInt(selectedHour.split("-")[0]);
	            
	            // Extract time ID from selectedHour string.
	            int timeId = Integer.parseInt(selectedHour.split("-")[1]);
	            

	            	// Set the user ID in the reservation form.
	                reservationForm.setUser_Id(user.getUserObj().getId());
	                
	                // Set the reservation date in the reservation form.
	                reservationForm.setRes_Date(currentDate);
	                
	                // Set the room id in the reservation form.
	                reservationForm.setRoom_Id(roomId);
	                
	                // Set the time id in the reservation form.
	                reservationForm.setTime_Id(timeId);
	                
	                // Map reservationForm to ReservationObj using ModelMapper.
	                ReservationObj reservedObj = modelMapper.map(reservationForm, ReservationObj.class); 
	                
	                // calls the method insertReservationService of reservationService and uses the "reservedObj" as parameters
	                reservationService.insertReservationService(reservedObj);
	                
	            
	        }
	    
	    // returns a string that can be used as a part of URL
	    return "redirect:/main/main";
	}
	
	
	

	
	/**
	 * @return return a URL that redirects to "/main/main"
	 * @param model
	 * @param a request parameter of "currentDate" named "currentDateStr" datatype of String
	 * @param a request parameter of "selectedHours" named "selectedHours" datatype of ArrayList=String
	 * @param a request parameter of "uncheckedCheckboxIds" named "uncheckedCheckboxId" datatype of ArrayList=String
	 * @param reservationForm
	 */
	@PostMapping(value = "/deleted")
	public String deleteReserve(Model model,
			 					@RequestParam(name = "currentDate", required = false) String currentDateStr,
	                            @RequestParam(name = "selectedHours", required = false) ArrayList<String> selectedHours,
	                            @RequestParam(name = "uncheckedCheckboxIds", required = false) ArrayList<String> uncheckedCheckboxId,
	                            ReservationFormWebDto reservationForm) {
		
		//Declare currentDate
	    LocalDate currentDate = null;
	    
	    // Converts the currentDateStr to local date and store it to "currentDate"
	    currentDate = LocalDate.parse(currentDateStr);
	    
		// For each String in uncheckedCheckboxId.
	    for (String uncheckSelectedReserve : uncheckedCheckboxId) {
	    	
	    	// Extract room ID from uncheckSelectedReserve string.
	        int roomId = Integer.parseInt(uncheckSelectedReserve.split("-")[0]);
	        
	        // Extract time ID from uncheckSelectedReserve string.
	        int timeId = Integer.parseInt(uncheckSelectedReserve.split("-")[1]);
           
        	// Retrieve specific reservation details for given parameters.
	        ReservationInOutDto specificReservationObject = reservationService.findSpecificReservationList(currentDate, roomId, timeId);
	        
	        // Assign list of specific reservation objects.
	        List<ReservationDeleteObj> specificReservationList = specificReservationObject.getDeleteListReservation();
	        
	        // Iterate through specific reservation objects.
	        for (ReservationDeleteObj reservation : specificReservationList) {

	            // Get reservation ID from reservation object.
	            int reservationId = reservation.getId();
                 // Delete the reservation with the specified ID
                 reservationService.deleteReservation(reservationId);
	        }
	    }
	    //  Redirect to the main page.
	    return "redirect:/main/main";
	    
	}
	
	
	/**
	 * @return return a URL that redirects to "/main/main"
	 * @param model
	 * @param a request parameter of "changeDate" named "changeDate" datatype of String
	 * 
	 */
	@PostMapping(value = "/main/date")
	public String postMain(Model model, @RequestParam(value = "changeDate", required = false) String changeDate) {
	
		    // Update currentDate based on the specified changeDate parameter
		    if ("previous".equals(changeDate)) {
		    	// decrements the current date
		        currentDate = currentDate.minusDays(1);
		    // Update currentDate based on the specified changeDate parameter
		    } else if ("next".equals(changeDate)) {
		    	// increment the current date
		        currentDate = currentDate.plusDays(1);
		    }
		    
	        // Update checkbox states based on the new date
		    reservationService.updateCheckboxStates(currentDate);		 
		 
	     // Generate a map indicating reservation ownership status using availReservation and user.
		    ReservationInOutDto updatedMap = reservationService.disamblingKey(currentDate);
		    
		    
		    // Add the "updatedMap" attribute to the model with the key "reservationOwnershipMap".
		    model.addAttribute("reservationOwnershipMap", updatedMap.disamblingKeyObj.getKey());
	    
	 // Redirect to the "/main/main" endpoint.
        return "redirect:/main/main";
	}


}




