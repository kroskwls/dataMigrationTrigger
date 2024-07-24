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
@Table(name = "COMTNGNRLMBER")
public class EgovCommonUser { // 일반사용자
	@Id
	@Column(name = "MBER_ID", nullable = false)
	private String userId;

	@Column(name = "MBER_NM", nullable = false)
	private String name;
	
	@Column(name = "SEXDSTN_CODE", nullable = false)
	private String gender; // M: male, F: female
	
	@Column(name = "MBER_EMAIL_ADRES", nullable = false)
	private String email;

	@Column(name = "ESNTL_ID", nullable = false, unique = true)
	private String userJoinId;

	@Column(name = "BIRTHDATE")
	private String birth; // YYYY-MM-DD
}
