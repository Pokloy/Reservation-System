package controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.cyzennt.rs.rs_spring_alier.controller.LogoutController;


/**
 * This class test the log-out Controller
 * @author Alier Torrenueva
 * 01/27/2024
 */
public class LogoutControllerTest {

    private LogoutController logoutController;

    // This method sets up logoutController mocks for each test.
    @BeforeEach
    public void setUp() {
    	// Initializes LogoutController
        logoutController = new LogoutController();
    }

    
    // This method unit test the Post Logout method of logout Controller
    @Test
    public void testPostLogout() throws Exception {
    	// Set up MockMvc for logoutController.
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(logoutController).build();
        
        // Perform a POST request to "/logout".
        mockMvc.perform(post("/logout"))
    			// Assert that the status is a 3xx redirection.
                .andExpect(status().is3xxRedirection())
                // Assert that the redirected URL is "/login".
                .andExpect(redirectedUrl("/login"));
    }
}
