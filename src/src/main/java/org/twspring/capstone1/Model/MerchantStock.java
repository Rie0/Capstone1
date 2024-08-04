package org.twspring.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotNull(message = "ID cannot be empty")
    @Positive(message = "ID cannot be a zero or a negative number")
    @Min(value = 1, message = "ID cannot be less than 1")
    private int id;

    @NotNull(message = "ProductId cannot be empty")
    @Positive(message = "ProductId cannot be a zero or a negative number")
    @Min(value = 1, message = "ProductId cannot be less than 1")
    private int productId;

    @NotNull(message = "MerchantId cannot be empty")
    @Positive(message = "MerchantId cannot be a zero or a negative number")
    @Min(value = 1, message = "MerchantId cannot be less than 1")
    private int merchantId;

    @NotNull(message = "Stock cannot be empty")
    @Positive(message = "Stock cannot be a zero or a negative number")
    @Min(value = 11, message = "Stock cannot be less than 11") //at the start
    private int stock;
}
