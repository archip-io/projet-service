package com.archipio.projectservice.service.impl;

import com.archipio.projectservice.dto.CreateProjectDto;
import com.archipio.projectservice.dto.ProjectOutputDto;
import com.archipio.projectservice.exception.ProjectNameAlreadyExistException;
import com.archipio.projectservice.mapper.ProjectMapper;
import com.archipio.projectservice.persistence.entity.core.Project;
import com.archipio.projectservice.persistence.entity.core.Tag;
import com.archipio.projectservice.persistence.repository.ProjectRepository;
import com.archipio.projectservice.persistence.repository.TagRepository;
import com.archipio.projectservice.service.ProjectService;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final TagRepository tagRepository;

  @Transactional
  @Override
  public ProjectOutputDto createProject(
      @NonNull CreateProjectDto createProjectDto, @NonNull String ownerName) {
    if (projectRepository
        .findByOwnerUsernameAndProjectName(ownerName, createProjectDto.getProjectName())
        .isPresent()) {
      throw new ProjectNameAlreadyExistException();
    }
    Project project = projectMapper.toEntity(createProjectDto);
    project.setOwnerUsername(ownerName);

    project.setTags(convertTags(createProjectDto.getTags()));
    projectRepository.save(project);
    return projectMapper.toOutputDto(project);
  }

  private Set<Tag> convertTags(@NonNull Set<String> tags) {
    return tags.stream()
        .map(
            t ->
                tagRepository
                    .findByTag(t)
                    .orElseGet(
                        () -> {
                          Tag newTag = new Tag();
                          newTag.setTag(t);
                          return tagRepository.save(newTag);
                        }))
        .collect(Collectors.toSet());
  }
}
