package controller;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.cyzennt.rs.rs_spring_alier.controller.MainController;
import jp.co.cyzennt.rs.rs_spring_alier.controller.dto.ReservationFormWebDto;
import jp.co.cyzennt.rs.rs_spring_alier.model.dto.ReservationInOutDto;
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
 * This class test the MainController
 * @author Alier Torrenueva
 * 01/28/2024
 */

//These annotations enable Mockito and Spring support in JUnit 5.
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class MainControllerTest {
	//  Resource management field.
	private AutoCloseable closeable;
	
	//  Injects dependencies into MainController.
	@InjectMocks
    private MainController mainController;

	// Creates deep stub of ReservationService.
	private ReservationService mockReservationServiceMock = mock(ReservationService.class,Mockito.RETURNS_DEEP_STUBS);
 
	// Creates deep stub of ReservationFormWebDto.
	private ReservationFormWebDto mockReservationFormWebDto = mock(ReservationFormWebDto.class,Mockito.RETURNS_DEEP_STUBS);
	
	// Creates deep stub of Model.
	private Model model = mock(Model.class,Mockito.RETURNS_DEEP_STUBS);
	
	// Creates mock ModelMapper.
	private ModelMapper modelMapper = mock(ModelMapper.class);

	// Initializes Mockito mocks.
	@BeforeEach
	public void openMocks() {
			//  Opens mocks.
	        closeable = MockitoAnnotations.openMocks(this);
	}
	
	//  Closes Mockito mocks.
    @AfterEach
    public void releaseMocks() throws Exception {
        // Releases Mockito mocks.
    	closeable.close();
    }
   
    // This method is used to test on GetMain method of the Main Controller.
    @Test
    public void testGetMain() throws Exception {
    	// Retrieves the current date.
        LocalDate currentDate = LocalDate.now();
        
        // Mocks the time list.
        ReservationInOutDto timeList = mockReservationServiceMock.populateTime();
        
        // Mocks the room list.
        ReservationInOutDto roomList = mockReservationServiceMock.populateRoom();
        
        //  Creates a new instance and updates all data.
        ReservationInOutDto allDataAndCheckStatus = mockReservationServiceMock.updateCheckboxStates(currentDate); // Create a new instance
        
        // Initializes an updated map.
        ReservationInOutDto updatedMap = new ReservationInOutDto();
        
        // Sets a sample user ID.
        String sampleUser = "0001";
        
        // Sets time objects.
        allDataAndCheckStatus.setTimeObj(timeList.getTimeObj());
        
        // Sets room objects.
        allDataAndCheckStatus.setRoomObj(roomList.getRoomObj());
        
        // Mocks user login.
        ReservationInOutDto user = mockReservationServiceMock.loginUser();
        
        // Sets user object.
        UserObj userObj = new UserObj();
        
        // Sets sampleUser to userObj
        userObj.setName(sampleUser);
        
        // Sets userObj to userObj
        user.setUserObj(userObj);
        
        // Sets the disambling key object in the updated map.
        updatedMap.setDisamblingKeyObj(new DisamblingKeyObj());

        // Mocks the behavior of updateCheckboxStates method.
        when(mockReservationServiceMock.updateCheckboxStates(currentDate)).thenReturn(allDataAndCheckStatus);
        
        // Mocks the behavior of populateTime method.
        when(mockReservationServiceMock.populateTime()).thenReturn(timeList);
        
        // Mocks the behavior of populateRoom method.
        when(mockReservationServiceMock.populateRoom()).thenReturn(roomList);
        
        // Mocks the behavior of loginUser method.
        when(mockReservationServiceMock.loginUser()).thenReturn(user);
        
        // Mocks the behavior of disamblingKey method with any parameter.
        when(mockReservationServiceMock.disamblingKey(any())).thenReturn(updatedMap);

        // Invokes the getMain method of mainController with provided parameters.
        mainController.getMain(model, mockReservationFormWebDto);   
    }
    
    
    // This method is for unit test of Insert Reserve for Scenario 1 
    @Test
    public void testInsertReserve1() throws Exception {
    	// Retrieves the current date.
        LocalDate currentDate = LocalDate.now(); 
        
    	// Sets the current date as a string.
        String currentDateStr = "2024-02-01"; 
        
        // Initializes an ArrayList for selected hours.
        ArrayList<String> selectedHours = new ArrayList<>(); 
        
        // Adds selected hours to the list.
        selectedHours.add("1-1-2/1/24"); 

        // Creates a mock ReservationInOutDto object.
        ReservationInOutDto reservationCheckerIO = new ReservationInOutDto(); 
        
        // Sets the reservation checker to true.
        ReservationExistCheckerObj reservationCheckerList = new ReservationExistCheckerObj();
        
        // set the ReservChecker as false 
        reservationCheckerList.setReservChecker(false);
        
        // sets the reservationCheckerList to reservationCheckerIO
        reservationCheckerIO.setReservationExistChecker(reservationCheckerList);
        
        // Mocks the behavior of findExistReservation method.
        when(mockReservationServiceMock.findExistReservation(currentDate, 1, 1)).thenReturn(reservationCheckerIO); 
        
        // Mocks the behavior of modelMapper.map method.
        when(modelMapper.map(mockReservationFormWebDto, ReservationObj.class)).thenReturn(new ReservationObj()); 

        // Calls the method to be tested.
        String result = mainController.insertReserve(model, currentDateStr, selectedHours, mockReservationFormWebDto); 

        // Verifies reservation checker is false.
        assertFalse(reservationCheckerIO.getReservationExistChecker().isReservChecker()); 
        
        // Verifies redirection URL.
        assertEquals("redirect:/main/main", result); 
        
        // Verifies loginUser is called once.
        verify(mockReservationServiceMock, times(1)).loginUser(); 
        
        // Verifies insertReservationService is called once.
        verify(mockReservationServiceMock, times(1)).insertReservationService(any(ReservationObj.class)); 
    }

    



    // This method is for unit test of Insert Reserve for Scenario 2 
    @Test
    public void testInsertReserve2() throws Exception {
    	// Defines a string representing the current date.
        String currentDateStr = "2024-02-01";
        
        // Initializes an empty ArrayList for selected hours.
        ArrayList<String> selectedHours = new ArrayList<>();
        
        // Mocks the mapping behavior.
        when(modelMapper.map(mockReservationFormWebDto, ReservationObj.class)).thenReturn(new ReservationObj());
        
        // Asserts that the selected hours ArrayList is not null.
        assertNotNull(selectedHours);
        
        // Asserts that the selected hours ArrayList is empty.
        assertEquals(0,selectedHours.size());
        
        // Invokes the insertReserve method of mainController with the provided parameters.
        mainController.insertReserve(model, currentDateStr, selectedHours, mockReservationFormWebDto);
    }
    
    
    // This method is for unit test of Delete Reserve 
    @Test
    public void testDeleteReserve1() throws Exception {
    	// Initialize variables
    	// Set user to null
    	ReservationInOutDto user = null;
    	
    	// Set currentDateStr to a specific date
        String currentDateStr = "2024-02-01";
        
        // Initialize an empty list for selected hours
        ArrayList<String> selectedHours = new ArrayList<>();
        
        // Initialize an empty list for unchecked checkbox IDs
        ArrayList<String> uncheckedCheckboxIds = new ArrayList<>();
        
        // Add an example unchecked checkbox ID
        uncheckedCheckboxIds.add("1-1");
        
        // Mock objects and behavior
        // Mock ReservationInOutDto object
        ReservationInOutDto reservationObject = mock(ReservationInOutDto.class);
        
        // Mock ReservationDeleteObj object
        ReservationDeleteObj mockDeleteObj = mock(ReservationDeleteObj.class);
        
        // Create a list for specific reservation objects
        List<ReservationDeleteObj> specificReservationList = new ArrayList<>();
        
        // Add a mock reservation object to the list
        specificReservationList.add(mockDeleteObj);
        
        // Mock behavior for method calls
        // Mock findSpecificReservationList method to return reservationObject
        when(mockReservationServiceMock.findSpecificReservationList(any(LocalDate.class), anyInt(), anyInt()))
                .thenReturn(reservationObject);
        
        // Mock getDeleteListReservation method to return specificReservationList
        when(reservationObject.getDeleteListReservation()).thenReturn(specificReservationList);
        
        // Mock loginUser method to return user
        when(mockReservationServiceMock.loginUser()).thenReturn(user);
        
        // Assert
        // Verify that user is null
        assertNull(user);
        
        // Call method under test
        mainController.deleteReserve(model, currentDateStr, selectedHours, uncheckedCheckboxIds, mockReservationFormWebDto);
    }
    

    
    // This method is for unit test of Date Change Scenerio 2
    @Test
    public void testPreviousDateScenario2() {
    	// Define the changeDate variable as "previous"
        String changeDate = "previous";
        
        // Retrieve the current date
        LocalDate currentDate = LocalDate.now();
        
        // Create a mock HashMap and populate it with a single key-value pair
        HashMap<String, Boolean> mockHashMap = new HashMap<>();
        mockHashMap.put("key", true);
        
        // Create a DisamblingKeyObj and set its key attribute with the mock HashMap
        DisamblingKeyObj disamblingKeyObj = new DisamblingKeyObj();
        disamblingKeyObj.setKey(mockHashMap);
        
        // Create a ReservationInOutDto and set its DisamblingKeyObj with the created DisamblingKeyObj
        ReservationInOutDto updatedMap = new ReservationInOutDto();
        updatedMap.setDisamblingKeyObj(disamblingKeyObj);
        
        // Mock the behavior of the disamblingKey method to return the updated map
        when(mockReservationServiceMock.disamblingKey(any(LocalDate.class))).thenReturn(updatedMap);
        
        // Call the postMain method of the mainController with the provided model and changeDate
        mainController.postMain(model, changeDate);
        
        // Verify that the updateCheckboxStates method of mockReservationServiceMock is called once with the currentDate minus one day
        verify(mockReservationServiceMock, times(1)).updateCheckboxStates(currentDate.minusDays(1));
    }

    // This method is for unit test of Date Change Scenerio 1
    @Test
    public void testPreviousDateScenario1() {
    	// Define the changeDate variable as "next"
        String changeDate = "next";
        
        // Retrieve the current date
        LocalDate currentDate = LocalDate.now();
        
        // Create a mock HashMap and populate it with a single key-value pair
        HashMap<String, Boolean> mockHashMap = new HashMap<>();
        mockHashMap.put("1-1", true);
        
        // Create a DisamblingKeyObj and set its key attribute with the mock HashMap
        DisamblingKeyObj disamblingKeyObj = new DisamblingKeyObj();
        disamblingKeyObj.setKey(mockHashMap);
        
        // Create a ReservationInOutDto and set its DisamblingKeyObj with the created DisamblingKeyObj
        ReservationInOutDto updatedMap = new ReservationInOutDto();
        updatedMap.setDisamblingKeyObj(disamblingKeyObj);

        // Mock the behavior of the disamblingKey method to return the updated map
        when(mockReservationServiceMock.disamblingKey(any(LocalDate.class))).thenReturn(updatedMap);
        
        // Call the postMain method of the mainController with the provided model and changeDate
        mainController.postMain(model, changeDate);
        
        // Verify that the updateCheckboxStates method of mockReservationServiceMock is called once with the currentDate plus one day
        verify(mockReservationServiceMock, times(1)).updateCheckboxStates(currentDate.plusDays(1));
    }
   
    
    // This method is for unit test of Date Change Scenerio 3
    @Test
    public void testPreviousDateScenario3() {
    	// Define the changeDate variable as null
        String changeDate = null;
        
        // Retrieve the current date
        LocalDate currentDate = LocalDate.now();
        
        // Create a mock HashMap and populate it with a single key-value pair
        HashMap<String, Boolean> mockHashMap = new HashMap<>();
        mockHashMap.put("1-1", true);
        
        // Create a DisamblingKeyObj and set its key attribute with the mock HashMap
        DisamblingKeyObj disamblingKeyObj = new DisamblingKeyObj();
        disamblingKeyObj.setKey(mockHashMap);
        
        // Create a ReservationInOutDto and set its DisamblingKeyObj with the created DisamblingKeyObj
        ReservationInOutDto updatedMap = new ReservationInOutDto();
        updatedMap.setDisamblingKeyObj(disamblingKeyObj);
        
    	// Mock the behavior of the disamblingKey method to return the updated map
        when(mockReservationServiceMock.disamblingKey(any(LocalDate.class))).thenReturn(updatedMap);
        
        // Call the postMain method of the mainController with the provided model and changeDate
        mainController.postMain(model, changeDate);
        
        // Verify that the updateCheckboxStates method of mockReservationServiceMock is called once with the currentDate
        verify(mockReservationServiceMock, times(1)).updateCheckboxStates(currentDate);
    }
  
}
