package com.example.querygenie.domain.query;

import android.content.Context;
import android.content.Intent;

import com.example.querygenie.data.DBHelper;
import com.example.querygenie.data.model.PatternModel;
import com.example.querygenie.data.model.QueryModel;
import com.example.querygenie.domain.queryService.QueryService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;

public class ManageQuery {
    private String namePattern = "";
    private String role = "";
    private String goal = "";
    private String environment = "";
    private String textQuery = "";
    private String textAnswer = "";
    private LocalDateTime date;
    private final DBHelper dataBase;

    public ManageQuery(Context context) {
        dataBase = new DBHelper(context);
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
        dataBase.insertPattern(namePattern, role, goal, environment, false);
    }

    public void installPattern(int id) {
        PatternModel pattern = dataBase.selectPattern(id);
        setRole(pattern.getRole());
        setGoal(pattern.getGoal());
        setEnvironment(pattern.getEnvironment());
    }

    public void updatePattern(int id) {
        PatternModel pattern = dataBase.selectPattern(id);
        pattern.setRole(role);
        pattern.setGoal(goal);
        pattern.setEnvironment(environment);
        dataBase.updatePattern(pattern);
    }

    public void addQuery() {
        date = LocalDateTime.now();
        DateTimeFormatter customFormatter = new DateTimeFormatterBuilder()
                .appendPattern("dd/MM/yyyy HH:mm:ss")
                .toFormatter();
        dataBase.insertQuery(role, goal, environment, textQuery, textAnswer,
                date.format(customFormatter), 1, false);
    }

    public void selectQuery(int id){
        QueryModel queryModel = dataBase.selectQuery(id);
        this.role = queryModel.getRole();
        this.goal = queryModel.getGoal();
        this.environment = queryModel.getEnvironment();
        this.textQuery = queryModel.getQuery();
        this.textAnswer = queryModel.getAnswer();
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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

    public String getTextAnswer() {
        return textAnswer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getTextQuery() {
        return textQuery;
    }
}
