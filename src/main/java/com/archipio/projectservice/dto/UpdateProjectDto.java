package com.archipio.projectservice.dto;

import static com.archipio.projectservice.util.ValidationUtils.MAX_PROJECT_NAME_LENGTH;
import static com.archipio.projectservice.util.ValidationUtils.MIN_PROJECT_NAME_LENGTH;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/** DTO for {@link com.archipio.projectservice.persistence.entity.core.Project} */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProjectDto {
  @JsonProperty("project_name")
  @NotNull(message = "{validation.projectname.not-null}")
  @Length(
      min = MIN_PROJECT_NAME_LENGTH,
      max = MAX_PROJECT_NAME_LENGTH,
      message = "{validation.projectname.length}")
  private String projectName;

  private String description;

  @JsonProperty("is_private")
  private Boolean isPrivate = false;

  private Set<String> tags = new HashSet<>();
}
