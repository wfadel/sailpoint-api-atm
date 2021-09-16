package com.sailpoint.atm.facade.v1;

import com.sailpoint.atm.dto.v1.LoginDtoV1;
import com.sailpoint.atm.service.AtmAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AtmAuthenticationFacadeV1 {

    @Autowired
    private AtmAuthenticationService atmAuthenticationService;

    public String login(LoginDtoV1 loginDtoV1) {
        return atmAuthenticationService.login(loginDtoV1.getCardNumber());
    }

    public String validateAndGetCardNumber(WebRequest webRequest) {
        String authorizationHeader = webRequest.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authorization header is required.");
        }

        String[] authorization = authorizationHeader.split(" ");
        if (authorization.length < 2) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bearer is required.");
        }

        return atmAuthenticationService.validateAndGetCardNumber(authorization[1]);
    }
}
