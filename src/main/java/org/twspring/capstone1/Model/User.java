package org.twspring.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class User {

    @NotNull(message = "ID cannot be empty")
    @Positive(message = "ID cannot be a zero or a negative number")
    @Min(value = 1, message = "ID cannot be less than 1")
    private int id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min=6, message = "Username cannot have less than 6 letters")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 7, message = "Password must have at least 7 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{7,}$", message = "Password must have both characters and digits and a special character")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Role cannot be empty")
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role can only be Admin or User.")
    private String role;

    @NotNull(message = "Balance cannot be empty")
    @Positive(message = "Balance must be a positive number")
    private double balance;

    @NotNull(message = "Is prime member cannot be empty")
    @AssertFalse(message = "New members cannot have prime membership")
    private boolean isPrimeMember;

    //EXTRA
    @Null(message = "bought Products IDs must be initiated to null")
    private final ArrayList<Integer> boughtProductsIds = new ArrayList<>();

}
