package sypks_user_login.rest_controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.Login;
import rest_model.User;
import sypks_user_login.rest_services.LoginService;

@RestController
@RequestMapping("/login/oauth2")

public class LoginController {
	LoginService log = new LoginService();

	@RequestMapping(method = RequestMethod.POST)
	public void login(@RequestBody User user) {
		log.login(user);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Login getStatus() {
		return log.getStatus();
	}

}
