package com.supportportal.repository;

import com.supportportal.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long>{
}
