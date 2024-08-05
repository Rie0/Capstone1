package org.twspring.capstone1.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Product;
import org.twspring.capstone1.Model.Review;
import org.twspring.capstone1.Model.User;
import org.twspring.capstone1.Service.Interfaces.IReviewService;

import java.util.ArrayList;

@Service
public class ReviewService implements IReviewService {
    ArrayList<Review> reviews = new ArrayList<>();

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    //GET
    @Override //get all
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    @Override //get all (by product)
    public ArrayList<Review> getProductReviews(int productId) {
        ArrayList<Review> foundReviews = new ArrayList();
        for (Review review : reviews) {
            if (review.getProductId()==productId) {
                foundReviews.add(review);
            }
        }
        return foundReviews;
    }

    @Override //get one
    public Review getReview(int id) {
        for (Review review : reviews) {
            if (review.getId()==id) {
                return review;
            }
        }
        return null;
    }

    //POST
    @Override
    public int addReview(Review review) {
        //check if user posting exists
        if(userService.getUser(review.getUserId())==null){
            return 1; //case 1: User with ID doesn't exist
        }
        if (productService.getProduct(review.getProductId())==null) {
            return 2; //case 2: Product with ID doesn't exist
        }

        reviews.add(review);

        //update product number of reviews and average score
        //in case it's the first review
        if (productService.getProduct(review.getProductId()).getNumberOfReview()==0){
            productService.getProduct(review.getProductId()).setAverageScore(review.getScore());
            productService.getProduct(review.getProductId()).setNumberOfReview(1);
            return 0;
        }

        int oldNumberOfReview = productService.getProduct(review.getProductId()).getNumberOfReview();
        productService.getProduct(review.getProductId()).setAverageScore(((productService.getProduct(review.getProductId()).getAverageScore()*oldNumberOfReview)
                +review.getScore())/(oldNumberOfReview+1));
        productService.getProduct(review.getProductId()).setNumberOfReview(productService.getProduct(review.getProductId()).getNumberOfReview()+1); //count this review

        return 0; // case 0: success
    }

    @Override
    public int updateReview(int id, Review review) {

        //check if user posting exists
        if(userService.getUser(getReview(id).getUserId())==null){
            return 1; //case 1: User with ID doesn't exist
        }
        if (productService.getProduct(getReview(id).getProductId())==null) {
            return 2; //case 2: Product with ID doesn't exist
        }

        //You cannot change the user of the review or the product of the review
        if (review.getProductId() != getReview(id).getProductId()) {
            return 3; //case 3: invalid; you can't change the product of the review
        }
        if (review.getUserId() != getReview(id).getUserId()) {
            return 4; //case 4: invalid; you can't change the user of the review
        }

        for (Review re : reviews) {
            if (re.getId()==id) {

                //edit previous product score

                int oldScore = re.getScore();
                int newScore = review.getScore();
                double oldAverageScore = productService.getProduct(re.getProductId()).getAverageScore();
                int numberOfReviews = productService.getProduct(re.getProductId()).getNumberOfReview();

                productService.getProduct(re.getProductId()).setAverageScore(
                        (((numberOfReviews*oldAverageScore)-(oldScore))
                        +newScore)/(numberOfReviews)
                );

                reviews.set(reviews.indexOf(re), review);
                return 0; //success
            }
        }
        return 5; //case 5: review doesn't exist
    }

    @Override
    public boolean deleteReview(int id) {

        //in case only one review exists

        for (Review re : reviews) {
            if (re.getId()==id) {

                //in case only one review exists
                if (productService.getProduct(getReview(id).getProductId()).getNumberOfReview()>=1) {
                    productService.getProduct(re.getProductId()).setAverageScore(0);
                }

                //edit previous product score
                double oldAverageScore = productService.getProduct(re.getProductId()).getAverageScore();
                int oldNumberOfReviews = productService.getProduct(re.getProductId()).getNumberOfReview();

                //update number of reviews
                productService.getProduct(re.getProductId()).setNumberOfReview(oldNumberOfReviews-1);

                productService.getProduct(re.getProductId()).setAverageScore(
                        ((oldNumberOfReviews*oldAverageScore)-(re.getScore()))
                        / (productService.getProduct(re.getProductId()).getNumberOfReview())
                );

                reviews.remove(getReview(id));
                return true;
            }
        }
       return false;
    }
}
