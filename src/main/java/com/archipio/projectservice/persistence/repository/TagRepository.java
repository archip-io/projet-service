package com.archipio.projectservice.persistence.repository;

import com.archipio.projectservice.persistence.entity.core.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
  Optional<Tag> findByTag(String tag);
}
