package com.archipio.projectservice.persistence.repository;

import com.archipio.projectservice.persistence.entity.core.Project;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  Optional<Project> findByOwnerUsernameAndProjectName(String ownerUsername, String projectName);
}
