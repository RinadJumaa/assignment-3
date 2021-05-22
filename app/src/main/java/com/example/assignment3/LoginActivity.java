package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    TextView btn_registerpage;
    EditText edttxt_email;
    EditText edttxt_password;
    private String login_url = "http://192.168.68.107/education_center/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SetUpViews();
        switchToSignUpPage();
    }

    private void SetUpViews() {
        btn_registerpage = findViewById(R.id.btn_registerpage);
        edttxt_email= findViewById(R.id.edttxt_email);
        edttxt_password = findViewById(R.id.edttxt_password);
    }

    private void switchToSignUpPage() {

        btn_registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Registeration_Activity.class);
                startActivity(intent);
            }
        });

    }

    public void btn_SignIn(View view) {
        String email = edttxt_email.getText().toString();
        String password = edttxt_password.getText().toString();
        if(!email.isEmpty() && !password.isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, HomePage.class);
            startActivity(intent);
        }
        else
            Toast.makeText(this, "you have to enter information", Toast.LENGTH_SHORT).show();
    }

}