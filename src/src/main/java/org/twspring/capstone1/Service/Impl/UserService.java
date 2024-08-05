package org.twspring.capstone1.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Merchant;
import org.twspring.capstone1.Model.MerchantStock;
import org.twspring.capstone1.Model.User;
import org.twspring.capstone1.Service.Interfaces.IUserService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    ArrayList<User> users = new ArrayList<>();
     static final double primeSubscriptionCost = 4.99;
     static final double primeDiscount = 0.2; //%20 discount

    private final MerchantService merchantService;
    private final ProductService productService;
    private final MerchantStockService merchantStockService;

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public User getUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public boolean updateUser(int id, User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, user);
                return true;
            }
        }
        return false;
    }

    //EXTRA: PRIME MEMBERSHIP
    @Override
    public int subscribeToPrime(int id) {
        User user = getUser(id);
        //check cases:
        if(user == null) {
            return 1; //user with ID doesn't exist.
        }
        if (user.getRole().equalsIgnoreCase("Admin")){
            return 2; //Only customers can apply for prime membership
        }
        if (user.isPrimeMember()) {
            return 3; //Already a member
        }
            if (user.getBalance()<primeSubscriptionCost) {
            return 4; //Not enough balance
        }
        user.setBalance(getUser(id).getBalance()-primeSubscriptionCost);
        user.setPrimeMember(true);
        return 0; //success
    }

    @Override
    public int purchaseProduct(int userId, int merchantId, int productId) {
        User user = getUser(userId);
        //Not found cases
        if (merchantService.getMerchant(merchantId) == null) {
            return 1; //case 1: no merchant with ID found
        }
        if (productService.getProduct(productId) == null) {
            return 2; //case 2: no product with ID found
        }
        if (user == null) {
            return 3; //case 3: no user with ID found
        }
        if (user.getRole().equalsIgnoreCase("Admin")){
            return 4; //case 4: User is an admin, admins cannot purchase from the website.
        }

        //Check if a merchant has the product
        for (MerchantStock merchantStock: merchantStockService.getMerchantStocks()) {
            if (merchantStock.getMerchantId()==merchantId && merchantStock.getProductId()==productId) {
                //check if the product is stocked
                if (merchantStock.getStock()>0){
                    if (user.getBalance()>=productService.getProduct(productId).getPrice()){

                        //EXTRA APPLY PRIME MEMBERSHIP DISCOUNT
                        if (user.isPrimeMember()){

                            merchantStock.setStock(merchantStock.getStock()-1);
                            user.setBalance(user.getBalance()-(
                                    productService.getProduct(productId).getPrice()-(productService.getProduct(productId).getPrice()*primeDiscount)));

                            return 0;// case 0: Success (change to a new case? yes)
                        }

                        merchantStock.setStock(merchantStock.getStock()-1);
                        user.setBalance(user.getBalance()-productService.getProduct(productId).getPrice());
                        return 0;// case 0: Success
                    }else {
                        return 5;// case 5: Not enough balances
                    }
                }else {
                    return 6;// case 6: Item out of stock
                }
            }
        }
        return 7;//case 7: Merchant doesn't sell the product
    }

    @Override
    public boolean deleteUser(int id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.remove(i);
                return true;
            }
        }
        return false;
    }
}
