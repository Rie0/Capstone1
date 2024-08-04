package org.twspring.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.twspring.capstone1.Api.ApiResponse;
import org.twspring.capstone1.Model.User;
import org.twspring.capstone1.Service.Impl.UserService;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //=======================================GET=======================================
    @GetMapping("/get/users")
    public ResponseEntity getUsers() {
        if(userService.getUsers().isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No users found"));
        }
        return ResponseEntity.status(200).body(userService.getUsers());
    }
    @GetMapping("/get/user/{id}")
    public ResponseEntity getUser(@PathVariable int id) {
        if (userService.getUser(id) == null) {
            return ResponseEntity.status(404).body(new ApiResponse("No user with ID " + id + " found"));
        }
        return ResponseEntity.status(200).body(userService.getUser(id));
    }
    //=======================================POST=======================================
    @PostMapping("add/user")
    public ResponseEntity addUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        userService.addUser(user);
        return ResponseEntity.status(201).body(new ApiResponse("User added successfully"));
    }
    //=======================================UPDATE=======================================
    @PutMapping("/update/user/{id}")
    public ResponseEntity updateUser(@PathVariable int id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        boolean isUpdated = userService.updateUser(id, user);
        if (isUpdated) {
            return ResponseEntity.status(201).body(new ApiResponse("User updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("No user with ID " + id + " found"));
    }
    //=======================================DELETE=======================================
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.status(201).body(new ApiResponse("User deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("No user with ID " + id + " found"));
    }
}
