package org.twspring.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.twspring.capstone1.Api.ApiResponse;
import org.twspring.capstone1.Model.MerchantStock;
import org.twspring.capstone1.Service.Impl.MerchantStockService;

@RestController
@RequestMapping("api/v1/merchant_stock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    //=======================================GET=======================================
    @GetMapping("/get/merchant_stocks")
    public ResponseEntity getMerchantStocks() {
        if (merchantStockService.getMerchantStocks().isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No merchant stocks found"));
        }
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    @GetMapping("/get/merchant_stock/{id}")
    public ResponseEntity getMerchantStock(@PathVariable int id) {
        if (merchantStockService.getMerchantStock(id)==null) {
            return ResponseEntity.status(404).body(new ApiResponse("No merchant stock with ID "+id+" found"));
        }
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStock(id));
    }
    //=======================================POST=======================================
    @PostMapping("/add/merchant_stock")
    public ResponseEntity addMerchantStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        merchantStockService.addMerchantStock(merchantStock);
        return ResponseEntity.status(201).body(new ApiResponse("Merchant stock added successfully"));
    }
    //=======================================UPDATE=======================================
    @PutMapping("/update/merchant_stock/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable int id, @Valid @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));
        }
        boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);
        if (isUpdated) {
            return ResponseEntity.status(201).body(new ApiResponse("Merchant stock updated successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("No merchant stock with ID "+id+" found"));
    }
    //=======================================DELETE=======================================
    @DeleteMapping("/delete/merchant_stock/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable int id) {
        boolean isDeleted = merchantStockService.deleteMerchantStock(id);
        if (isDeleted) {
            return ResponseEntity.status(201).body(new ApiResponse("Merchant stock deleted successfully"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("No merchant stock with ID "+id+" found"));
    }
}
