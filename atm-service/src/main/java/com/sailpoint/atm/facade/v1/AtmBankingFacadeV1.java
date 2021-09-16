package com.sailpoint.atm.facade.v1;

import com.sailpoint.atm.dto.v1.BalanceDtoV1;
import com.sailpoint.atm.service.AtmBankingService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtmBankingFacadeV1 {
    @Autowired
    private AtmBankingService atmBankingService;

    public BalanceDtoV1 deposit(String cardNumber, double value) {
        return BalanceDtoV1.builder().currentBalance(atmBankingService.deposit(cardNumber, value)).build();
    }

    public BalanceDtoV1 withdraw(String cardNumber, Double value) {
        return BalanceDtoV1.builder().currentBalance(atmBankingService.withdraw(cardNumber, value)).build();
    }

    public BalanceDtoV1 getBalance(String cardNumber) {
        return BalanceDtoV1.builder().currentBalance(atmBankingService.getBalance(cardNumber)).build();
    }
}
