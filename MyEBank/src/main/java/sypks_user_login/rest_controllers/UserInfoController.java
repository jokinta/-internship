package sypks_user_login.rest_controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.UserInfo;
import rest_model.User;
import sypks_user_login.rest_services.GetUserInfo;

@RestController
@RequestMapping("/getuserinfo")

public class UserInfoController {
	GetUserInfo info = new GetUserInfo();

	@RequestMapping(method = RequestMethod.POST)
	public void UserInfo(@RequestBody User user) {
		info.getInfo(user);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public UserInfo getStatus() {
		return info.getStatus();
	}

}
