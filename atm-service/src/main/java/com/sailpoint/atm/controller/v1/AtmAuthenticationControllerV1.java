package com.sailpoint.atm.controller.v1;

import com.sailpoint.atm.dto.v1.LoginDtoV1;
import com.sailpoint.atm.facade.v1.AtmAuthenticationFacadeV1;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/atm")
public class AtmAuthenticationControllerV1 {

    @Autowired
    private AtmAuthenticationFacadeV1 atmAuthenticationFacadeV1;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@Valid @RequestBody LoginDtoV1 loginDtoV1) {
        return ResponseEntity.ok(atmAuthenticationFacadeV1.login(loginDtoV1));
    }
}
