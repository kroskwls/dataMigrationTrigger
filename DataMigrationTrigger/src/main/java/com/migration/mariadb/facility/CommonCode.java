package com.migration.mariadb.facility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.migration.mariadb.facility.compositeid.CommonCodeIds;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CommonCodeIds.class)
@Table(name = "COMTCCMMNDETAILCODE")
public class CommonCode {
  @Id
  @Column(name = "CODE_ID")
  private String codeId;

  @Id
  @Column(name = "CODE")
  private String code;

  @Column(name = "CODE_NM")
  private String codeName;
}