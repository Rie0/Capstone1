package org.twspring.capstone1.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Merchant;
import org.twspring.capstone1.Model.MerchantStock;
import org.twspring.capstone1.Service.Interfaces.IMerchantStockService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService implements IMerchantStockService {
    ArrayList<MerchantStock> merchantStocks= new ArrayList<>();

    //inject product and merchant to access the arrays
    @Autowired
    private ProductService productService;
    @Autowired
    private MerchantService merchantService;

    @Override
    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }

    @Override
    public MerchantStock getMerchantStock(int id) {
       for (MerchantStock merchantStock : merchantStocks) {
           if (merchantStock.getId() == id) {
               return merchantStock;
           }
       }
       return null;
    }

    @Override
    public int addMerchantStock(MerchantStock merchantStock) {
        //check of merchant exists
        if (merchantService.getMerchant(merchantStock.getMerchantId())==null) {
            return 1; //case 1: no merchant with ID found
        }
        if (productService.getProduct(merchantStock.getProductId())==null) {
            return 2; //case 2: no product with ID found
        }
        merchantStocks.add(merchantStock);
        return 0; //success
    }

    @Override
    public boolean updateMerchantStock(int id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId() == id) {
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }

    @Override
    public int updateStock(int id, int productId, int merchantId, int amount) {
        //1- Not found cases

        //Check if a product with the given id exists, if not return 1

        if (productService.getProduct(productId) == null) {
            return 1; //Case 1: no product with ID found
        }

        //Check if a merchant with the given id exists, if not return 2
        if (merchantService.getMerchant(merchantId) == null) {
            return 2; //Case 2: no product with ID found
        }

        //2- Mismatch cases

        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getId() == id) {
                if (merchantStock.getProductId() != productId) {
                    return 3; //Case 3: The product with ID is not the product of this stock
                }
                if (merchantStock.getMerchantId() != merchantId) {
                    return 4; //Case 4: The merchant with ID is not the owner of this stock
                }
                //3- Amount Input validation failure case
                if (amount < 0) {
                    return 5; //Case 5: The amount cannot be less than 1
                }
                //if passed all conditions
                merchantStock.setStock(merchantStock.getStock() + amount);
                return 0; //case 0: success
            }
        }
        return 6; //case 6: no merchant stock with ID found
    }


    @Override
    public boolean deleteMerchantStock(int id) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId() == id) {
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }
}
