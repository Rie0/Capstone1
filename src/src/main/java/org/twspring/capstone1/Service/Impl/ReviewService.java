package org.twspring.capstone1.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.twspring.capstone1.Model.Product;
import org.twspring.capstone1.Model.Review;
import org.twspring.capstone1.Service.Interfaces.IReviewService;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
    ArrayList<Review> reviews = new ArrayList<>();

    private final UserService userService;
    private final ProductService productService;

    //GET
    @Override //get all
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    @Override //get all (by product)
    public ArrayList<Review> getProductReviews(int productId) {
        ArrayList<Review> foundReviews = new ArrayList();
        for (Review review : reviews) {
            if (review.getProductId() == productId) {
                foundReviews.add(review);
            }
        }
        return foundReviews;
    }

    @Override //get one
    public Review getReview(int id) {
        for (Review review : reviews) {
            if (review.getId() == id) {
                return review;
            }
        }
        return null;
    }

    //POST
    @Override
    public int addReview(Review review) {
        //check if user posting exists
        if (userService.getUser(review.getUserId()) == null) {
            return 1; //case 1: User with ID doesn't exist
        }
        if (productService.getProduct(review.getProductId()) == null) {
            return 2; //case 2: Product with ID doesn't exist
        }

        reviews.add(review);

        //update product number of reviews and average score
        //in case it's the first review
        if (productService.getProduct(review.getProductId()).getNumberOfReview() == 0) {
            productService.getProduct(review.getProductId()).setAverageScore(review.getScore());
            productService.getProduct(review.getProductId()).setNumberOfReview(1);
            return 0;
        }

        Product product = productService.getProduct(review.getProductId());
        int oldNumberOfReviews = product.getNumberOfReview();
        double oldAverageScore = product.getAverageScore();

        product.setNumberOfReview(oldNumberOfReviews + 1);
        product.setAverageScore((oldAverageScore * oldNumberOfReviews + review.getScore()) / (oldNumberOfReviews + 1));

        return 0; // case 0: success
    }

    @Override
    public int updateReview(int id, Review review) {

        Review oldReview = getReview(id);
        if (oldReview == null) {
            return 5; // case 5: review doesn't exist
        }
        //check if user posting exists
        if (userService.getUser(getReview(id).getUserId()) == null) {
            return 1; //case 1: User with ID doesn't exist
        }
        if (productService.getProduct(getReview(id).getProductId()) == null) {
            return 2; //case 2: Product with ID doesn't exist
        }

        //You cannot change the user of the review or the product of the review
        if (review.getProductId() != getReview(id).getProductId()) {
            return 3; //case 3: invalid; you can't change the product of the review
        }
        if (review.getUserId() != getReview(id).getUserId()) {
            return 4; //case 4: invalid; you can't change the user of the review
        }

        // Update the review
        Product product = productService.getProduct(oldReview.getProductId());
        int oldScore = oldReview.getScore();
        int newScore = review.getScore();
        double oldAverageScore = product.getAverageScore();
        int numberOfReviews = product.getNumberOfReview();

        // Update average score
        product.setAverageScore(((numberOfReviews * oldAverageScore) - oldScore + newScore) / numberOfReviews);

        reviews.set(reviews.indexOf(oldReview), review);
        return 0; //success
    }



    @Override
    public boolean deleteReview(int id) {

        Review thisReview = getReview(id);
        if (thisReview == null) {
            return false; //doesn't exist
        }
        Product product = productService.getProduct(thisReview.getProductId());
        int oldNumberOfReviews = product.getNumberOfReview();
        double oldAverageScore = product.getAverageScore();

                // Update the product's review
                if (oldNumberOfReviews > 1) {
                    product.setNumberOfReview(oldNumberOfReviews - 1);
                    product.setAverageScore((oldNumberOfReviews * oldAverageScore - thisReview.getScore()) / (oldNumberOfReviews - 1));
                } else {
                    //in case there's only this review
                    product.setNumberOfReview(0);
                    product.setAverageScore(0);
                }
                reviews.remove(thisReview);
                return true; // Success
            }
}
