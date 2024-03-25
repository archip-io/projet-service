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
public class LikedProjectId implements Serializable {

    private String username;

    @Column(name = "project_id")
    private Long projectId;

}