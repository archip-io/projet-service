package com.archipio.projectservice.persistence.entity;

import com.archipio.projectservice.persistence.entity.core.Project;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "liked_projects")
public class LikedProject {
    @EmbeddedId
    private LikedProjectId id;

    @MapsId("projectId")
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}