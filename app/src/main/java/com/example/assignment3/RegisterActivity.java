package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    TextView btn_loginpage;
    EditText edttxt_fullName;
    EditText edttxt_registeremail;
    EditText edttxt_registerpassword;
    EditText edttxt_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SetUpViews();
        switchToLogInPage();
        PostData();
    }



    private void SetUpViews() {
        btn_loginpage = findViewById(R.id.btnLoginPage);
        edttxt_fullName = findViewById(R.id.edttxt_fullName);
        edttxt_registeremail = findViewById(R.id.edttxt_registereEmail);
        edttxt_registerpassword = findViewById(R.id.edttxt_registerPassword);
        edttxt_username = findViewById(R.id.edttxt_username);
    }

    private void switchToLogInPage() {

        btn_loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void displayLoader() {
        ProgressDialog pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void PostData() {

    }

    public void btn_SignUp(View view) {
    }
}