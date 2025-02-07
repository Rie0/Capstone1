package org.twspring.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
public class Review {
    @NotNull(message = "ID cannot be empty")
    @Positive(message = "ID cannot be a zero or a negative number")
    @Min(value = 1, message = "ID cannot be less than 1")
    private int id;

    @NotNull(message = "User ID cannot be empty")
    @Positive(message = "User ID cannot be a zero or a negative number")
    @Min(value = 1, message = "User ID cannot be less than 1")
    private int userId;

    @NotNull(message = "Product ID cannot be empty")
    @Positive(message = "Product ID cannot be a zero or a negative number")
    @Min(value = 1, message = "Product ID cannot be less than 1")
    private int productId;

    @NotNull(message = "Score cannot be empty")
    @Positive(message = "Score cannot be a zero or a negative number")
    @Range(min = 1, max = 5, message = "Score must be between 1 to 5")
    private int score;

    @NotEmpty(message = "Comment cannot be empty")
    @Size(min = 5, max = 255, message = "Comment must have between 5 to 255 characters")
    private String comment;
}
