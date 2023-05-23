package com.bankingApp.controller;


import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.constant.EResponse;
import com.bankingApp.service.UserService;
import com.bankingApp.utility.BaseResponse;


@RestController
@RequestMapping("balance")
public class BalanceController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
    private UserService userService;

    public BalanceController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/checkbalance")
	public ResponseEntity<BaseResponse> getCurrentBalance(Principal principal) {
		log.info("Inside getCurrentBalance()");
		BaseResponse response = new BaseResponse();
		try {
			String username = principal.getName();
			double balance = userService.getCurrentBalance(username);
			response.setResponseCode(EResponse.SUCCESS.getCode());
			response.setResponseMessage(EResponse.SUCCESS.getMessage());
			response.setResponseBody(balance);
		} catch (Exception e) {
			response.setResponseCode(EResponse.FAILED.getCode());
			response.setResponseMessage(EResponse.FAILED.getMessage());
			response.setResponseBody(e.getMessage());

		}
		log.info("Exit getCurrentBalance()");
		return ResponseEntity.ok(response);
	}
    
}
