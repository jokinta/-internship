package app_main;
import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@ComponentScan(basePackages= {"sypks_accounts.rest_controller"})
@ComponentScan(basePackages= {"sypks_accounts.rest_services"})
@ComponentScan(basePackages= {"sypks_user_login.rest_controllers"})
@ComponentScan(basePackages= {"sypks_user_login.rest_services"})
@ComponentScan(basePackages= {"sypks_transactions.services"})
@ComponentScan(basePackages= {"sypks_transactions.controllers"})

@RestController
@EnableResourceServer
@SpringBootApplication
public class SpringBootWebAppApplicationSecurity {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebAppApplicationSecurity.class, args);
	}

	@RequestMapping("/validateUser")
	public Principal user(Principal user) {
		return user;
	}
	
}
