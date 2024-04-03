package com.archipio.projectservice.persistence.entity.core;

import com.archipio.projectservice.persistence.entity.LikedProject;
import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "owner_username")
  private String ownerUsername;

  @Column(name = "project_name")
  private String projectName;

  private String description;

  @Column(name = "is_private")
  private Boolean isPrivate = false;

  @OneToMany(mappedBy = "project")
  private Set<LikedProject> likedProjects = new LinkedHashSet<>();

  @ManyToMany
  @JoinTable(
      name = "project_tree",
      joinColumns = @JoinColumn(name = "project_id"),
      inverseJoinColumns = @JoinColumn(name = "tree_id"))
  private Set<Node> nodes = new LinkedHashSet<>();

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "projects_tags",
      joinColumns = @JoinColumn(name = "project_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new LinkedHashSet<>();
}
