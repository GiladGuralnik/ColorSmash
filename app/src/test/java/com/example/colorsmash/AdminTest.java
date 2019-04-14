package com.example.colorsmash;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdminTest {

    Admin admin;

    @Before
    public void pre(){

        admin = new Admin();
    }


    @Test
    public void testUsername() {
        admin.setUsername("Admin");
        String userName = "Admin";
        assertEquals(userName,admin.getUsername());
    }


    @Test
    public void testUserPassword() {
        admin.setPassword("AdminPassword");
        String password = "AdminPassword";
        assertEquals(password,admin.getPassword());
    }

}