package jp.co.cyzennt.rs.rs_spring_alier.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This class Defines a Spring bean for the ModelMapper class.
 * @author Alier Torrenueva
 * 01/17/2024
 */

@Configuration
public class JavaConfig {
	
	
	/**
	 * This method will Instantiate and return a new instance of ModelMapper
	 * @return ModelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		// Create and return a new instance of ModelMapper.
		return new ModelMapper();
	} 
}
