package Logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.UserDao;
import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.UserLogic;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl.UserLogicImpl;


/**
 * This class test the User Logic Implementation
 * @author Alier Torrenueva
 * 01/31/2024
 */

//These annotations enable Mockito and Spring support in JUnit 5.
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserLogicImplTest {
	
	// Mock the UserDao interface
	@Mock
	private UserDao userDao;

	// Inject mocks into the UserLogicImpl instance
	@InjectMocks
	private UserLogic userLogic = new UserLogicImpl();

	// Test method to retrieve a user by ID
	@Test
	public void testGetLoginUser() {
	    // Define a test user ID
	    String testUserId = "testUser";

	    // Create a test UserEntity object
	    UserEntity testUser = new UserEntity();
	    testUser.setId(testUserId);
	    testUser.setName("Test User");
	    testUser.setPassword("testPassword");

	    // Define the behavior of the userDao mock
	    when(userDao.findLoginUser(testUserId)).thenReturn(testUser);

	    // Call the method under test
	    UserEntity result = userLogic.getLoginUser(testUserId);

	    // Assert that the returned user matches the expected user
	    assertEquals(testUser, result);
	}



}
