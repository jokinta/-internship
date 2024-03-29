package app_main;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
 
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
 
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//-- define URL patterns to enable OAuth2 security
		http
	    .csrf().disable()

		.authorizeRequests()
		
		.antMatchers("/user/**").access("hasRole('USER')")
		.antMatchers("/admin/**").access("hasRole('ADMIN')")

		.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
	
	
	
}