package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CourseInfo extends AppCompatActivity {

    EditText cName , cCode , cTeacher , cField , cFees ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_info);
        SetUpViews();
    }



    private void SetUpViews() {
        cName = findViewById(R.id.cName);
        cCode = findViewById(R.id.cCode);
        cTeacher = findViewById(R.id.cTeacher);
        cFees = findViewById(R.id.cFees);
        cField = findViewById(R.id.cField);
    }




    public void back_OnClick(View view) {
        finish();
    }

    public void update_OnClick(View view) {
    }

    public void Delete_OnClick(View view) {
    }
}