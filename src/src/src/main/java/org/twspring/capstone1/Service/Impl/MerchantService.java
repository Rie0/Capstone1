package org.twspring.capstone1.Service.Impl;

import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Merchant;
import org.twspring.capstone1.Service.Interfaces.IMerchantService;

import java.util.ArrayList;

@Service
public class MerchantService implements IMerchantService {
    ArrayList<Merchant> merchants = new ArrayList<>();

    @Override
    public ArrayList<Merchant> getMerchants() {
        return merchants;
    }

    @Override
    public Merchant getMerchant(int id) {
        for (Merchant merchant : merchants) {
            if (merchant.getId() == id) {
                return merchant;
            }
        }
        return null;
    }

    @Override
    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    @Override
    public boolean updateMerchant(int id, Merchant merchant) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId() == id) {
                merchants.set(i, merchant);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteMerchant(int id) {
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId() == id) {
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }
}
