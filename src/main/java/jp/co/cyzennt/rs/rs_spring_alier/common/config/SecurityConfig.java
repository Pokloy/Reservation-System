package jp.co.cyzennt.rs.rs_spring_alier.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * This class set on the security configuration of the web app.
 * @author Alier Torrenueva
 * 01/17/2024
 */


@EnableWebSecurity					
@Configuration					
public class SecurityConfig extends WebSecurityConfigurerAdapter {					
					
    @Autowired					
    private UserDetailsService userDetailsService;					
					
    
    /** configure exclusions*/					
    @Override					
    public void configure(WebSecurity web) throws Exception {					
        // Ignoring		 			
        web					
            .ignoring()					
                .antMatchers("/webjars/**")					
                .antMatchers("/css/**")					
                .antMatchers("/js/**")					
                .antMatchers("/h2-console/**");					
    }					
					
    /** Security settings */					
    @Override					
    protected void configure(HttpSecurity http) throws Exception {					
					
        // Setup pages to require login or not					
        http					
            .authorizeRequests()					
                .antMatchers("/login").permitAll() //Direct link is ok					
                .anyRequest().authenticated(); // Direct requires authentication					
					
        // login process					
        http					
            .formLogin()					
                .loginProcessingUrl("/login") // login process path					
                .loginPage("/login") // login page url					
                .failureUrl("/login?error") // transition destination upon login failure	
                .usernameParameter("id") // user id on login page					
                .passwordParameter("password") // password for login page					
                .defaultSuccessUrl("/main/main", true); // transition destination after login success					
					
        // logout process					
        http					
            .logout()					
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))					
                .logoutUrl("/logout")					
                .logoutSuccessUrl("/login?logout");					
    }					
					
    /** Configure Authentication */					
    @Override					
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {					
					
    	//PasswordEncoder encoder = passwordEncoder();					
					
        // User data authentication					
        auth					
            .userDetailsService(userDetailsService)					
            .passwordEncoder(NoOpPasswordEncoder.getInstance());				
    }					
}					

