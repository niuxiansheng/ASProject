package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JNIUtil jniUtil = new JNIUtil();
        System.out.println(jniUtil.getWorld());
    }

    public class Test{
        int id;
        public Test(int i){
            id = i;
        }
    }
}
