package com.supportportal.repository;

import com.supportportal.domain.GazStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenzinarieRepository extends JpaRepository<GazStation, Long> {
}
