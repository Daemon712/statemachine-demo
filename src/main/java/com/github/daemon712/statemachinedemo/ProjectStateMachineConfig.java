package com.github.daemon712.statemachinedemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sberned.statemachine.StateMachine;
import ru.sberned.statemachine.StateRepository;
import ru.sberned.statemachine.lock.MapLockProvider;

import java.util.EnumSet;

@Configuration
public class ProjectStateMachineConfig {
    @Bean
    StateRepository<Project, ProjectState, Integer> projectStateRepository() {
        return StateRepository.StateRepositoryBuilder.<Project, ProjectState, Integer>configure()
                .setAvailableStates(EnumSet.allOf(ProjectState.class))
                .defineTransitions()
                .from(ProjectState.INITIATED)
                .to(ProjectState.IN_DEVELOPMENT)
                .and()
                .from(ProjectState.IN_DEVELOPMENT)
                .to(ProjectState.IN_DEVELOPMENT)
                .and()
                .from(ProjectState.IN_DEVELOPMENT)
                .to(ProjectState.IN_PRODUCTION)
                .and()
                .from(ProjectState.IN_PRODUCTION)
                .to(ProjectState.ARCHIVED)
                .build();
    }

    @Bean
    StateMachine<Project, ProjectState, Integer> projectStateMachine(
            ProjectRepository projectRepository, StateRepository<Project, ProjectState, Integer> projectStateRepository) {
        StateMachine<Project, ProjectState, Integer> stateMachine = new StateMachine<>(
                projectRepository::getOne,
                (state, project, info) -> project.setState(state),
                new MapLockProvider()
        );
        stateMachine.setStateRepository(projectStateRepository);
        return stateMachine;
    }
}
