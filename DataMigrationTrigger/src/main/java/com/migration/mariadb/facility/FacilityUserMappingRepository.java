package com.migration.mariadb.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.migration.mariadb.facility.compositeid.FacilityUserMappingIds;

@Repository
public interface FacilityUserMappingRepository extends JpaRepository<FacilityUserMapping, FacilityUserMappingIds> {
}
