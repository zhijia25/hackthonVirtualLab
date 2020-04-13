package com.example.virtuallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.jumpToResourceList();
    }

    /**
     * jump
     */
    private void jumpToResourceList() {
        Intent intent = new Intent(MainActivity.this, ResourceListActivity.class);
        startActivity(intent);
    }



}
