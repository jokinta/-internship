

package sypks_user_login.rest_controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.StatusInfo;
import rest_model.User;
import sypks_user_login.rest_services.UnblockUserService;

@RestController
@RequestMapping("/unblockuser")

public class UnblockUserController {
	UnblockUserService unblockUserService = new UnblockUserService();

	@RequestMapping(method = RequestMethod.POST)
	public void unblock_user(@RequestBody User user) {
		unblockUserService.unblock_user(user);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public StatusInfo getStatus() {
		return unblockUserService.getStatus();
	}

}
