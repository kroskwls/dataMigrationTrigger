package com.migration.mariadb.facility.dto;

import java.util.List;

import com.migration.mariadb.user.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityWithUserDto {
  private String name;
  private String type;
  private List<UserDto> users;
}
