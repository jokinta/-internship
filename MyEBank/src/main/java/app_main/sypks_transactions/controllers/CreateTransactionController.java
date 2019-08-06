package app_main.sypks_transactions.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app_main.model.StatusInfo;
import app_main.rest_model.Transaction;
import app_main.sypks_transactions.services.CreateTransactionService;

@RestController
@RequestMapping("user/createtransaction")

public class CreateTransactionController {
	CreateTransactionService createTransactionService = new CreateTransactionService();

	@RequestMapping(method = RequestMethod.POST)
	public void create_transaction(@RequestBody Transaction transaction) {
		createTransactionService.create_transaction(transaction);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public StatusInfo getStatus() {
		return createTransactionService.getStatus();
	}

}
