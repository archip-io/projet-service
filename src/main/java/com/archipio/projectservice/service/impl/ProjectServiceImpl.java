package com.archipio.projectservice.service.impl;

import com.archipio.projectservice.dto.CreateProjectDto;
import com.archipio.projectservice.dto.ProjectOutputDto;
import com.archipio.projectservice.dto.UpdateProjectDto;
import com.archipio.projectservice.exception.NotSuchProjectException;
import com.archipio.projectservice.exception.ProjectNameAlreadyExistException;
import com.archipio.projectservice.mapper.ProjectMapper;
import com.archipio.projectservice.persistence.entity.core.Project;
import com.archipio.projectservice.persistence.entity.core.Tag;
import com.archipio.projectservice.persistence.repository.ProjectRepository;
import com.archipio.projectservice.persistence.repository.TagRepository;
import com.archipio.projectservice.service.ProjectService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.archipio.projectservice.util.SpecPageSortUtils.generatePageable;
import static com.archipio.projectservice.util.SpecPageSortUtils.generateProjectSpec;
import static com.archipio.projectservice.util.SpecPageSortUtils.generateSort;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
  private static final List<String> availableSortFields = List.of("projectNameAsc", "projectNameDesc");
  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper;
  private final TagRepository tagRepository;

  @Transactional
  @Override
  public ProjectOutputDto createProject(
      @NonNull CreateProjectDto createProjectDto, @NonNull String ownerName) {
    if (projectRepository.existsByOwnerUsernameAndProjectName(
        ownerName, createProjectDto.getProjectName())) {
      throw new ProjectNameAlreadyExistException();
    }
    Project project = projectMapper.toEntity(createProjectDto);
    project.setOwnerUsername(ownerName);

    project.setTags(convertTags(createProjectDto.getTags()));
    projectRepository.save(project);
    return projectMapper.toOutputDto(project);
  }

  @Transactional
  @Override
  public ProjectOutputDto updateProject(
      @NonNull UpdateProjectDto updateProjectDto,
      @NonNull String ownerName,
      @NonNull String oldProjectName) {

    Project projectDB =
        projectRepository
            .findByOwnerUsernameAndProjectName(ownerName, oldProjectName)
            .orElseThrow(NotSuchProjectException::new);

    if (!Objects.equals(updateProjectDto.getProjectName(), projectDB.getProjectName())
        && projectRepository.existsByOwnerUsernameAndProjectName(
            ownerName, updateProjectDto.getProjectName())) {
      throw new ProjectNameAlreadyExistException();
    }

    projectMapper.partialUpdate(updateProjectDto, projectDB);
    projectDB.setTags(convertTags(updateProjectDto.getTags()));

    projectRepository.save(projectDB);
    return projectMapper.toOutputDto(projectDB);
  }

  @Override
  public void deleteProject(@NonNull String ownerName, @NonNull String projectName) {
    Project projectDB =
        projectRepository
            .findByOwnerUsernameAndProjectName(ownerName, projectName)
            .orElseThrow(NotSuchProjectException::new);

    projectRepository.delete(projectDB);
  }

  @Override
  public List<ProjectOutputDto> getAllFiltered(
      Map<String, String> filters, List<String> sorts, int page, int pageSize) {
    var sort = generateSort(sorts, availableSortFields);
    var pageable = generatePageable(page, pageSize, sort);
    var spec = generateProjectSpec(filters);
    return projectMapper.toOutputDtoList(projectRepository.findAll(spec, pageable, sort));
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
