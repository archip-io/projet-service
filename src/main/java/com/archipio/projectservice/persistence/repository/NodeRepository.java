package com.archipio.projectservice.persistence.repository;

import com.archipio.projectservice.persistence.entity.core.Node;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<Node, UUID> {
  List<Node> findByParent(Node parent);
}
