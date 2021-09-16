package com.sailpoint.atm.dto.v1;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountDtoV1 {

    @NotNull(message = "Deposit value is required")
    @Positive(message = "The deposited value should be greater than 0")
    private Double value;
}
