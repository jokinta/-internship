package app_main.sypks_user_login.rest_controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app_main.model.UserInfo;
import app_main.rest_model.User;
import app_main.sypks_user_login.rest_services.GetUserInfo;

@RestController
@RequestMapping("/getuserinfo")

public class UserInfoController {
	GetUserInfo info = new GetUserInfo();
	 User user;


	  public void UserInfo(Principal principal) {
		 user.setUsername(principal.getName());
		 info.getInfo(user);	  
		 }
	  
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public UserInfo getStatus() {
		return info.getStatus();
	}

}
