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
@Table(name = "COMTNENTRPRSMBER")
public class EgovAdminUser { // 시설관리자
	@Id
	@Column(name = "ENTRPRS_MBER_ID", nullable = false)
	private String userId;

	@Column(name = "APPLCNT_NM", nullable = false)
	private String name;
	
	@Column(name = "GENDER", nullable = false)
	private String gender; // M: male, F: female
	
	@Column(name = "APPLCNT_EMAIL_ADRES", nullable = false)
	private String email;

	@Column(name = "ESNTL_ID", nullable = false, unique = true)
	private String userJoinId;

	@Column(name = "BIRTHDATE")
	private String birth; // YYYY-MM-DD
}
