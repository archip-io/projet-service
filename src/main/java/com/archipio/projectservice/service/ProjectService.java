package com.archipio.projectservice.service;

import com.archipio.projectservice.dto.CreateProjectDto;
import com.archipio.projectservice.dto.ProjectOutputDto;
import com.archipio.projectservice.dto.UpdateProjectDto;
import lombok.NonNull;

public interface ProjectService {
  /**
   * Создание проекта.
   *
   * @param createProjectDto дто проекта
   * @param ownerName ник пользователя
   * @return созданный проект
   */
  ProjectOutputDto createProject(
      @NonNull CreateProjectDto createProjectDto, @NonNull String ownerName);

  /**
   * Редактирование проекта.
   *
   * @param updateProjectDto дто проекта
   * @param ownerName ник пользователя
   * @param oldProjectName старое название проекта
   * @return обновленный проект
   */
  ProjectOutputDto updateProject(
      @NonNull UpdateProjectDto updateProjectDto,
      @NonNull String ownerName,
      @NonNull String oldProjectName);

/**
 * Удаление проекта.
 * @param ownerName ник пользователя
 * @param projectName название проекта
*/
  void deleteProject(
          @NonNull String ownerName,
          @NonNull String projectName);
}
