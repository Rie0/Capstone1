package org.twspring.capstone1.Service.Impl;

import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.MerchantStock;
import org.twspring.capstone1.Service.Interfaces.IMerchantStockService;

import java.util.ArrayList;

@Service
public class MerchantStockService implements IMerchantStockService {
    ArrayList<MerchantStock> merchantStocks= new ArrayList<>();

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
    public void addMerchantStock(MerchantStock merchantStock) {
        merchantStocks.add(merchantStock);
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
