package com.bankingApp.controller;

import com.bankingApp.constant.EResponse;
import com.bankingApp.model.User;
import com.bankingApp.repository.UserRepository;
import com.bankingApp.service.TransactionService;
import com.bankingApp.utility.BaseResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("transaction")
public class TransactionController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    private UserRepository userRepository;
    private TransactionService transactionService;

    public TransactionController(UserRepository userRepository, TransactionService transactionService) {
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }

    @PostMapping("/debit")
	public ResponseEntity<BaseResponse> withdrawal(Principal principal, @RequestParam double amount) {
		log.info("Inside withdrawal()");
		BaseResponse response = new BaseResponse();

		String username = principal.getName();
		User user = userRepository.findByUsername(username);
		try {
			String amt = transactionService.withdrawal(user, amount);
			response.setResponseCode(EResponse.SUCCESS.getCode());
			response.setResponseMessage(EResponse.SUCCESS.getMessage());
			response.setResponseBody(amt);
		} catch (Exception e) {
			response.setResponseCode(EResponse.BAD_REQUEST.getCode());
			response.setResponseMessage(EResponse.BAD_REQUEST.getMessage());
			response.setResponseBody(e.getMessage());
		}
		log.info("Exit withdrawal()");
		return ResponseEntity.ok(response);
	}

    @PostMapping("/credit")
    public ResponseEntity<BaseResponse> deposit(Principal principal, @RequestParam double amount) {
    	log.info("Inside deposit()");
		BaseResponse response = new BaseResponse();
        
        try {
        	String username = principal.getName();
            User user = userRepository.findByUsername(username);
            String amt = transactionService.deposit(user, amount);
			response.setResponseCode(EResponse.SUCCESS.getCode());
			response.setResponseMessage(EResponse.SUCCESS.getMessage());
			response.setResponseBody(amt);
		} catch (Exception e) {
			response.setResponseCode(EResponse.BAD_REQUEST.getCode());
			response.setResponseMessage(EResponse.BAD_REQUEST.getMessage());
			response.setResponseBody(e.getMessage());
		}
        return ResponseEntity.ok(response);
    }
}