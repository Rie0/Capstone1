package org.twspring.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.twspring.capstone1.Api.ApiResponse;
import org.twspring.capstone1.Model.Merchant;
import org.twspring.capstone1.Service.Impl.MerchantService;

import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    //=======================================GET=======================================
    @GetMapping("/get/merchants")
    public ResponseEntity getMerchants() {
        if (merchantService.getMerchants().isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No merchants found"));
        }
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @GetMapping("/get/merchant/{id}")
    public ResponseEntity getMerchant(@PathVariable int id) {
        if (merchantService.getMerchant(id)==null){
            return ResponseEntity.status(404).body(new ApiResponse("No merchant with ID "+id+" found"));
        }
        return ResponseEntity.status(200).body(merchantService.getMerchant(id));
    }
    //=======================================POST=======================================
    @PostMapping("add/merchant")
    public ResponseEntity addMerchant(@Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        merchantService.addMerchant(merchant);
        return ResponseEntity.status(201).body(new ApiResponse("Merchant added successfully"));
    }

    //FOR TESTS
    @PostMapping("add/merchants")
    public ResponseEntity addMerchants() {
        Merchant merchant1 = new Merchant(1,"Merchant 1");
        Merchant merchant2 = new Merchant(2,"Merchant 2");
        merchantService.addMerchant(merchant1);
        merchantService.addMerchant(merchant2);
        return ResponseEntity.status(201).body(new ApiResponse("Merchants added successfully"));
    }
    //=======================================UPDATE=======================================
    @PutMapping("update/merchant/{id}")
    public ResponseEntity updateMerchant(@PathVariable int id,@Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        boolean isUpdated = merchantService.updateMerchant(id, merchant);
        if (isUpdated) {
            return ResponseEntity.status(201).body(new ApiResponse("Merchant updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("No merchant with ID "+id+" found"));
    }
    //=======================================DELETE=======================================
    @DeleteMapping("delete/merchant/{id}")
    public ResponseEntity deleteMerchant(@PathVariable int id) {
        boolean isDeleted = merchantService.deleteMerchant(id);
        if (isDeleted) {
            return ResponseEntity.status(201).body(new ApiResponse("Merchant deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("No merchant with ID "+id+" found"));
    }
}
