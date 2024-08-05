package org.twspring.capstone1.Service.Interfaces;

import org.twspring.capstone1.Model.Review;

import java.util.ArrayList;

public interface IReviewService {

    public ArrayList<Review> getReviews();
    public ArrayList<Review> getProductReviews(int productId);
    public Review getReview(int id);
    public int addReview(Review review);
    public int updateReview(int id, Review review);
    public boolean deleteReview(int id);
}
