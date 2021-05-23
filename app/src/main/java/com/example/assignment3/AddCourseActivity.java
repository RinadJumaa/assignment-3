package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class AddCourseActivity extends AppCompatActivity {

    EditText CourseName, CourseCode, CourseFees, CourseDuration, CourseTeacher, CourseField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setUpViews();
    }

    private void setUpViews() {
        CourseCode = findViewById(R.id.edtxtCourseCode);
        CourseName = findViewById(R.id.edtxtCourseName);
        CourseFees = findViewById(R.id.edtxtfees);
        CourseDuration = findViewById(R.id.edttxtduration);
        CourseTeacher = findViewById(R.id.edttxtTeacherName);
        CourseField = findViewById(R.id.edttxtfield);


    }

    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String Name = CourseName.getText().toString();
        String Code = CourseCode.getText().toString();
        String Fees = CourseFees.getText().toString();
        String Duration = CourseDuration.getText().toString();
        String Teacher = CourseTeacher.getText().toString();
        String Field = CourseField.getText().toString();

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

            Toast.makeText(AddCourseActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnAddOnClick(View view) {
        String restUrl = "http://192.168.68.107/education_center/addcourse.php";
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

}