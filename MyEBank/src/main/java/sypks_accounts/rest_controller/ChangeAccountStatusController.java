package sypks_accounts.rest_controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.StatusInfo;
import rest_model.AccountStatus;
import sypks_accounts.rest_services.ChangeAccStatusService;

@RestController
@RequestMapping("admin/changeaccstatus")

public class ChangeAccountStatusController {
	ChangeAccStatusService changeAccStatus = new ChangeAccStatusService();

	@RequestMapping(method = RequestMethod.POST)
	public void change_acc_status(@RequestBody AccountStatus accStatus) {
		changeAccStatus.change_acc_status(accStatus);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public StatusInfo getStatus() {
		return changeAccStatus.getStatus();
	}

}
