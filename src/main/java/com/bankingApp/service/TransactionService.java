package com.bankingApp.service;

import com.bankingApp.constant.EResponse;
import com.bankingApp.model.User;
import com.bankingApp.repository.UserRepository;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionService {
	private UserRepository userRepository;

	public TransactionService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public String withdrawal(User user, double amount) {
		double currentBalance = user.getBalance();
		if (amount <= 0 || currentBalance < amount) {
			return EResponse.BAD_REQUEST.getMessage();
		}
		user.setBalance(currentBalance - amount);
		userRepository.save(user);
		return String.valueOf(amount);
	}

	@Transactional
	public String deposit(User user, double amount) {
		if (amount <= 0) {
			return EResponse.BAD_REQUEST.getMessage();
		}
		double currentBalance = user.getBalance();
		user.setBalance(currentBalance + amount);
		userRepository.save(user);
		return String.valueOf(amount + " has been credited successfully");
	}
}