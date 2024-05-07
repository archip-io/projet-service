package com.archipio.projectservice.persistence.repository;

import com.archipio.projectservice.persistence.entity.core.Project;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends SpecPageSortRepository<Project, Long> {
  boolean existsByOwnerUsernameAndProjectName(String ownerUsername, String projectName);

  Optional<Project> findByOwnerUsernameAndProjectName(String ownerUsername, String projectName);
}
