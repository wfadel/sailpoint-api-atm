package com.sailpoint.atm.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AtmCardRepository {
    private static final Map<String, String> database = new HashMap<>() {{
        put("0123456789123456", "1234");
        put("6123456789123450", "4321");
        put("0623456789123451", "2134");
        put("0163456789123452", "3214");
        put("0126456789123453", "3124");
        put("0123656789123454", "4123");
    }};

    public Optional<String> findPinNumberByCardNumber(String cardNumber) {
        return Optional.ofNullable(database.get(cardNumber));
    }
}
