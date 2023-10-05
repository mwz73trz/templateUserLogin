package com.techelevator.dao;

import com.techelevator.model.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getAllReviews();

    public Review createReview(Review review);

}
