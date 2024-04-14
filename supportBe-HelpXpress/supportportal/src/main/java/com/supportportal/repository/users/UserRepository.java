package com.supportportal.repository.users;

import com.supportportal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<User> findTop100ByOrderByJoinDateDesc();
    List<User> findAllByRole(String name);
    @Query("SELECT su.id FROM User su")
    List<Long> findAllUserIds();
}
