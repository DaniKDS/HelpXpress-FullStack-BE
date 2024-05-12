package com.supportportal.repository;

import com.supportportal.domain.Benzinarie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenzinarieRepository extends JpaRepository<Benzinarie, Long> {
}
