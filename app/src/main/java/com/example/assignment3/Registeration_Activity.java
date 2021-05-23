package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Registeration_Activity extends AppCompatActivity {

    Button save ;
    EditText fullName , emailAddress , pass , phone , birth , commentsText ;
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
        pass = findViewById(R.id.password);
        phone = findViewById(R.id.phoneNumber);
        birth = findViewById(R.id.birthDate);
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


    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String fullname = fullName.getText().toString();
        String email = emailAddress.getText().toString();
        String password = pass.getText().toString();
        String gender = findgender();
        String birthDate = birth.getText().toString();
        String phoneNumber = phone.getText().toString();

        String data = URLEncoder.encode("fullname", "UTF-8")
                + "=" + URLEncoder.encode(fullname, "UTF-8");

        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(email, "UTF-8");

        data += "&" + URLEncoder.encode("password", "UTF-8")
                + "=" + URLEncoder.encode(password, "UTF-8");

        data += "&" + URLEncoder.encode("gender", "UTF-8")
                + "=" + URLEncoder.encode(gender, "UTF-8");

        data += "&" + URLEncoder.encode("birthDate", "UTF-8")
                + "=" + URLEncoder.encode(birthDate, "UTF-8");

        data += "&" + URLEncoder.encode("phoneNumber", "UTF-8")
                + "=" + URLEncoder.encode(phoneNumber, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        // Show response on activity
        return text;



    }


    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(Registeration_Activity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddOnClick(View view) {
        String restUrl = "http://192.168.68.107/education_center/registerstudent.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            Registeration_Activity.SendPostRequest runner = new Registeration_Activity.SendPostRequest();
            runner.execute(restUrl);
        }
    }
}