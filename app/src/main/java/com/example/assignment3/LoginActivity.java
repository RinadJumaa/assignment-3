package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {

    TextView btn_registerpage;
    EditText edttxt_email;
    EditText edttxt_password;
    //private String login_url = "http://192.168.68.107/education_center/login.php";

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

        String url = "http://192.168.68.107/education_center/login.php?emailaddress=" + edttxt_email.getText() +"&password="
                +edttxt_password.getText();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else {
            DownloadTextTask runner = new DownloadTextTask();
            runner.execute(url);

        }
    }
        private InputStream OpenHttpConnection (String urlString) throws IOException
        {
            InputStream in = null;
            int response = -1;

            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            if (!(conn instanceof HttpURLConnection))
                throw new IOException("Not an HTTP connection");
            try {
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                response = httpConn.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    in = httpConn.getInputStream();
                }
            } catch (Exception ex) {
                Log.d("Networking", ex.getLocalizedMessage());
                throw new IOException("Error connecting");
            }
            return in;
        }
    private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }

        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer))>0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }
        return str;
    }

    private class DownloadTextTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {
        return DownloadText(urls[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        //String[] books = result.split(",");
        //String str = "";
        //for(String s : books){
        //    str+= s + "\n";
        // }
//        EditText edtData = findViewById(R.id.edtData);
//        edtData.setText(result);

        if(result.equals("Student found")) {

            Intent intent = new Intent(LoginActivity.this, HomePage.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(LoginActivity.this, "wrong input", Toast.LENGTH_SHORT).show();
        }
    }
}

}