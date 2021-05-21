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
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";

    private String username;
    private String password;
    private ProgressDialog pDialog;
    private String login_url = "http://192.168.68.107/education_center/login.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionHandler(getApplicationContext());
        if (session.isLoggedIn()) {
            loadsecondActivity();
        }

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
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
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

    /**
     * Launch Dashboard Activity on Successful Login
     */
    public void loadsecondActivity() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
   /* private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(i);
        finish();

    }*/

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void login() {
        displayLoader();
        JSONObject request = new JSONObject();
        closeKeyboard();

        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {
                                session.loginUser(username, response.getString(KEY_FULL_NAME));
                                loadsecondActivity();

                            } else {
                                Toast.makeText(getApplicationContext(), response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     *
     * @return
     */
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(username)) {
            edttxt_email.setError("Email cannot be empty");
            edttxt_email.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            edttxt_email.setError("Please Enter Valid email address");
            edttxt_email.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            edttxt_password.setError("Password cannot be empty");
            edttxt_password.requestFocus();

       /* } else {
           // Toast.makeText(getApplicationContext(), "Invalid username or password ", Toast.LENGTH_LONG).show();
             emptyInputEditText();*/
        }
        return true;
    }


   /* private void emptyInputEditText() {
        etUsername.setText(null);
        etPassword.setText(null);
    }*/

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager i = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            i.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}