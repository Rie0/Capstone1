package org.twspring.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

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

    @NotNull(message = "On sale cannot be null")
    @AssertFalse(message = "On sale must be initiated to false")
    private boolean onSale;

    @NotNull(message = "Number of reviews cannot be null")
    @Range(min=0,max=0, message = "Number of reviews must be 0 at creation")
    private int numberOfReview;

    @NotNull(message = "Average score cannot be null")
    @Range(min=0,max=0, message = "Products must have 0 score at creation")
    private double averageScore;
}
