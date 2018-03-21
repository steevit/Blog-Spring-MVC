package pl.steevit.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import pl.steevit.blog.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http
	    .authorizeRequests()
	    .antMatchers("/posts/create").hasRole("ADMIN")
	    .antMatchers("/posts/edit/**").hasRole("ADMIN")
	    .antMatchers("/posts/delete/**").hasRole("ADMIN")
	    .antMatchers("/users/**").hasRole("ADMIN")
	    .and()
	    .userDetailsService(userService)
	    .formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/authenticateTheUser")
			.permitAll()
	    .and()
	    //.logout().clearAuthentication(true)
	    //.logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
	    //.invalidateHttpSession(true) 
	    //.and()
	    .authorizeRequests()
	    .and()
		.exceptionHandling().accessDeniedPage("/access-denied");

	}
	
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}
	
}
