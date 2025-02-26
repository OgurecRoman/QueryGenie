package com.example.querygenie.data.model;

import java.io.Serializable;

public class PatternModel implements Serializable {
    private final int id;
    private String name;
    private String role;
    private String goal;
    private String environment;
    private boolean isliked;

    public PatternModel(int id, String name, String role, String goal, String environment, boolean isliked) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.goal = goal;
        this.environment = environment;
        this.isliked = isliked;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getGoal() {
        return goal;
    }

    public String getEnvironment() {
        return environment;
    }

    public boolean getIsliked() {
        return isliked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }
}
