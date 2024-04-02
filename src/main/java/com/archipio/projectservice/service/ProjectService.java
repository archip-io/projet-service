package com.archipio.projectservice.service;

import com.archipio.projectservice.dto.CreateProjectDto;
import com.archipio.projectservice.dto.ProjectOutputDto;
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
}
