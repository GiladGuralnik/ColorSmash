package com.example.colorsmash;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

public class DataBaseUnitTest extends junit.framework.TestCase {


    @Test
    public void test(){
        DatabaseReference mRef;
        FirebaseDatabase mDataBase;
        mDataBase=FirebaseDatabase.getInstance();
        mRef = mDataBase.getReference("Admins");
        assertNotNull(mRef);

    }

}
