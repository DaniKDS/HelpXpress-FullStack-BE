package com.supportportal.repository;

import com.supportportal.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Query("SELECT su.id FROM Organization su")
    List<Long> findAllIds();
}
