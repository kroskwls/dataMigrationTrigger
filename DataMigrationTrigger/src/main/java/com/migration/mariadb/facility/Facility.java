package com.migration.mariadb.facility;

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
@Table(name = "TB_SVA_FCLT_BSC")
public class Facility {
  @Id
  @Column(name = "FCLT_MNG_NO", nullable = false)
  private String id;

  @Column(name = "FCLT_NM", nullable = false)
  private String name;

  @Column(name = "FCLT_TYPE_CD", nullable = false)
  private String type;
}
