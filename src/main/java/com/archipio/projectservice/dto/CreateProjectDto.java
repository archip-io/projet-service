package com.archipio.projectservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.archipio.projectservice.util.ValidationUtils.MAX_PROJECT_NAME_LENGTH;
import static com.archipio.projectservice.util.ValidationUtils.MIN_PROJECT_NAME_LENGTH;

/** DTO for {@link com.archipio.projectservice.persistence.entity.core.Project} */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectDto implements Serializable {

  @JsonProperty("project_name")
  @NotNull(message = "{validation.projectname.not-null}")
  @Length(
      min = MIN_PROJECT_NAME_LENGTH,
      max = MAX_PROJECT_NAME_LENGTH,
      message = "{validation.projectname.length}")
  String projectName;

  String description;

  @JsonProperty("is_private")
  Boolean isPrivate = false;

  Set<String> tags = new HashSet<>();
}
