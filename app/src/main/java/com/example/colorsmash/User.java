package com.example.colorsmash;

import java.util.ArrayList;

public class User {
    private String username;
    private String name;
    private int age;
    private ArrayList<String> scores;
    private ArrayList<String> badColors;

    public User() {
    }

    public User(String username, String name, int age, String gender, ArrayList<Integer> scores, ArrayList<String> badColors) {
        this.scores = (ArrayList<String>) scores.clone();
        badColors = (ArrayList<String>) badColors.clone();
        this.username = username;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;
}
