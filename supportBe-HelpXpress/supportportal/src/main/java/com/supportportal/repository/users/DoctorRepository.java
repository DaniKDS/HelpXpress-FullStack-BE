package com.supportportal.repository.users;

import com.supportportal.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByUserId(Long id);
    @Query("SELECT su.id FROM Doctor su")
    List<Long> findAllIds();
    List<Doctor> findBySpecialUserId(Long id);

    @Query("SELECT d FROM Doctor d WHERE d.user.username = :username")
    Doctor findByUserUsername(@Param("username") String username);

}


