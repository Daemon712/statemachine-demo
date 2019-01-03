package com.github.daemon712.statemachinedemo;

import lombok.Data;
import ru.sberned.statemachine.state.HasStateAndId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Project implements HasStateAndId<Integer, ProjectState> {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private ProjectState state;
}
