package com.migration.mariadb.facility;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.migration.mariadb.facility.dto.FacilityDto;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {
  @Query("SELECT new com.migration.mariadb.facility.dto.FacilityDto(f.name, c.codeName) FROM Facility f inner join CommonCode c on f.type = c.code")
  List<FacilityDto> findAllFacilityDto();

  @Query("SELECT new com.migration.mariadb.facility.dto.FacilityDto(f.name, c.codeName) FROM Facility f inner join CommonCode c on f.type = c.code where f.name = :name")
  FacilityDto findFacilityDtoByName(String name);

  @Query(value = "select user.ENTRPRS_MBER_ID as userId, facility.FCLT_NM as facilityName from tb_cmn_user_fclt_mpng_dsctn mapping inner join comtnentrprsmber user on mapping.USER_UNQ_ID = user.ESNTL_ID inner join tb_sva_fclt_bsc facility on mapping.FCLT_MNG_NO = facility.FCLT_MNG_NO union all select user.MBER_ID as userId, facility.FCLT_NM  as facilityName from tb_cmn_user_fclt_mpng_dsctn mapping inner join comtngnrlmber user on mapping.USER_UNQ_ID = user.ESNTL_ID inner join tb_sva_fclt_bsc facility on mapping.FCLT_MNG_NO = facility.FCLT_MNG_NO", nativeQuery = true)
  List<Map<String, String>> findAllUsersWithFacilityDtos();

  @Query(value = "select user.ENTRPRS_MBER_ID as userId, facility.FCLT_NM as facilityName from tb_cmn_user_fclt_mpng_dsctn mapping inner join comtnentrprsmber user on mapping.USER_UNQ_ID = user.ESNTL_ID inner join tb_sva_fclt_bsc facility on mapping.FCLT_MNG_NO = facility.FCLT_MNG_NO where facility.FCLT_NM = :facilityName union all select user.MBER_ID as userId, facility.FCLT_NM  as facilityName from tb_cmn_user_fclt_mpng_dsctn mapping inner join comtngnrlmber user on mapping.USER_UNQ_ID = user.ESNTL_ID inner join tb_sva_fclt_bsc facility on mapping.FCLT_MNG_NO = facility.FCLT_MNG_NO where facility.FCLT_NM = :facilityName", nativeQuery = true)
  List<Map<String, String>> findUsersWithFacilityDtosByFacilityName(@Param("facilityName") String facilityName);
}
