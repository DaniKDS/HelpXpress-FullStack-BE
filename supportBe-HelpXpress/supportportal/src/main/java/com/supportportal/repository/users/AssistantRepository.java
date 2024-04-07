package com.supportportal.repository.users;

import com.supportportal.domain.Assistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AssistantRepository extends JpaRepository<Assistant, Long> {
    Optional<Assistant> findById(Long assistantId);
    boolean existsByUserId(Long userId);
    Optional<Object> findByUserId(Long id);
    @Query("SELECT su.id FROM Assistant su")
    List<Long> findAllIds();
}
