package com.archipio.projectservice.controller.api.v0;

import static com.archipio.projectservice.util.ApiUtils.API_V0_PREFIX;
import static org.springframework.http.HttpStatus.OK;

import com.archipio.commonauth.UserDetailsImpl;
import com.archipio.projectservice.dto.CreateProjectDto;
import com.archipio.projectservice.dto.ProjectOutputDto;
import com.archipio.projectservice.dto.UpdateProjectDto;
import com.archipio.projectservice.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V0_PREFIX)
public class ProjectController {

  private final ProjectService projectService;

  @PreAuthorize("hasAuthority('CREATE_PROJECT')")
  @PostMapping()
  public ResponseEntity<ProjectOutputDto> createProject(
      @Valid @RequestBody CreateProjectDto createProjectDto,
      @AuthenticationPrincipal UserDetailsImpl principal) {
    return ResponseEntity.status(OK)
        .body(projectService.createProject(createProjectDto, principal.getUsername()));
  }

  @PreAuthorize("hasAuthority('UPDATE_PROJECT')")
  @PatchMapping("/{oldProjectName}")
  public ResponseEntity<ProjectOutputDto> updateProject(
      @Valid @RequestBody UpdateProjectDto updateProjectDto,
      @AuthenticationPrincipal UserDetailsImpl principal,
      @PathVariable String oldProjectName) {
    return ResponseEntity.status(OK)
        .body(
            projectService.updateProject(
                updateProjectDto, principal.getUsername(), oldProjectName));
  }
}
