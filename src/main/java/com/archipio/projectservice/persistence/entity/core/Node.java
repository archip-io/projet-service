package com.archipio.projectservice.persistence.entity.core;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tree")
public class Node {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "file_name")
  private String fileName;

  @Column(name = "file_id")
  private String fileId;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Node parent;

  @ManyToMany(mappedBy = "nodes")
  private Set<Project> projects = new LinkedHashSet<>();

  @OneToMany(mappedBy = "parent")
  private Set<Node> nodes = new LinkedHashSet<>();
}
