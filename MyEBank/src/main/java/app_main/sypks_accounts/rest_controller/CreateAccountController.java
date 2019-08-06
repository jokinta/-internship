package app_main.sypks_accounts.rest_controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app_main.model.StatusInfo;
import app_main.rest_model.Account;
import app_main.sypks_accounts.rest_services.CreateAccountService;

@RestController
@RequestMapping("user/createacc")

public class CreateAccountController {
	CreateAccountService createAccount = new CreateAccountService();

	@RequestMapping(method = RequestMethod.POST)
	public void create_acc(@RequestBody Account account) {
		createAccount.create_acc(account);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public StatusInfo getStatus() {
		return createAccount.getStatus();
	}

}
