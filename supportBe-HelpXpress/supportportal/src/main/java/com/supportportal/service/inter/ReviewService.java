package com.supportportal.service.inter;

import com.supportportal.domain.Review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllReviewsByDoctorUsername(String username);
}