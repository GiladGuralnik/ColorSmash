package com.example.colorsmash;

import android.widget.EditText;
import android.text.TextUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginActivityTest {


    @Test
    public void test(){
        int num =2;
        int out;
        int expected = 2;
        LoginActivity loginActivity = new LoginActivity();
        out=loginActivity.test(num);
        assertEquals(expected,out);

    }
    /*@Test
    public void isValidPassword(){
        boolean expected = true;
        boolean output;
        String password = "12345678";

        LoginActivity loginActivity = new LoginActivity();
        output=loginActivity.isValidPassword(password);

        assertEquals(expected,output);
    }
    @Test
    public void isValidEmail(){
        boolean expected = true;
        boolean output;
        String email = "Yoel@gmail.com";

        LoginActivity loginActivity = new LoginActivity();
        output=loginActivity.isValidEmail(email);
        assertEquals(expected,output);

    }*/
    /*@Test
    public void onCreate() {


        LoginActivity loginActivity = new LoginActivity();


        assertEquals(true,loginActivity.isValidPassword(email));

    }*/


    //@Test
    //public void onClick() {
   // }
}