package com.crud.task.tasks.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String title;

    @Column(name = "description")
    private String content;

    public Task(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Task(Long id, String title, String content) {
    }

    public Task() {

    }
}