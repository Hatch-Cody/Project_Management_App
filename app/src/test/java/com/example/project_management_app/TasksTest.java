package com.example.project_management_app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TasksTest {
    @Test
    public void SetTaskNameTest(){
        Task t = new Task();
        t.setTaskName("null");

        assertEquals("null", t.getTaskName());
    }
}
