package com.migration.mariadb.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMTNEMPLYRINFO")
public class EgovSuperUser { // 시청관리자
	@Id
	@Column(name = "EMPLYR_ID", nullable = false)
	private String userId;

	@Column(name = "USER_NM", nullable = false)
	private String name;
	
	@Column(name = "SEXDSTN_CODE", nullable = false)
	private String gender; // M: male, F: female
	
	@Column(name = "EMAIL_ADRES", nullable = false)
	private String email;

	@Column(name = "ESNTL_ID", nullable = false, unique = true)
	private String userJoinId;

	@Column(name = "BRTHDY")
	private String birth; // YYYYMMDD
}
