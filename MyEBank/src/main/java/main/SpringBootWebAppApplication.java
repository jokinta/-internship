package main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages= {"sypks_accounts.rest_controller"})
@ComponentScan(basePackages= {"sypks_accounts.rest_services"})
@ComponentScan(basePackages= {"sypks_user_login.rest_controllers"})
@ComponentScan(basePackages= {"sypks_user_login.rest_services"})
@ComponentScan(basePackages= {"sypks_transactions.services"})
@ComponentScan(basePackages= {"sypks_transactions.controllers"})

@SpringBootApplication
public class SpringBootWebAppApplication {
	public static void main(String[] args) {

	SpringApplication.run(SpringBootWebAppApplication.class, args);
	}
}
