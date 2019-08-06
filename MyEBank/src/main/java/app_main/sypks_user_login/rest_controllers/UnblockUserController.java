

package app_main.sypks_user_login.rest_controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app_main.model.StatusInfo;
import app_main.rest_model.User;
import app_main.sypks_user_login.rest_services.UnblockUserService;

@RestController
@RequestMapping("admin/unblockuser")

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
