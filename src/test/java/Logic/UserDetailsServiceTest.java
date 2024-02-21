package Logic;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.co.cyzennt.rs.rs_spring_alier.model.dao.entity.UserEntity;
import jp.co.cyzennt.rs.rs_spring_alier.model.dto.ReservationInOutDto;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.UserLogic;
import jp.co.cyzennt.rs.rs_spring_alier.model.logic.impl.UserDetailsLogicImpl;
import jp.co.cyzennt.rs.rs_spring_alier.model.object.UserObj;

/**
 * This class test the User Details Service
 * @author Alier Torrenueva
 * 01/31/2024
 */

//These annotations enable Mockito and Spring support in JUnit 5.
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {


private AutoCloseable closeable;
	
	@InjectMocks
	private UserDetailsLogicImpl mockUserDetailsLogicImpl;
	
	@Mock
	private UserLogic mockUserLogic;
	
	@BeforeEach
	public void openMocks() {
	        closeable = MockitoAnnotations.openMocks(this);
	}
    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    /**
     * Test the loadUserByUsername method of the UserDetailsService implementation.
     * It verifies that the method correctly loads a user by username and creates
     * a UserDetails object with the expected properties.
     */
    @Test
    public void testLoadUserByUsername1() {
        // Create a sample user returned by the mocked UserService
        UserEntity userEntity = new UserEntity();
        userEntity.setId("0001");
        userEntity.setName("0001");
        userEntity.setPassword("password");


        // Set up the UserService mock behavior
        when(mockUserLogic.getLoginUser("0001")).thenReturn(userEntity);

        // Call the method to be tested
        UserDetails userDetails = mockUserDetailsLogicImpl.loadUserByUsername("0001");
        
        // Verify that the getLoginUser method of the UserService was called exactly once with the expected username
        verify(mockUserLogic, times(1)).getLoginUser("0001");
        
        // Assertions to verify the UserDetails object properties
        assertAll(
            // Verify that the user has the "GENERAL" authority
            () -> assertTrue(userDetails.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("GENERAL"))),
            
            // Verify that the username matches the expected value
            () -> assertEquals("0001", userDetails.getUsername()),
            
            // Verify that the password matches the expected value
            () -> assertEquals("password", userDetails.getPassword()),
            
            // Verify that the UserDetails object is not null
            () -> assertNotNull(userDetails)
        );
    }
    


    

}
