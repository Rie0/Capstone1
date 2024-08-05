package org.twspring.capstone1.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Merchant;
import org.twspring.capstone1.Model.User;
import org.twspring.capstone1.Service.Interfaces.IMerchantService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService implements IMerchantService {
    ArrayList<Merchant> merchants = new ArrayList<>();

    //fixes the circular dependency
    @Lazy
    @Autowired
    private UserService userService;

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
    public int certifyMerchant(int adminId, int merchantId) {
        User user = userService.getUser(adminId);
        Merchant merchant = getMerchant(merchantId);
        //check if exists
        if (user == null){
            return 1; //case: Admin doesn't exist
        }
        if (merchant == null){
            return 2; //case: merchant doesn't exist
        }
        //check if is admin
        if (!user.getRole().equalsIgnoreCase("Admin")){
            return 3; //case: Certification must be done by an admin
        }
        if (merchant.isCertified()){
            return 4; //case: Merchant already certified
        }
        merchant.setCertified(true);
        return 0; //success
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
