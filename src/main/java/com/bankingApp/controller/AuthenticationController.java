package com.bankingApp.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.constant.EResponse;
import com.bankingApp.model.AuthenticationRequest;
import com.bankingApp.model.AuthenticationResponse;
import com.bankingApp.model.User;
import com.bankingApp.security.JwtUtil;
import com.bankingApp.service.UserService;
import com.bankingApp.utility.BaseResponse;


@RestController
@RequestMapping("auth")
public class AuthenticationController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/signup")
	public ResponseEntity<BaseResponse> signUp(@RequestBody User user) {
		log.info("Inside signUp()");
		BaseResponse response = new BaseResponse();
		try {
			userService.registerUser(user);
			response.setResponseCode(EResponse.SUCCESS.getCode());
			response.setResponseMessage(EResponse.SUCCESS.getMessage());
			response.setResponseBody("User registered successfully");
		} catch (Exception e) {
			response.setResponseCode(EResponse.FAILED.getCode());
			response.setResponseMessage(EResponse.FAILED.getMessage());
			response.setResponseBody(e.getMessage());

		}
		log.info("Exit signUp()");
		return ResponseEntity.ok(response);
	}

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
		log.info("Inside authenticateUser()");
		BaseResponse response = new BaseResponse();
        try {
        	authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
        	response.setResponseCode(EResponse.UNAUTHORIZED.getCode());
			response.setResponseMessage(EResponse.UNAUTHORIZED.getMessage());
			response.setResponseBody(e.getMessage());
        }

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        response.setResponseCode(EResponse.SUCCESS.getCode());
		response.setResponseMessage(EResponse.SUCCESS.getMessage());
		response.setResponseBody(new AuthenticationResponse(token));
        return ResponseEntity.ok(response);
    }
}