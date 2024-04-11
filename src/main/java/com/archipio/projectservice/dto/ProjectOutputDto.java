package com.archipio.projectservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for {@link com.archipio.projectservice.persistence.entity.core.Project} */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectOutputDto implements Serializable {
  @JsonProperty("owner_username")
  private String ownerUsername;

  @JsonProperty("project_name")
  private String projectName;

  private String description;

  @JsonProperty("is_private")
  private Boolean isPrivate;

  private Set<String> tags;
}
