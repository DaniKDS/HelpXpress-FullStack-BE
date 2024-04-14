package com.supportportal.repository;

import com.supportportal.domain.Review;
import com.supportportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Aici adaugi query-ul care să returneze recenziile pe baza username-ului doctorului
    @Query("SELECT r FROM Review r WHERE r.doctor.user.username = :username")
    List<Review> findAllByDoctorUsername(@Param("username") String username);
}
