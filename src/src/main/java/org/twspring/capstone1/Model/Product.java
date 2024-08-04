package org.twspring.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotNull(message = "ID cannot be empty")
    @Positive(message = "ID cannot be a zero or a negative number")
    @Min(value = 1, message = "ID cannot be less than 1")
    private int id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min=4, message = "Name cannot have less than 4 letters")
    private String name;

    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be greater than 0")
    private double price;

    @NotNull(message = "CategoryID cannot be empty")
    @Positive(message = "CategoryID cannot be a zero or a negative number")
    @Min(value = 1, message = "CategoryID cannot be less than 1")
    private int categoryId;
}
