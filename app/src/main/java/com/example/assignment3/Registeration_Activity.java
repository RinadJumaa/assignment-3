package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Registeration_Activity extends AppCompatActivity {

    Button save ;
    EditText fullName , emailAddress , password , phoneNumber , birthDate , commentsText ;
    CheckBox checkMale , checkFemale , checkNotToSay ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_);
        setViews();

    }

    private void setViews() {
        fullName = findViewById(R.id.fullName);
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phoneNumber);
        birthDate = findViewById(R.id.birthDate);
        checkFemale = findViewById(R.id.checkFemale);
        checkMale = findViewById(R.id.checkMale);
        checkNotToSay = findViewById(R.id.checkNotToSay);
        save =  findViewById(R.id.save);
    }

    private String findgender() {
        String g ;
        if (checkMale.isChecked()){
            g = "Male";
        }else if (checkFemale.isChecked()){
            g = "Female";
        }else if (checkNotToSay.isChecked()){
            g = "You Dont Prefer to Say Your Gender";
        }else{
            g = "";
        }
        return g ;
    }
}