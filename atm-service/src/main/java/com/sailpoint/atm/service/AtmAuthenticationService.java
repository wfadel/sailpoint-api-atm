package com.sailpoint.atm.service;

import com.sailpoint.atm.repository.AtmCardRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AtmAuthenticationService {

    private final Map<String, String> loggedInCustomers = new HashMap<>();

    @Autowired
    private AtmCardRepository atmCardRepository;

    public String login(String cardNumber) {
        Optional<String> dbPinNumber = atmCardRepository.findPinNumberByCardNumber(cardNumber);
        if (!dbPinNumber.isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid card or pin number");
        }

        String token = generateToken();
        loggedInCustomers.put(token, cardNumber);
        return token;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String validateAndGetCardNumber(String accessToken) {
        String result = loggedInCustomers.get(accessToken);
        if (!StringUtils.hasText(result)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid access token");
        }

        return result;
    }
}
