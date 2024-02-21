package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
import jp.co.cyzennt.rs.rs_spring_alier.model.object.UserObj;
import jp.co.cyzennt.rs.rs_spring_alier.model.service.ReservationService;
import jp.co.cyzennt.rs.rs_spring_alier.model.service.impl.ReservationServiceImpl;


/**
 * This class test the Reservation Service Implementation
 * @author Alier Torrenueva
 * 01/30/2024
 */

//These annotations enable Mockito and Spring support in JUnit 5.
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ReservationServiceImplTest {
	
	private AutoCloseable closeable;
	
	@InjectMocks
	private ReservationServiceImpl  mockReservationServiceImpl;
	
	// Mocks time logic
    @Mock
    private TimeLogic timeLogic;
    
    // Mocks room logic
    @Mock
    private RoomLogic roomLogic;
    
    // Mocks reservation Logic
    @Mock
    private ReservationLogic reservationLogic;
    
    // Mocks user logic
	@Mock
	private UserLogic userService;
	
	// Mocks security session
	@Mock
	private SecuritySession secSession;
    

	// Before each test, open and initialize the Mockito mocks for this test class
	@BeforeEach
	public void openMocks() {
	        closeable = MockitoAnnotations.openMocks(this);
	}
	
	// After each test, release and close the Mockito mocks to avoid resource leaks
    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }
	
    // This method test the populateTime method for scenario 1
    @Test
    public void testPopulateTime1() {
    	
    	// Create a list of mock time entities
        List<TimeEntity> mockAvailTime = new ArrayList<>();
        
        // Create and add the first mock time entity
        TimeEntity timeEntity1 = new TimeEntity();
        
        // sets timeEntity1's id to 1
        timeEntity1.setId(1);
        
        // sets timeEntity1's name to "10:00 AM"
        timeEntity1.setName("10:00 AM");
        
        // add's timeEntity1 to mockAvailTime
        mockAvailTime.add(timeEntity1);
        
        // Create and add the second mock time entity
        TimeEntity timeEntity2 = new TimeEntity();
        
        // sets timeEntity2's id to 2
        timeEntity2.setId(2);
        
        // sets timeEntity2's name to "11:00 AM"
        timeEntity2.setName("11:00 AM");
        
        // add's timeEntity2 to mockAvailTime
        mockAvailTime.add(timeEntity2);

        // Set up the behavior of the timeLogic mock to return the mock list of available times
        when(timeLogic.availTime()).thenReturn(mockAvailTime);
        
        // Call the method to be tested        
        ReservationInOutDto result = mockReservationServiceImpl.populateTime();
        
        // Assertions to verify the result
        assertEquals(2, result.getTimeObj().size());
        
        // Assertion to verify the timeEntity1
        assertEquals(1, result.getTimeObj().get(0).getId());
        assertEquals("10:00 AM", result.getTimeObj().get(0).getName());
        
        // Assertion to verify the timeEntity2
        assertEquals(2, result.getTimeObj().get(1).getId());
        assertEquals("11:00 AM", result.getTimeObj().get(1).getName());
    }
    
    // This method test the populateTime method for scenario 2
    @Test
    public void testPopulateTime2() {
    	// Set up mock response to return null for available time
        List<TimeEntity> mockAvailTime = null;
        when(timeLogic.availTime()).thenReturn(mockAvailTime);
        
        // Call the method under test
        ReservationInOutDto result = mockReservationServiceImpl.populateTime();
        
        // Assertion to verify that the result is not null
        assertNotNull(result);
        
        // Assertion to verify that the time object in the result is null
        assertNull(result.getTimeObj());
    }
    
    // This method test the populateTime method for scenario 3
    @Test
    public void testPopulateTime3() {
    	// Set up mock response to return an empty list for available time
        List<TimeEntity> mockAvailTime = new ArrayList<>();
        when(timeLogic.availTime()).thenReturn(mockAvailTime);
        
        // Call the method under test
        ReservationInOutDto result = mockReservationServiceImpl.populateTime();
        
        // Assertion to verify that the result is not null
        assertNotNull(result);
        
        // Assertion to verify that the size of the mock available time list is 0
        assertEquals(0,mockAvailTime.size());
    }
    
    // This method test the populateRoom method for scenario 1
    @Test
    public void testPopulateRoom1() {
    	// Create a mock list of available rooms
        List<RoomEntity> mockAvailRoom = new ArrayList<>();
        
        // Declares a new roomEntity named roomEntity1
        RoomEntity roomEntity1 = new RoomEntity();
        
        // Sets the roomEntity1's id to 1
        roomEntity1.setId(1);
        
        // Sets the roomEntity1's name to "10:00 AM"
        roomEntity1.setName("10:00 AM");
        
        // Add's roomEntity1 to mockAvailRoom
        mockAvailRoom.add(roomEntity1);
        
        // Declares a new roomEntity named roomEntity2
        RoomEntity roomEntity2 = new RoomEntity();
        
        // Sets the roomEntity2's id to 2
        roomEntity2.setId(2);
        
        // Sets the roomEntity2's name to "11:00 AM"
        roomEntity2.setName("11:00 AM");
        
        // Add's roomEntity2 to mockAvailRoom
        mockAvailRoom.add(roomEntity2);
        
        // Set up mock response to return the mock list of available rooms
        when(roomLogic.availRoom()).thenReturn(mockAvailRoom);
        
        // Call the method under test
        ReservationInOutDto result = mockReservationServiceImpl.populateRoom();
        
        // Assertion to verify the size and contents of the result
        assertEquals(2, result.getRoomObj().size());
        
        // asserts Equal the roomEntity1
        assertEquals(1, result.getRoomObj().get(0).getId());
        assertEquals("10:00 AM", result.getRoomObj().get(0).getName());
        
        // asserts Equal the roomEntity2
        assertEquals(2, result.getRoomObj().get(1).getId());
        assertEquals("11:00 AM", result.getRoomObj().get(1).getName());
    }
    
    // This method test the populateRoom method for scenario 2
    @Test
    public void testPopulateRoom2() {
    	// Set up mock response to return a null list of available rooms
        List<RoomEntity> mockAvailRoom = null;
        
        // mocks the roomLogic.availRoom() to return the mockAvailRoom
        when(roomLogic.availRoom()).thenReturn(mockAvailRoom);
        
        // Call the method under test
        ReservationInOutDto result = mockReservationServiceImpl.populateRoom();
        
        // Assertions to verify that the result is not null and the room object is null
        assertNotNull(result);
        assertNull(result.getRoomObj());
    }
    
    // This method unit test the populateRoom method for scenario 3
    @Test
    public void testPopulateRoom3() {
    	// Initialize mock list for available rooms.
        List<RoomEntity> mockAvailRoom = new ArrayList<>();
        
        // Set behavior for room availability method.
        when(roomLogic.availRoom()).thenReturn(mockAvailRoom);
        
        // Populate room data in reservation object.
        ReservationInOutDto result = mockReservationServiceImpl.populateRoom();
        
        // Ensure reservation object is not null.
        assertNotNull(result);
        
        // Assert that mock room list is empty.
        assertEquals(0,mockAvailRoom.size());
    }
  
    // This method unit test the calculateShouldCheckList
    @Test
    public void testCalculateShouldCheckList() {
        // Mocking dependencies
    	// Initialize mock list for available rooms.
        List<RoomEntity> mockAvailRooms = new ArrayList<>();
        
        // Initialize mock list for available times.
        List<TimeEntity> mockAvailTime = new ArrayList<>();
        
        // Initialize mock list for available reservations.
        List<ReservationEntity> mockAvailReservation = new ArrayList<>();
        
        // Retrieve the current date.
        LocalDate currentDate = LocalDate.now();

        // Adding some mock data to the reservation list
        ReservationEntity reservation1 = new ReservationEntity();
        
        // Set room ID to 1.
        reservation1.setRoom_id(1);
        
        // Set time ID to 1.
        reservation1.setTime_id(1);
        
        // Set reservation date to current date.
        reservation1.setRes_date(currentDate);
        
    	// Add reservation to mock list.
        mockAvailReservation.add(reservation1);

        // Mocking the behavior of checkedCombinations
        Set<String> mockCheckedCombinations = new HashSet<>();
        
        // add's the currentDate and "1-1" to HashSet "mockCheckedCombinations"
        mockCheckedCombinations.add("1-1-" + currentDate);
       
        // Create a room entity with ID 0.
        RoomEntity roomEntity = new RoomEntity();	        
        roomEntity.setId(0);
        
        // Set the name as "sample room".
        roomEntity.setName("sample room");
        
        // Add the room entity to the mock list of available rooms.
        mockAvailRooms.add(roomEntity);
        
        // Create a time entity with ID 0.
        TimeEntity timeEntity = new TimeEntity();
        timeEntity.setId(0);
        
	    // Set the name as "sample time".
        timeEntity.setName("sample time");
        
        // Add the time entity to the mock list of available time.
        mockAvailTime.add(timeEntity);

        // calls the method "calculateShouldCheckList" from the "ReservationServiceImpl" package
        mockReservationServiceImpl.calculateShouldCheckList(mockAvailRooms, mockAvailTime, mockAvailReservation, currentDate);

        // asserts the mockAvailRoom's size to 1
        assertEquals(1, mockAvailRooms.size());
        
        // asserts the mockAvailTime's size to 1
        assertEquals(1, mockAvailTime.size());
    }
    
    
    // This method unit test of Update Checkbox method
    @Test
    public void testUpdateCheckbox() {
    	 // Retrieve the current date.
        LocalDate currentDate = LocalDate.now();


        // Initialize a list for available rooms.
        List<RoomEntity> mockAvailRooms = new ArrayList<>();
        RoomEntity roomEntity = new RoomEntity();
        
        // Add a room entity to the list.
        mockAvailRooms.add(roomEntity);
        
        // Stub the room logic to return the mockAvailRooms
        when(roomLogic.availRoom()).thenReturn(mockAvailRooms);

        // Initialize a list for available times.
        List<TimeEntity> mockAvailTime = new ArrayList<>();
        TimeEntity timeEntity = new TimeEntity();
        
        // Add a time entity to the list.
        mockAvailTime.add(timeEntity);
        
        // Stub the time logic to return the mockAvailTime.
        when(timeLogic.availTime()).thenReturn(mockAvailTime);
        
        // Initialize a list for available reservations.
        List<ReservationEntity> mockAvailReservation = new ArrayList<>();
        ReservationEntity reservation1 = new ReservationEntity();
        
        // Add a reservation entity to the list.
        mockAvailReservation.add(reservation1);
        
        // Stub the reservation logic to return the mockAvailReservation.
        when(reservationLogic.findAvailReserved()).thenReturn(mockAvailReservation);

        // calls the method "updateCheckboxStates" with parameters "currentDate" from ReservationServiceImpl
        mockReservationServiceImpl.updateCheckboxStates(currentDate);

    }


    // This method is for method GenerateReservationOwnershipMap scenario 1
    @Test
    public void testGenerateReservationOwnershipMap1() {
        // Mocking dependencies
        List<ReservationEntity> availReservation = new ArrayList<>();
        
        // Create a new user entity.
        UserEntity user = new UserEntity();
        
        // Set the user's ID.
        user.setId("0003");
        
        // Retrieve the current date.
        LocalDate currentDate = LocalDate.now();
        
        // Create a new reservation entity named "reservation1".
        ReservationEntity reservation1 = new ReservationEntity();
        
        // Set the room ID.
        reservation1.setRoom_id(1);
        
        // Set the time ID.
        reservation1.setTime_id(2);
        
        // Set the user ID.
        reservation1.setUser_id("0001");
        
        // Set the reservation date.
        reservation1.setRes_date(currentDate);
        
        // Create a new reservation entity named "reservation2".
        ReservationEntity reservation2 = new ReservationEntity();
        
        // Set the room ID.
        reservation2.setRoom_id(1);
        
        // Set the time ID.
        reservation2.setTime_id(1);
        
        // Set the user ID.
        reservation2.setUser_id("0003");
        
        // Set the reservation date.
        reservation2.setRes_date(currentDate);
        
        // Create a new reservation entity named "reservation3".
        ReservationEntity reservation3 = new ReservationEntity();
        
        // Set the room ID.
        reservation3.setRoom_id(1);
        
        // Set the time ID.
        reservation3.setTime_id(1);
        
        // Set the user ID.
        reservation3.setUser_id("0001");
        
        // Set the reservation date.
        reservation3.setRes_date(currentDate);
        
        // Create a new reservation entity named "reservation4".
        ReservationEntity reservation4 = new ReservationEntity();
        
        // Set the room ID.
        reservation4.setRoom_id(1);
        
        // Set the time ID.
        reservation4.setTime_id(2);
        
        // Set the user ID.
        reservation4.setUser_id("0001");
        
        // Set the reservation date.
        reservation4.setRes_date(currentDate);

        // adds the reservations entities to "availReservation" List
        availReservation.add(reservation1);
        availReservation.add(reservation2);
        availReservation.add(reservation3);
        availReservation.add(reservation4);

        
        // Invoking the method under test and store it to the result hashmap.
        Map<String, Boolean> result = mockReservationServiceImpl.generateReservationOwnershipMap(availReservation, user, currentDate);
        
        // Asserts the result hashmap size to 2.
        assertEquals(2, result.size()); 
        
        // Assert the result that containskey to true.
        assertTrue(result.containsKey("1-2"));
    }

    // This method is for method GenerateReservationOwnershipMap scenario 2
    @Test
    public void testGenerateReservationOwnershipMap2() {
        // Mocking dependencies
        List<ReservationEntity> availReservation = new ArrayList<>();
        
        // Create a new user entity.
        UserEntity user = new UserEntity();
        
        // Set the user's ID.
        user.setId("0003");
        
        // Retrieve the current date.
        LocalDate currentDate = LocalDate.now();
        
        // Adds plus 1 to the current date
        LocalDate futureDate = currentDate.plusYears(1);
        
        // Create a new reservation entity named "reservation1".
        ReservationEntity reservation1 = new ReservationEntity();
        
        // Set the room ID.
        reservation1.setRoom_id(1);
        
        // Set the time ID.
        reservation1.setTime_id(1);
        
        // Set the user ID.
        reservation1.setUser_id("0003");
        
        // Set the reservation date to the future date.
        reservation1.setRes_date(futureDate);
        
        // Create a new reservation entity named "reservation2".
        ReservationEntity reservation2 = new ReservationEntity();
        
        // Set the user ID.
        reservation2.setUser_id("0001");
        
        // Set the reservation date to the future date.
        reservation2.setRes_date(futureDate);
        
        // adds the reservations entities to "availReservation" List
        availReservation.add(reservation1);
        availReservation.add(reservation2);

        // Invoking the method under test and store it to the result hashmap.
        Map<String, Boolean> result = mockReservationServiceImpl.generateReservationOwnershipMap(availReservation, user, currentDate);
        
        // Asserts the result hashmap size to 2.
        assertEquals(2, result.size()); 
        
        // Assert the result that containskey to false.
        assertFalse(result.containsKey("2-2"));
    }

    // This method is for method findSpecificReservationList for scenario 1
    @Test
    public void testFindSpecificReservationList1() {
    	// Retrieve the current date.
        LocalDate currentDate = LocalDate.now();
        
        // declares int named "roomId" to 1
        int roomId = 1;
        
        // declares int named "timeId" to 1
        int timeId = 1;
        
        // Create a list to store available reservations.
        List<ReservationEntity> reservationsAvailable = new ArrayList<>();
        
        // Create a reservation entity with an ID and user ID.
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setId(1);
        reservationEntity.setUser_id("0001");
        
        // Add the reservation entity to the list.
        reservationsAvailable.add(reservationEntity);
        
        // Mock the behavior of finding user-specific reservations.
        Mockito.when(reservationLogic.findUserSpecificReserve(currentDate, roomId, timeId))
               .thenReturn(reservationsAvailable);
        
        // Call the method to find specific reservations.
        ReservationInOutDto reservationIO = mockReservationServiceImpl.findSpecificReservationList(currentDate, roomId, timeId);
        
        // Ensure that the result is not null.
        assertNotNull(reservationIO);
        
        // Retrieve the list of delete reservations.
        List<ReservationDeleteObj> deleteListReservation = reservationIO.getDeleteListReservation();
        
        // Ensure that the delete list is not null.
        assertNotNull(deleteListReservation);
        
        // Assert that the delete list contains one reservation.
        assertEquals(1, deleteListReservation.size());
    }

    // This method is for method findSpecificReservationList for scenario 2
    @Test
    public void testFindSpecificReservationList2() {
    	// Retrieve the current date.
        LocalDate currentDate = LocalDate.now();
        
        // declares int named "roomId" to 1
        int roomId = 1;
        
        // declares int named "timeId" to 1
        int timeId = 1;
        
        
        // Mock the behavior of finding user-specific reservations to empty.
        Mockito.when(reservationLogic.findUserSpecificReserve(currentDate, roomId, timeId))
               .thenReturn(Collections.emptyList());

        // Call the method to find specific reservations.
        ReservationInOutDto reservationIO = mockReservationServiceImpl.findSpecificReservationList(currentDate, roomId, timeId);

        // Ensure that the result is not null.
        assertNotNull(reservationIO);
        
        // Retrieve the list of delete reservations.
        List<ReservationDeleteObj> deleteListReservation = reservationIO.getDeleteListReservation();
        
        // Ensure that the delete list is not null.
        assertNull(deleteListReservation);
    }
    
    
    // This method is for findSpecificReservationList for scenario 3
    @Test
    public void testFindSpecificReservationList3() {
    	// Retrieve the current date.
        LocalDate currentDate = LocalDate.now();
        
        // declares int named "roomId" to 1
        int roomId = 1;
        
        // declares int named "timeId" to 1
        int timeId = 1;
        
        // Mocking the behavior of findUserSpecificReserve method to return null
        Mockito.when(reservationLogic.findUserSpecificReserve(currentDate, roomId, timeId))
               .thenReturn(null);

        // Invoke the method under test
        ReservationInOutDto reservationIO = mockReservationServiceImpl.findSpecificReservationList(currentDate, roomId, timeId);

        // Ensure that the result is not null.
        assertNotNull(reservationIO);
        
        // Retrieve the list of delete reservations.
        List<ReservationDeleteObj> deleteListReservation = reservationIO.getDeleteListReservation();
        
        // Ensure that the delete list is not null.
        assertNull(deleteListReservation);
    }
    
    // This method is for DeleteReservation
    @Test
    public void testDeleteReservation() {
    	
    	// declares int named "id" to 1
    	int id = 1;
    	
    	// calls the method deleteReservation with the parameters "id"
        mockReservationServiceImpl.deleteReservation(id);
        
        // verify the method deleteReservation
    	verify(reservationLogic).deleteReservation(id);
    }

    // This method if for Retrieve Reservation Scenario 1
    @Test
    public void testRetrieveReservation1() {
    	// Retrieve available reservations from the reservation logic.
    	reservationLogic.findAvailReserved();

    	// Get the current date.
    	LocalDate currentDate = LocalDate.now();

    	// Create a reservation entity with specific details.
    	ReservationEntity reservation1 = new ReservationEntity();
    	reservation1.setId(1);
    	reservation1.setRes_date(currentDate);
    	reservation1.setRoom_id(1);
    	reservation1.setTime_id(1);
    	reservation1.setUser_id("0001");

    	// Create a list containing the reservation entity.
    	List<ReservationEntity> reservations = Collections.singletonList(reservation1);

    	// Mock the behavior to return the list of reservations.
    	when(reservationLogic.findAvailReserved()).thenReturn(reservations);

    	// Call the method to retrieve reservations from the reservation service.
    	ReservationInOutDto reservationIO = mockReservationServiceImpl.retrieveReservation();

    	// Ensure that the result is not null.
    	assertNotNull(reservationIO);

    	// Retrieve the list of reservation objects from the result.
    	List<ReservationObj> reservationLists = reservationIO.getReserveObj();

    	// Ensure that the list of reservation objects is not null.
    	assertNotNull(reservationLists);

    	// Assert that the list contains one reservation.
    	assertEquals(1, reservationLists.size());

    	// Assert specific details of the reservation object.
    	assertEquals(1, reservationLists.get(0).getId());
    	assertEquals(currentDate, reservationLists.get(0).getRes_date());
    	assertEquals(1, reservationLists.get(0).getRoom_id());
    	assertEquals(1, reservationLists.get(0).getTime_id());
    	assertEquals("0001", reservationLists.get(0).getUser_id());
    }

    // This method if for Retrieve Reservation Scenario 2
    @Test
    public void testRetrieveReservation2() {
    	// Retrieve reservations using the reservation service.
    	ReservationInOutDto reservationIO = mockReservationServiceImpl.retrieveReservation();

    	// Ensure that the retrieved reservation object is not null.
    	assertNotNull(reservationIO);

    	// Assert that there are no available reservations.
    	assertEquals(0, reservationLogic.findAvailReserved().size());

    }

    // This method if for Retrieve Reservation Scenario 3
    @Test
    public void testRetrieveReservation3() {
    	// Retrieve available reservations using the reservation service.
    	reservationLogic.findAvailReserved();

    	// Set reservations to null.
    	List<ReservationEntity> reservations = null;

    	// Mock the behavior to return null when finding available reservations.
    	when(reservationLogic.findAvailReserved()).thenReturn(reservations);

    	// Retrieve reservation information and ensure it's not null.
    	ReservationInOutDto reservationIO = mockReservationServiceImpl.retrieveReservation();
    	assertNotNull(reservationIO);

    	// Ensure that the reservation list is null.
    	List<ReservationObj> reservationLists = reservationIO.getReserveObj();
    	assertNull(reservationLists);

    }

    // This method is for FindExistReservation
    @Test
    public void testFindExistReservationNotNull() {
    	// Retrieve the current date.
        LocalDate currentDate = LocalDate.now();
        
        // declares int named "roomId" to 1
        int roomId = 1;
        
        // declares int named "timeId" to 1
        int timeId = 1;

        // Mock the behavior of reservationLogic
        when(reservationLogic.reservationExists(currentDate, roomId, timeId)).thenReturn(true);

        // Invoke the method under test
        ReservationInOutDto reservationIO = mockReservationServiceImpl.findExistReservation(currentDate, roomId, timeId);

        // Assert that the ReservationInOutDto object is null
        assertNotNull(reservationIO);
    }
    
    // This method is for Login User
    @Test
    public void testLoginUser() {

        // Mock the behavior of userService
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId("0001");
        mockUserEntity.setName("User01");
        mockUserEntity.setPassword("User01");
        when(userService.getLoginUser(secSession.getUsername())).thenReturn(mockUserEntity);

        // Invoke the method under test
        ReservationInOutDto userIO = mockReservationServiceImpl.loginUser();

        // Create a new UserObj object.
        UserObj expectedUserObj = new UserObj();
        expectedUserObj.setId("0001");
        expectedUserObj.setName("User01");
        expectedUserObj.setPassword("User01");

        // Create a new ReservationInOutDto object with the expected UserObj
        ReservationInOutDto expectedUserIO = new ReservationInOutDto();
        expectedUserIO.setUserObj(expectedUserObj);

        // Verify that the returned ReservationInOutDto matches the expected one
        assertEquals(expectedUserIO, userIO);
    }

    // This method is for DisamblingKey scenario 1
    @Test
    public void testDisamblingKey1() {
    	// Get the current date.
    	LocalDate currentDate = LocalDate.now();

    	// Create a mock user entity.
    	UserEntity mockUser = new UserEntity();
    	mockUser.setId("0001");
    	mockUser.setName("User01");
    	mockUser.setPassword("User01");

    	// Retrieve available reservations.
    	List<ReservationEntity> availReservation = reservationLogic.findAvailReserved();

    	// Create a mock list of available reservations.
    	List<ReservationEntity> mockAvailReservation = new ArrayList<>();

    	// Generate reservation ownership map.
    	Map<String, Boolean> mockUpdatedMap = mockReservationServiceImpl.generateReservationOwnershipMap(availReservation, mockUser, currentDate);

    	// Create a reservation entity and add it to the mock available reservation list.
    	ReservationEntity reservation = new ReservationEntity();
    	reservation.setId(0);
    	reservation.setRes_date(currentDate);
    	reservation.setRoom_id(1);
    	reservation.setTime_id(1);
    	reservation.setUser_id("0001");
    	mockAvailReservation.add(reservation);

    	// Mock the behavior to return the mock available reservation list.
    	when(reservationLogic.findAvailReserved()).thenReturn(mockAvailReservation);

    	// Mock the behavior to return the mock user when retrieving the login user.
    	when(userService.getLoginUser(secSession.getUsername())).thenReturn(mockUser);

    	// Create mock key reservation data transfer object.
    	ReservationInOutDto mockKeyIO = new ReservationInOutDto();

    	// Create mock disambling key object list and set the key map.
    	DisamblingKeyObj mockDisamblingKeyObjList = new DisamblingKeyObj();
    	mockDisamblingKeyObjList.setKey(new HashMap<>(mockUpdatedMap));

    	// Set the disambling key object list to the mock key reservation data transfer object.
    	mockKeyIO.setDisamblingKeyObj(mockDisamblingKeyObjList);

    	// Perform disambling key operation.
    	mockReservationServiceImpl.disamblingKey(currentDate);

    	// Ensure that the mock key reservation data transfer object is not null.
    	assertNotNull(mockKeyIO);

    }

    // This method is for InsertReservationService 
    @Test
    public void testInsertReservationService() {
    	// Create a reservation object.
    	ReservationObj reservationObj = new ReservationObj();

    	// Create a list to store insert reservations.
    	List<ReservationEntity> insertReservations = new ArrayList<>();

    	// Get the current date.
    	LocalDate currentDate = LocalDate.now();

    	// Create a reservation entity and add it to the insert reservations list.
    	ReservationEntity reservationEntity = new ReservationEntity();
    	reservationEntity.setRes_date(currentDate);
    	reservationEntity.setRoom_id(0);
    	reservationEntity.setTime_id(0);
    	insertReservations.add(reservationEntity);

    	// Call the method to insert the reservation service.
    	mockReservationServiceImpl.insertReservationService(reservationObj);

    	// Insert reservations into the reservation logic.
    	reservationLogic.insertReservation(insertReservations);

    }





}

