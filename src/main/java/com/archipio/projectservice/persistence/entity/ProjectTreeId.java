package com.archipio.projectservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProjectTreeId implements Serializable {

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "tree_id")
    private UUID treeId;
}