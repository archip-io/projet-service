package com.archipio.projectservice.persistence.entity.core;

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
@Table(name = "tags")
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String tag;

  @ManyToMany(mappedBy = "tags")
  private Set<Project> projects = new LinkedHashSet<>();
}
