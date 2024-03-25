package com.archipio.projectservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ProjectsTagId implements Serializable {

    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "project_id")
    private Long projectId;
}