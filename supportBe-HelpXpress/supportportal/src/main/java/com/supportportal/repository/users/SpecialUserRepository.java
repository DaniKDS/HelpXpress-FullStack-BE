package com.supportportal.repository.users;

import com.supportportal.domain.Doctor;
import com.supportportal.domain.Organization;
import com.supportportal.domain.SpecialUser;
import org.apache.tomcat.jni.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpecialUserRepository extends JpaRepository<SpecialUser, Long>
{
    boolean existsByUserId(Long userId);
    @Query("SELECT su.id FROM SpecialUser su")
    List<Long> findAllIds();
    SpecialUser findByUserUsername(String username);
}