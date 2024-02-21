package jp.co.cyzennt.rs.rs_spring_alier.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * This class log-outs the user.
 * @author Alier Torrenueva
 * 01/18/2024
 */
@Controller
public class LogoutController {

	/**
	 * @return "redirect:/login"
	 */
	@PostMapping(value = "/logout")
	public String postLogout() {
		// the method returns a string 
		return "redirect:/login";
	}
}
