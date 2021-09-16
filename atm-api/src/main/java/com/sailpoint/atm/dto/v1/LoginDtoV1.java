package com.sailpoint.atm.dto.v1;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDtoV1 {
    @NotBlank(message = "Pin number is required")
    @Pattern(regexp = "^[0-9]{4}", message = "Invalid pin number. Pin number must be exactly 4 digits")
    private String pinNumber;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "^[0-9]{16}", message = "Invalid card number. Card number must be exactly 16 digits")
    private String cardNumber;
}
