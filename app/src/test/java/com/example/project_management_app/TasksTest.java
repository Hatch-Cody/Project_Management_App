package com.example.project_management_app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TasksTest {
    @Test
    public void SetTaskNameTest(){
        TaskItems t = new TaskItems();
        t.setTaskName("null");

        assertEquals("null", t.getTaskName());
    }
}
