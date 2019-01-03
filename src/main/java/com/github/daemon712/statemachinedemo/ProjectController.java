package com.github.daemon712.statemachinedemo;

import org.springframework.web.bind.annotation.*;
import ru.sberned.statemachine.StateMachine;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private ProjectRepository projectRepository;
    private StateMachine<Project, ProjectState, Integer> projectStateMachine;

    public ProjectController(ProjectRepository projectRepository, StateMachine<Project, ProjectState, Integer> projectStateMachine) {
        this.projectRepository = projectRepository;
        this.projectStateMachine = projectStateMachine;
    }

    @GetMapping
    List<Project> listProjects() {
        return projectRepository.findAll();
    }

    @PostMapping
    Project createProject() {
        Project project = new Project();
        project.setName("test");
        project.setState(ProjectState.INITIATED);
        return projectRepository.save(project);
    }

    @PostMapping("/{id}")
    void performEvent(@PathVariable Integer id) throws Exception {
        projectStateMachine.handleMessage(id, ProjectState.IN_DEVELOPMENT, null);
    }
}
