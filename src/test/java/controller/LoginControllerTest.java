package controller;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.cyzennt.rs.rs_spring_alier.controller.MainController;
import jp.co.cyzennt.rs.rs_spring_alier.login.LoginController;

/**
 * This class test the login Controller
 * @author Alier Torrenueva
 * 01/27/2024
 */
public class LoginControllerTest {

	// Declaration loginController for mocks
    private LoginController loginController;
    // Declaration mainControllerMock for mocks
    private MainController mainControllerMock;


	 //This method sets up controller mocks for each test.
    @BeforeEach
    public void setUp() {
    	// Creates a mock of MainController.
        mainControllerMock = mock(MainController.class);
        
        // Initializes LoginController with the MainController mock.
        loginController = new LoginController(mainControllerMock);
    }

    //This method unit test the GetLogin method of Login Controller
    @Test
    public void testGetLogin() throws Exception {
    	// Set up MockMvc for loginController.
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

     // Perform a GET request to "/login".
        mockMvc.perform(get("/login"))
        	 // Assert that the status is OK (200).
                .andExpect(status().isOk())
             // Assert that the view name is "login/login".
                .andExpect(view().name("login/login"));
    }
}

