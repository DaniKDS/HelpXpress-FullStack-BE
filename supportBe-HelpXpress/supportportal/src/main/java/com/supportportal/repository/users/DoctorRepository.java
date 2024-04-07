package com.supportportal.repository.users;

import com.supportportal.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByUserId(Long id);
    @Query("SELECT su.id FROM Doctor su")
    List<Long> findAllIds();
}


