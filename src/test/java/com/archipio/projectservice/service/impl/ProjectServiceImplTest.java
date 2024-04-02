package com.archipio.projectservice.service.impl;

import com.archipio.projectservice.dto.CreateProjectDto;
import com.archipio.projectservice.dto.ProjectOutputDto;
import com.archipio.projectservice.exception.ProjectNameAlreadyExistException;
import com.archipio.projectservice.mapper.ProjectMapper;
import com.archipio.projectservice.persistence.entity.core.Project;
import com.archipio.projectservice.persistence.entity.core.Tag;
import com.archipio.projectservice.persistence.repository.ProjectRepository;
import com.archipio.projectservice.persistence.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static org.mockito.quality.Strictness.LENIENT;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class ProjectServiceImplTest {

  @InjectMocks private ProjectServiceImpl projectService;

  @Mock private ProjectRepository projectRepository;

  @Mock private ProjectMapper projectMapper;

  @Mock private TagRepository tagRepository;

  @Test
  void createProject_thenTagNotExist_whenCreateProjectAndCreateTag() {
    // prepare
    final var ownerUsername = "yana";
    final var projectName = "pisyapopa";
    final var description = "The pisya is not popa";
    final var isPrivate = true;
    final var tagName = "tagName";
    final var tags = new HashSet<>(List.of(tagName));

    CreateProjectDto createProjectDto =
        new CreateProjectDto(projectName, description, isPrivate, tags);
    ProjectOutputDto projectOutputDtoWait =
        new ProjectOutputDto(ownerUsername, projectName, description, isPrivate, tags);

    Project project = new Project();
    Tag tag = new Tag();
    tag.setTag(tagName);

    when(projectMapper.toEntity(createProjectDto)).thenReturn(project);
    when(projectMapper.toOutputDto(project)).thenReturn(projectOutputDtoWait);
    when(tagRepository.findByTag(tagName)).thenReturn(Optional.empty());
    when(tagRepository.save(any(Tag.class))).thenReturn(tag);

    // do
    ProjectOutputDto projectOutputDto =
        projectService.createProject(createProjectDto, ownerUsername);

    // check
    assertThat(projectOutputDto).isEqualTo(projectOutputDtoWait);
    verify(projectRepository, times(1)).save(project);
    verify(tagRepository, times(1)).save(any(Tag.class));
  }

  @Test
  void createProject_thenTagExist_whenCreateProjectAndNotCreateTag() {
    // prepare
    final var ownerUsername = "yana";
    final var projectName = "pisyapopa";
    final var description = "The pisya is not popa";
    final var isPrivate = true;
    final var tagName = "tagName";
    final var tags = new HashSet<>(List.of(tagName));

    CreateProjectDto createProjectDto =
        new CreateProjectDto(projectName, description, isPrivate, tags);
    ProjectOutputDto projectOutputDtoWait =
        new ProjectOutputDto(ownerUsername, projectName, description, isPrivate, tags);

    Project project = new Project();
    Tag tag = new Tag();
    tag.setTag(tagName);

    when(projectMapper.toEntity(createProjectDto)).thenReturn(project);
    when(projectMapper.toOutputDto(project)).thenReturn(projectOutputDtoWait);
    when(tagRepository.findByTag(tagName)).thenReturn(Optional.of(tag));

    // do
    ProjectOutputDto projectOutputDto =
        projectService.createProject(createProjectDto, ownerUsername);

    // check
    assertThat(projectOutputDto).isEqualTo(projectOutputDtoWait);
    verify(projectRepository, times(1)).save(project);
    verify(tagRepository, times(0)).save(any(Tag.class));
  }

  @Test
  void createProject_thenProjectNameExist_thenThrownProjectNameAlreadyExistException() {
    // prepare
    final var ownerUsername = "yana";
    final var projectName = "pisyapopa";
    final var description = "The pisya is not popa";
    final var isPrivate = true;
    final var tags = new HashSet<>(Arrays.asList("a", "b", "c"));

    CreateProjectDto createProjectDto =
        new CreateProjectDto(projectName, description, isPrivate, tags);

    when(projectRepository.findByOwnerUsernameAndProjectName(
            ownerUsername, createProjectDto.getProjectName()))
        .thenThrow(new ProjectNameAlreadyExistException());

    // check
    assertThatExceptionOfType(ProjectNameAlreadyExistException.class)
        .isThrownBy(() -> projectService.createProject(createProjectDto, ownerUsername));
  }
}
