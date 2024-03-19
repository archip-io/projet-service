package com.archipio.projectservice.persistence.entity.core;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tree")
public class Node {
    @Id
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