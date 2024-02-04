package com.danielks.Tasks.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tb_tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String header;
    private String body;
    private boolean ended;

    public Task() {
    }

    public Task(Long id, String header, String body, boolean ended) {
        this.id = id;
        this.header = header;
        this.body = body;
        this.ended = ended;
    }

    public Long getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
