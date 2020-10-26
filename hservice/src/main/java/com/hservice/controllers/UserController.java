package com.hservice.controllers;


import com.hservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/project-leads")
    public ResponseEntity<?> getProjectLeads() {
        return ResponseEntity.ok(userService.findProjectLeads());
    }

    @GetMapping("/executors/{projectId}")
    public ResponseEntity<?> getUserShortDtosByProjectId(@PathVariable("projectId") Long projectId){
        return ResponseEntity.ok(userService.findUsersByProjectId(projectId));
    }
}
