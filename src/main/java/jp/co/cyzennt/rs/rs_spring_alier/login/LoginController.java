package jp.co.cyzennt.rs.rs_spring_alier.login;

import java.time.LocalDate;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.cyzennt.rs.rs_spring_alier.controller.MainController;
import jp.co.cyzennt.rs.rs_spring_alier.login.dto.LoginFormWebDto;


/**
 * This is a controller for log-in
 * @author Alier Torrenueva
 * 01/19/2024
 */

@Controller
public class LoginController {

	private MainController mainController;
	
    @Autowired
    public LoginController(MainController mainController) {
        this.mainController = mainController;
    }
		
	/**
	 * @return goes to the page of "/login"
	 * @params model
	 * @params locale
	 * @params loginForm
	 */
	   @GetMapping("/login")
	   public String getLogin(Model model, @ModelAttribute("loginForm") LoginFormWebDto loginForm) {
		   // set the date to current when user logs-in
		   mainController.currentDate = LocalDate.now();
		   // return to the login page
	       return "login/login"; 
	  }
}

