package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class CourseInfo extends AppCompatActivity {

    EditText cName , cTeacher , cField , cFees , cDuration ;
    TextView cCode ;
    CheckBox myCheck ;
    String courseinfo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_info);
        SetUpViews();

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        courseinfo = info;
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();

        displayData();

    }

    private void displayData() {

        String [] str = courseinfo.split("<>");
        String [] course = str[0].split(",");
        cName.setText(course[0]);
        cCode.setText(course[1]);
        cFees.setText(course[2]);
        cDuration.setText(course[3]);
        cTeacher.setText(course[4]);
        cField.setText(course[5]);

    }


    private void SetUpViews() {
        cName = findViewById(R.id.cName);
        myCheck = findViewById(R.id.myCheck);
        cCode = findViewById(R.id.cCode);
        cTeacher = findViewById(R.id.cTeacher);
        cFees = findViewById(R.id.cFees);
        cDuration = findViewById(R.id.cDuration);
        cField = findViewById(R.id.cField);
    }




    public void back_OnClick(View view) {
        finish();
    }

    public void update_OnClick(View view) {

        String restUrl = "http://192.168.0.100/education_center/update.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            SendPostRequest runner = new SendPostRequest();
            runner.execute(restUrl);
        }

    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String Name = cName.getText().toString();
        String Code = cCode.getText().toString();
        String Fees = cFees.getText().toString();
        String Teacher = cTeacher.getText().toString();
        String Field = cField.getText().toString();
        String Duration = cDuration.getText().toString();

        String data = URLEncoder.encode("Name", "UTF-8")
                + "=" + URLEncoder.encode(Name, "UTF-8");

        data += "&" + URLEncoder.encode("Code", "UTF-8") + "="
                + URLEncoder.encode(Code, "UTF-8");

        data += "&" + URLEncoder.encode("Fees", "UTF-8")
                + "=" + URLEncoder.encode(Fees, "UTF-8");

        data += "&" + URLEncoder.encode("Duration", "UTF-8")
                + "=" + URLEncoder.encode(Duration, "UTF-8");

        data += "&" + URLEncoder.encode("Teacher", "UTF-8")
                + "=" + URLEncoder.encode(Teacher, "UTF-8");

        data += "&" + URLEncoder.encode("Field", "UTF-8")
                + "=" + URLEncoder.encode(Field, "UTF-8");

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

            Toast.makeText(CourseInfo.this, result, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void Delete_OnClick(View view) {
        if(myCheck.isChecked() == true ){

            String restUrl = "http://192.168.0.100/education_center/delete.php";
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        123);

            } else{
                SendPostRequest runner = new SendPostRequest();
                runner.execute(restUrl);
            }

        }else{
            Toast.makeText(CourseInfo.this, "Confirm First", Toast.LENGTH_SHORT).show();
        }
    }
}