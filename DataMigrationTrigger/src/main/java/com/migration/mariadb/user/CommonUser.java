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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_CMN_USER_BSC")
public class CommonUser {
	@Id
	@Column(name = "USER_ID", nullable = false)
	private String userJoinId; // ESNTL_ID
	
	@Column(name = "USER_TYPE_CD", nullable = false)
	private String userTypeCode; // 01: 시청관리자, 02: 시설관리자, 03: 일반사용자
	
	@Column(name = "ETELNO", nullable = false)
	private String phone;

	// @Column(name = "BRDT", nullable = false)
	// private String birth;
}
