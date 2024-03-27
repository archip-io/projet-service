package com.archipio.projectservice.persistence.entity;

import com.archipio.projectservice.persistence.entity.core.Node;
import com.archipio.projectservice.persistence.entity.core.Project;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_tree")
public class ProjectTree {
  @EmbeddedId private ProjectTreeId id;

  @MapsId("projectId")
  @OneToOne
  @JoinColumn(name = "project_id")
  private Project project;

  @MapsId("treeId")
  @ManyToOne
  @JoinColumn(name = "tree_id")
  private Node node;
}
