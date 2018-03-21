package pl.steevit.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import pl.steevit.blog.service.UserService;

@Configuration
@Order(1)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserService userService;

	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/api/**")
			.httpBasic()
			.and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET).hasRole("USER")
			.antMatchers(HttpMethod.POST).hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT).hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
			.anyRequest().authenticated()
			.and()
			.userDetailsService(userService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
}
