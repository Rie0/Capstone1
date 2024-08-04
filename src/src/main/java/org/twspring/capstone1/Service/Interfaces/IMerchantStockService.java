package org.twspring.capstone1.Service.Interfaces;

import org.twspring.capstone1.Model.MerchantStock;

import java.util.ArrayList;

public interface IMerchantStockService {
    public ArrayList<MerchantStock> getMerchantStocks();
    public MerchantStock getMerchantStock(int id);
    public void addMerchantStock(MerchantStock merchantStock);
    public boolean updateMerchantStock(int id, MerchantStock merchantStock);
    public boolean deleteMerchantStock(int id);
}
