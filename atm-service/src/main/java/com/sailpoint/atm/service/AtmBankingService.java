package com.sailpoint.atm.service;

import com.sailpoint.atm.repository.AtmAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AtmBankingService {

    @Autowired
    private AtmAccountRepository atmAccountRepository;

    public Double deposit(String cardNumber, double value) {
        Double currentBalance = atmAccountRepository.getBalance(cardNumber).orElse(0.0d);
        Double newBalance = currentBalance + value;
        atmAccountRepository.saveBalance(cardNumber, newBalance);

        return newBalance;
    }

    public Double withdraw(String cardNumber, Double value) {
        Double currentBalance = atmAccountRepository.getBalance(cardNumber).orElse(0.0d);
        if (currentBalance - value < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The requested amount is larger than the current balance");
        }

        Double newBalance = currentBalance - value;
        atmAccountRepository.saveBalance(cardNumber, newBalance);

        return newBalance;
    }

    public Double getBalance(String cardNumber) {
        return atmAccountRepository.getBalance(cardNumber).orElse(0.0d);
    }
}
