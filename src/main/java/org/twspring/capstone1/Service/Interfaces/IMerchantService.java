package org.twspring.capstone1.Service.Interfaces;

import org.twspring.capstone1.Model.Merchant;

import java.util.ArrayList;

public interface IMerchantService {
    public ArrayList<Merchant> getMerchants();
    public Merchant getMerchant(int id);
    public void addMerchant(Merchant merchant);
    public boolean updateMerchant(int id, Merchant merchant);
    public boolean deleteMerchant(int id);
}
