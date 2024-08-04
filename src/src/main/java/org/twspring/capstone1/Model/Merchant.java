package org.twspring.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotNull(message = "ID cannot be empty")
    @Positive(message = "ID cannot be a zero or a negative number")
    @Min(value = 1, message = "ID cannot be less than 1")
    private int id;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min=4, message = "Name cannot have less than 4 letters")
    private String name;
}
