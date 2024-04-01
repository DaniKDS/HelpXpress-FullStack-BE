package com.supportportal.repository;

import com.supportportal.domain.Review;
import com.supportportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
