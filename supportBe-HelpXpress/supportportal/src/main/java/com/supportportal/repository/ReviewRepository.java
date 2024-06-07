package com.supportportal.repository;

import com.supportportal.domain.Review;
import com.supportportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.doctor.user.username = :username")
    List<Review> findAllByDoctorUsername(@Param("username") String username);
}
