package com.example.project_management_app;

import java.util.Queue;
import java.util.Vector;

public class Profile {
    private String name;
    private String userName;
    private String password;
    private Boolean isActive;
    private Boolean isManager;
    private String EmployeeId;
    private String bio;
    private String phoneNumber;
    private String email;
    private String position;
    private Queue<Integer> hours;
    private Boolean isLoggedIn;
    private double userId;
    private Vector<Tasks> t;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String comparePassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Queue<Integer> getHours() {
        return hours;
    }

    public void setHours(Queue<Integer> hours) {
        this.hours = hours;
    }

    public Boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public double getUserId() {
        return userId;
    }

    public void setUserId(double userId) {
        this.userId = userId;
    }

    public Vector<Tasks> getTaks() {
        return t;
    }

    public void setTasks(Vector<Tasks> t) {
        this.t = t;
    }
}