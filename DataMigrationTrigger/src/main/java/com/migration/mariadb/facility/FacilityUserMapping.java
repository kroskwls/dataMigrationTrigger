package com.migration.mariadb.facility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.migration.mariadb.facility.compositeid.FacilityUserMappingIds;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(FacilityUserMappingIds.class)
@Table(name = "TB_CMN_USER_FCLT_MPNG_DSCTN")
public class FacilityUserMapping {
  @Id
  @Column(name = "USER_UNQ_ID")
  private String userJoinId;

  @Id
  @Column(name = "FCLT_MNG_NO")
  private String facilityId;
}