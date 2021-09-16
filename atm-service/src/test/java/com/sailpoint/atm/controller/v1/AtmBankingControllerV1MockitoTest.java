package com.sailpoint.atm.controller.v1;

import com.sailpoint.atm.dto.v1.AmountDtoV1;
import com.sailpoint.atm.dto.v1.BalanceDtoV1;
import com.sailpoint.atm.facade.v1.AtmAuthenticationFacadeV1;
import com.sailpoint.atm.facade.v1.AtmBankingFacadeV1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AtmBankingControllerV1MockitoTest {
    private static final String CARD_NUMBER = "0123456789123456";
    private static final Double AMOUNT = 100d;

    @Mock
    private AtmAuthenticationFacadeV1 mockedAtmAuthenticationFacadeV1;

    @Mock
    private AtmBankingFacadeV1 mockedAtmBankingFacadeV1;

    @InjectMocks
    private AtmBankingControllerV1 atmBankingControllerV1;

    @Test
    public void deposit() {
        // Given
        AmountDtoV1 amountDtoV1 = AmountDtoV1.builder().value(AMOUNT).build();
        WebRequest mockedWebRequest = mock(WebRequest.class);
        given(mockedAtmAuthenticationFacadeV1.validateAndGetCardNumber(any())).willReturn(CARD_NUMBER);
        BalanceDtoV1 expectedBalanceDtoV1 = mock(BalanceDtoV1.class);
        given(mockedAtmBankingFacadeV1.deposit(anyString(), anyDouble())).willReturn(expectedBalanceDtoV1);

        // When
        ResponseEntity<BalanceDtoV1> result = atmBankingControllerV1.deposit(mockedWebRequest, amountDtoV1);

        // Then
        verify(mockedAtmAuthenticationFacadeV1).validateAndGetCardNumber(mockedWebRequest);
        verify(mockedAtmBankingFacadeV1).deposit(CARD_NUMBER, amountDtoV1.getValue());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expectedBalanceDtoV1);
    }

    @Test
    public void deposit_forbidden() {
        // Given
        AmountDtoV1 amountDtoV1 = AmountDtoV1.builder().value(AMOUNT).build();
        WebRequest mockedWebRequest = mock(WebRequest.class);
        given(mockedAtmAuthenticationFacadeV1.validateAndGetCardNumber(any()))
                .willThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Authorization header is required."));

        // When
        Throwable exception = catchThrowable(() -> atmBankingControllerV1.deposit(mockedWebRequest, amountDtoV1));

        // Then
        verify(mockedAtmAuthenticationFacadeV1).validateAndGetCardNumber(mockedWebRequest);
        verifyNoInteractions(mockedAtmBankingFacadeV1);

        assertThat(exception).isInstanceOf(ResponseStatusException.class).hasMessageContaining(HttpStatus.FORBIDDEN.toString());
    }
}
