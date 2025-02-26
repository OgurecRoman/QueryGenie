package com.example.querygenie.domain.query;

import android.content.Context;
import android.content.Intent;

import com.example.querygenie.data.DBPattern;
import com.example.querygenie.data.model.PatternModel;
import com.example.querygenie.domain.queryService.QueryService;

import java.util.Objects;

public class Query {
    private String namePattern = "";
    private String role = "";
    private String goal = "";
    private String environment = "";
    private String textQuery = "";
    private final DBPattern dbPattern;

    public Query(Context context) {
        dbPattern = new DBPattern(context);
    }

    private String patternText() {
        String text = "";
        if (!Objects.equals(role, "")) {
            text += "Роль: " + role;
        }
        if (!Objects.equals(goal, "")) {
            text += "\nЦель: " + goal;
        }
        if (!Objects.equals(environment, "")) {
            text += "\nОкружение: " + environment;
        }
        return text;
    }

    private String makeQueryText() {
        return patternText() + "\n" + textQuery;
    }

    public void sendQuery(Context context) {
        Intent intent = new Intent(context, QueryService.class);
        intent.putExtra("query", makeQueryText());
        context.startService(intent);
    }

    public void addPattern() {
        dbPattern.insert(namePattern, role, goal, environment, false);
    }

    public void installPattern(int id) {
        PatternModel pattern = dbPattern.select(id);
        setRole(pattern.getRole());
        setGoal(pattern.getGoal());
        setEnvironment(pattern.getEnvironment());
    }

    public void updatePattern(int id) {
        PatternModel pattern = dbPattern.select(id);
        pattern.setRole(role);
        pattern.setGoal(goal);
        pattern.setEnvironment(environment);
        dbPattern.update(pattern);
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
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

    public void setTextQuery(String textQuery) {
        this.textQuery = textQuery;
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

    public String getTextQuery() {
        return textQuery;
    }
}
