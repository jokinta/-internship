package sypks_transactions.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import model.StatusInfo;
import rest_model.Transaction;
import sypks_transactions.services.CreateTransactionService;

@RestController
@RequestMapping("/createtransaction")

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
