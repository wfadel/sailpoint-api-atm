package com.sailpoint.atm.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class AtmAccountRepository {
    private final Map<String, Double> accounts = new ConcurrentHashMap<>();

    public Optional<Double> getBalance(String cardNumber) {
        return Optional.ofNullable(accounts.get(cardNumber));
    }

    public void saveBalance(String cardNumber, Double newBalance) {
        accounts.put(cardNumber, newBalance);
    }
}
