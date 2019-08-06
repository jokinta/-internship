package app_main.sypks_accounts.rest_controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app_main.model.TuroverInfo;
import app_main.rest_model.Turnover;
import app_main.sypks_accounts.rest_services.GetTurnoverService;

@RestController
@RequestMapping("user/getturnover")

public class GetTurnoverController {
	GetTurnoverService turoverService = new GetTurnoverService();

	@RequestMapping(method = RequestMethod.POST)
	public void getTurover(@RequestBody Turnover turover) {
		turoverService.getTurover(turover);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ArrayList<TuroverInfo> getStatus() {
		return turoverService.getStatus();
	}
	
	



}
