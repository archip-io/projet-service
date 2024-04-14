package com.archipio.projectservice.mapper;

import com.archipio.projectservice.dto.CreateProjectDto;
import com.archipio.projectservice.dto.ProjectOutputDto;
import com.archipio.projectservice.dto.UpdateProjectDto;
import com.archipio.projectservice.persistence.entity.core.Project;
import com.archipio.projectservice.persistence.entity.core.Tag;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {

  @Named("convertTagsToStrings")
  static Set<String> convertTagsToString(@NonNull Set<Tag> tags) {
    return tags.stream().map(Tag::getTag).collect(Collectors.toSet());
  }

  @Mapping(target = "tags", ignore = true)
  Project toEntity(CreateProjectDto createProjectDto);

  @InheritConfiguration(name = "toEntity")
  @Mapping(target = "tags", ignore = true)
  void partialUpdate(UpdateProjectDto updateProjectDto, @MappingTarget Project project);

  @Mapping(target = "tags", source = "tags", qualifiedByName = "convertTagsToStrings")
  ProjectOutputDto toOutputDto(Project template);
}
