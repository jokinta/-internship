package sypks_user_login.rest_controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.Registration;
import rest_model.UserRegistration;
import sypks_user_login.rest_services.RegistrationService;

@RestController
@RequestMapping("/registration")

public class RegistrationController {
	RegistrationService reg = new RegistrationService();

	@RequestMapping(method = RequestMethod.POST)
	public void registraion(@RequestBody UserRegistration userRegistration) {
		reg.registration(userRegistration);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Registration getStatus() {
		return reg.getStatus();
	}

}
