package org.twspring.capstone1.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Merchant;
import org.twspring.capstone1.Model.MerchantStock;
import org.twspring.capstone1.Model.User;
import org.twspring.capstone1.Service.Interfaces.IUserService;

import java.util.ArrayList;

@Service
public class UserService implements IUserService {
    ArrayList<User> users = new ArrayList<>();
     static final double primeSubscriptionCost = 4.99;
     static final double primeDiscount = 0.2; //%20 discount

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MerchantStockService merchantStockService;

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

        //check cases:
        //user must be a customer
        //user must have enough money for subscription
        //add a date for renew?
        if(getUser(id) == null) {
            return 1; //user with ID doesn't exist.
        }
        if (getUser(id).getRole().equalsIgnoreCase("Admin")){
            return 2; //Only customers can apply for prime membership
        }
        if (getUser(id).isPrimeMember()) {
            return 3; //Already a member
        }
            if (getUser(id).getBalance()<primeSubscriptionCost) {
            return 4; //Not enough balance
        }
        getUser(id).setBalance(getUser(id).getBalance()-primeSubscriptionCost);
        getUser(id).setPrimeMember(true);
        return 0; //success
    }

    @Override
    public int purchaseProduct(int userId, int merchantId, int productId) {
        //Not found cases
        if (merchantService.getMerchant(merchantId) == null) {
            return 1; //case 1: no merchant with ID found
        }
        if (productService.getProduct(productId) == null) {
            return 2; //case 2: no product with ID found
        }
        if (getUser(userId) == null) {
            return 3; //case 3: no user with ID found
        }
        if (getUser(userId).getRole().equalsIgnoreCase("Admin")){
            return 4; //case 4: User is an admin, admins cannot purchase from the website.
        }

        //Check if a merchant has the product
        for (MerchantStock merchantStock: merchantStockService.getMerchantStocks()) {
            if (merchantStock.getMerchantId()==merchantId && merchantStock.getProductId()==productId) {
                //check if the product is stocked
                if (merchantStock.getStock()>0){
                    if (getUser(userId).getBalance()>=productService.getProduct(productId).getPrice()){

                        //EXTRA APPLY PRIME MEMBERSHIP DISCOUNT
                        if (getUser(userId).isPrimeMember()){

                            merchantStock.setStock(merchantStock.getStock()-1);
                            getUser(userId).setBalance(getUser(userId).getBalance()-(
                                    productService.getProduct(productId).getPrice()-(productService.getProduct(productId).getPrice()*primeDiscount)));

                            return 0;// case 0: Success (change to a new case? yes)
                        }

                        merchantStock.setStock(merchantStock.getStock()-1);
                        getUser(userId).setBalance(getUser(userId).getBalance()-productService.getProduct(productId).getPrice());
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
