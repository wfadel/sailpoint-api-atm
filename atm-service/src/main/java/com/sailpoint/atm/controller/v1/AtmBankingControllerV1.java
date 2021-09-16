package com.sailpoint.atm.controller.v1;

import com.sailpoint.atm.dto.v1.AmountDtoV1;
import com.sailpoint.atm.dto.v1.BalanceDtoV1;
import com.sailpoint.atm.facade.v1.AtmAuthenticationFacadeV1;
import com.sailpoint.atm.facade.v1.AtmBankingFacadeV1;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api/v1/atm")
public class AtmBankingControllerV1 {

    @Autowired
    private AtmAuthenticationFacadeV1 atmAuthenticationFacadeV1;

    @Autowired
    private AtmBankingFacadeV1 atmBankingFacadeV1;

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDtoV1> getBalance(WebRequest webRequest) {
        String cardNumber = atmAuthenticationFacadeV1.validateAndGetCardNumber(webRequest);

        BalanceDtoV1 result = atmBankingFacadeV1.getBalance(cardNumber);

        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDtoV1> deposit(WebRequest webRequest, @Valid @RequestBody AmountDtoV1 amountDtoV1) {
        String cardNumber = atmAuthenticationFacadeV1.validateAndGetCardNumber(webRequest);

        BalanceDtoV1 result = atmBankingFacadeV1.deposit(cardNumber, amountDtoV1.getValue());

        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BalanceDtoV1> withdraw(WebRequest webRequest, @Valid @RequestBody AmountDtoV1 amountDtoV1) {
        String cardNumber = atmAuthenticationFacadeV1.validateAndGetCardNumber(webRequest);

        BalanceDtoV1 result = atmBankingFacadeV1.withdraw(cardNumber, amountDtoV1.getValue());

        return ResponseEntity.ok(result);
    }
}
