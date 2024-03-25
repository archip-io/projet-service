package com.archipio.projectservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class LikedProjectId implements Serializable {

  private String username;

  @Column(name = "project_id")
  private Long projectId;
}
