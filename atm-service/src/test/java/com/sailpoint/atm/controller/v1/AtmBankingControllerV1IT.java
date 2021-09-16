package com.sailpoint.atm.controller.v1;

import com.sailpoint.atm.dto.v1.AmountDtoV1;
import com.sailpoint.atm.dto.v1.BalanceDtoV1;
import com.sailpoint.atm.dto.v1.LoginDtoV1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AtmBankingControllerV1IT {
    private static final String CARD_NUMBER = "0123456789123456";
    private static final String PIN_NUMBER = "1234";
    private static final Double AMOUNT = 100d;
    TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @Test
    public void deposit() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<LoginDtoV1> loginEntity = new HttpEntity<>(LoginDtoV1.builder().cardNumber(CARD_NUMBER).pinNumber(PIN_NUMBER).build(), headers);
        String token = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/atm/login", HttpMethod.POST,
                loginEntity,
                String.class).getBody();

        headers.add("Authorization", "Bearer " + token);
        HttpEntity<AmountDtoV1> entity = new HttpEntity<>(AmountDtoV1.builder().value(AMOUNT).build(), headers);

        ResponseEntity<BalanceDtoV1> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/atm/deposit", HttpMethod.PUT, entity, BalanceDtoV1.class);

        BalanceDtoV1 actual = response.getBody();

        assertThat(actual).isEqualTo(BalanceDtoV1.builder().currentBalance(AMOUNT).build());
    }
}
