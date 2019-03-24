package com.example.colorsmash;

import android.view.inputmethod.EditorInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//@RunWith(MockitoJUnitRunner.class)

public class LoginActivityTest extends junit.framework.TestCase{

    LoginActivity loginActivity ;

    //@Mock
    //LoginActivity view;
    @Before
    public void  setUp(){
        loginActivity = new LoginActivity();
        //loginActivity.onCreate(null);

    }


    @Test
    public void test_correctEmailAddress() {
        assertTrue(loginActivity.isEmailValid("name@email.com"));
    }

    @Test
    public void test_incorrectEmailAddress() {
        assertFalse(loginActivity.isEmailValid("nameabcemail.com"));
    }

    @Test
    public void test_correctPasswordLength() {
        assertTrue(loginActivity.isPasswordValid("a1b2c3d4e5"));
    }

    @Test
    public void test_incorrectPasswordLength() {
        assertFalse(loginActivity.isPasswordValid("1"));
    }




}