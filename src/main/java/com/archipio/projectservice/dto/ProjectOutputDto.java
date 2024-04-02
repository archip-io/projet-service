package com.archipio.projectservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Set;
import lombok.*;

/** DTO for {@link com.archipio.projectservice.persistence.entity.core.Project} */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectOutputDto implements Serializable {
  @JsonProperty("owner_username")
  String ownerUsername;

  @JsonProperty("project_name")
  String projectName;

  String description;

  @JsonProperty("is_private")
  Boolean isPrivate;

  Set<String> tags;
}
