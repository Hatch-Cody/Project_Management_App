package com.example.project_management_app;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeIdTest {

    @Test
    public void IdTest() {
        Profile p = new Profile();
        p.setEmployeeId("1210");
        assertEquals("1210", p.getEmployeeId());
    }

}
