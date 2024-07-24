package com.migration.mariadb.facility;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.migration.mariadb.facility.compositeid.CommonCodeIds;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodeIds> {
}
