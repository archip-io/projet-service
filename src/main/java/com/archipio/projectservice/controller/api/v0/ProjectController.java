package com.archipio.projectservice.controller.api.v0;

import static com.archipio.projectservice.util.ApiUtils.API_V0_PREFIX;
import static com.archipio.projectservice.util.ApiUtils.CREATE_PROJECT;
import static org.springframework.http.HttpStatus.OK;

import com.archipio.commonauth.UserDetailsImpl;
import com.archipio.projectservice.dto.CreateProjectDto;
import com.archipio.projectservice.dto.ProjectOutputDto;
import com.archipio.projectservice.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V0_PREFIX)
public class ProjectController {

  private final ProjectService projectService;

  @PreAuthorize("hasAuthority('CREATE_PROJECT')")
  @PostMapping(CREATE_PROJECT)
  public ResponseEntity<ProjectOutputDto> updateUsername(
      @Valid @RequestBody CreateProjectDto createProjectDto,
      @AuthenticationPrincipal UserDetailsImpl principal) {
    return ResponseEntity.status(OK)
        .body(projectService.createProject(createProjectDto, principal.getUsername()));
  }
}
