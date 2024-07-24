package com.migration.mariadb.facility.compositeid;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FacilityUserMappingIds implements Serializable {
  private String userJoinId;
  private String facilityId;
}