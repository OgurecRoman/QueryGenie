package com.example.querygenie.data.model;

import java.io.Serializable;
import java.util.Date;

public class QueryModel implements Serializable {
    private final int id;
    private String role;
    private String goal;
    private String environment;
    private String query;
    private String answer;
    private String date;
    private int count;
    private boolean isliked;

    public QueryModel(int id, String role, String goal, String environment, String query,
                      String date, String answer, int count, boolean isliked) {
        this.id = id;
        this.role = role;
        this.goal = goal;
        this.environment = environment;
        this.query = query;
        this.answer = answer;
        this.date = date;
        this.count = count;
        this.isliked = isliked;
    }

    public int getId() {
        return id;
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

    public String getQuery() {
        return query;
    }

    public String getAnswer() {
        return answer;
    }

    public String getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }

    public boolean getIsliked() {
        return isliked;
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

    public void setQuery(String query) {
        this.query = query;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }
}
