package com.example.project_management_app;

import org.junit.Test;
import static org.junit.Assert.*;



public class isManagerTest {
    @Test
    public void testIsManager(){
        Profile p = new Profile();
        p.setManager(true);
        assertEquals(true, p.isManager());
    }
}
