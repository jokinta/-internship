

package sypks_user_login.rest_controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.StatusInfo;
import rest_model.ChangePassword;
import sypks_user_login.rest_services.ChangePasswordService;

@RestController
@RequestMapping("/changepassword")

public class ChangePasswordController {
	ChangePasswordService changePasswordService = new ChangePasswordService();

	@RequestMapping(method = RequestMethod.POST)
	public void pass_change(@RequestBody ChangePassword changePassword) {
		changePasswordService.pass_change(changePassword);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public StatusInfo getStatus() {
		return changePasswordService.getStatus();
	}

}
