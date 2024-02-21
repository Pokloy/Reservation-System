package Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.cyzennt.rs.rs_spring_alier.common.util.SecuritySession;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.ReservationDao;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.ReservationEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.UserLogic;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl.ReservationLogicImpl;

/**
 * This class test the Reservation Logic Implementation
 * @author Alier Torrenueva
 * 01/31/2024
 */

//These annotations enable Mockito and Spring support in JUnit 5.
@ExtendWith(MockitoExtension.class)
public class ReservationLogicImplTest {
	// Mock the SecuritySession, UserLogic, and ReservationDao interfaces
	@Mock
	private SecuritySession mockSecSession;

	@Mock
	private UserLogic userService;

	@Mock
	private ReservationDao mockReservationDao;

	// Inject mocks into the ReservationLogicImpl instance
	@InjectMocks
	private ReservationLogicImpl reservationLogicImpl;

	// Test method to verify the availability of reservations
	@Test
	public void testFindAvailReserved() {
	    // Create mock reservation entities
	    List<ReservationEntity> findAvailReservedList = new ArrayList<>();
	    findAvailReservedList.add(new ReservationEntity());

	    // Define the behavior of the reservationDao mock
	    when(mockReservationDao.findAvailReserved()).thenReturn(findAvailReservedList);

	    // Call the method under test
	    List<ReservationEntity> result = reservationLogicImpl.findAvailReserved();

	    // Assert that the result is not null and has the expected size
	    assertNotNull(result);
	    assertEquals(findAvailReservedList.size(), result.size());
	}

	
	// Test method to check if a reservation exists
	@Test
	public void testReservationExists1() {
	    // Define the current date, room ID, and time ID
	    LocalDate currentDate = LocalDate.now();
	    int roomId = 1;
	    int timeId = 1;
	    char isDeleted = '1';
	    
	    // Define the behavior of the mock reservationDao
	    when(mockReservationDao.reservationExists(currentDate, roomId, timeId)).thenReturn(isDeleted);
	    
	    // Call the method under test
	    boolean result = reservationLogicImpl.reservationExists(currentDate, roomId, timeId);
	    
	    // Assert that a reservation exists for the specified parameters
	    assertTrue(result);
	}

	
	// Test method to check if a reservation does not exist
	@Test
	public void testReservationExists2() {
	    // Define the current date, room ID, and time ID
	    LocalDate currentDate = LocalDate.now();
	    int roomId = 1;
	    int timeId = 1;
	    char isDeleted = '2';
	    
	    // Define the behavior of the mock reservationDao
	    when(mockReservationDao.reservationExists(currentDate, roomId, timeId)).thenReturn(isDeleted);
	    
	    // Call the method under test
	    boolean result = reservationLogicImpl.reservationExists(currentDate, roomId, timeId);
	    
	    // Assert that no reservation exists for the specified parameters
	    assertFalse(result);
	}

	
	// Test method to check if a reservation with null deletion status does not exist
	@Test
	public void testReservationExists3() {
	    // Define the current date, room ID, and time ID
	    LocalDate currentDate = LocalDate.now();
	    int roomId = 1;
	    int timeId = 1;
	    Character isDeleted = null;
	    
	    // Define the behavior of the mock reservationDao
	    when(mockReservationDao.reservationExists(currentDate, roomId, timeId)).thenReturn(isDeleted);
	    
	    // Call the method under test
	    boolean result = reservationLogicImpl.reservationExists(currentDate, roomId, timeId);
	    
	    // Assert that no reservation exists for the specified parameters
	    assertFalse(result);
	}

	
	// Test method to insert a reservation
	@Test
	public void testInsertReservation() {
	    // Define the current date, room ID, and time ID
	    LocalDate currentDate = LocalDate.now();
	    int roomId = 1;
	    int timeId = 1;
	    
	    // Mock user entity for the logged-in user
	    UserEntity mockUserEntity = new UserEntity();
	    mockUserEntity.setId("0001");
	    mockUserEntity.setName("User01");
	    mockUserEntity.setPassword("User01");
	    
	    // Define the behavior of the userService to return the mockUserEntity
	    when(userService.getLoginUser(mockSecSession.getUsername())).thenReturn(mockUserEntity);
	    
	    // Create a list of reservation entities
	    List<ReservationEntity> mReservedList = new ArrayList<>();
	    ReservationEntity mReserved = new ReservationEntity();
	    mReserved.setRes_date(currentDate);
	    mReserved.setRoom_id(roomId);
	    mReserved.setTime_id(timeId);
	    
	    // Define the behavior of the mockReservationDao to add a reservation and return the reservation entity
	    when(mockReservationDao.addReservation(mockUserEntity.getId(), currentDate, roomId, timeId)).thenReturn(mReserved);
	    
	    // Add the reservation entity to the list
	    mReservedList.add(mReserved);
	    
	    // Call the method under test to insert the reservation
	    reservationLogicImpl.insertReservation(mReservedList);
	}

	
	// Test method to delete a reservation by ID
	@Test
	public void testDeleteReservation() {
	    // Define the ID of the reservation to delete
	    int id = 1;
	    
	    // Call the method under test to delete the reservation
	    reservationLogicImpl.deleteReservation(id);
	}

	
	
	// Test method to find user-specific reservations
	@Test
	public void testFindUserSpecificReserve() {
	    // Define the current date, room ID, and time ID
	    LocalDate currentDate = LocalDate.now();
	    int roomId = 1;
	    int timeId = 1;
	    
	    // Create a list of reservation entities
	    List<ReservationEntity> mReservedList = new ArrayList<>();
	    ReservationEntity mReserved = new ReservationEntity();
	    mReserved.setRes_date(currentDate);
	    mReserved.setRoom_id(roomId);
	    mReserved.setTime_id(timeId);
	    
	    // Add the reservation entity to the list
	    mReservedList.add(mReserved);
	    
	    // Define the behavior of the mockReservationDao to return the list of reservations
	    when(mockReservationDao.findUserSpecificReserve(currentDate, roomId, timeId)).thenReturn(mReservedList);
	    
	    // Call the method under test to find user-specific reservations
	    List<ReservationEntity> result = reservationLogicImpl.findUserSpecificReserve(currentDate, roomId, timeId);
	    
	    // Assert that the result is not null
	    assertNotNull(result);
	}

	

}
