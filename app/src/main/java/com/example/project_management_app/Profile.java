package com.example.project_management_app;

import android.graphics.Bitmap;
import android.view.View;

import java.util.Queue;
import java.util.Vector;

public class Profile {
    private static String firstName;
    private static String lastName;
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
    private Vector<Task> t;
    private Bitmap profilePic;

    /**
     * Default constructor for the profile class shows that no user was logged in.
     */
    Profile() {
        userName = "No User Logged in";
        email = "";
    }

    public static final String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public static final String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /*
    public Boolean isManager() {

        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }
    */

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

    /*
    public Queue<Integer> getHours() {
        return hours;
    }

    public void setHours(Queue<Integer> hours) {
        this.hours = hours;
    }
    */

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

    public Vector<Task> getTasks() {
        return t;
    }

    public void setTasks(Vector<Task> t) {
        this.t = t;
    }

    public Bitmap getProfilePic() { return profilePic; }

    public void setProfilePic(Bitmap profilePic) { this.profilePic = profilePic; }

    public void makeNewTask(View v){

    }

    public void sendTask(View v){

    }
}
