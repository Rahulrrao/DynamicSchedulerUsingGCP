package com.example.namrata.dynamic_scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onRadioButtonClicked(View v){
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
        int id = rg.getCheckedRadioButtonId();
        if(id == R.id.radio_asp){
            Intent myIntent = new Intent(this, AspActivity.class);
            startActivity(myIntent);
        }
        else if(id==R.id.radio_ds){
            Intent myIntent = new Intent(this, DSActivity.class);
            startActivity(myIntent);
        }
    }
}
