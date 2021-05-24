package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import classes.Course;

public class HomePage extends AppCompatActivity {

    ListView lst;
    EditText search;
    String student = "";
    TextView name, email,yourbirth,phonenumber,gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setupViews();



        Intent intent = getIntent();
        String info = intent.getStringExtra("student");
        student = info;

        DisplayData();

        String url = "http://192.168.0.100/education_center/courses.php";
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

    private void setupViews() {

        search = findViewById(R.id.edttxtsearch);
        lst = findViewById(R.id.lst);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        yourbirth = findViewById(R.id.yourbirth);
        phonenumber =findViewById(R.id.phonenumber);
        gender = findViewById(R.id.gender);
    }

    private void DisplayData() {
        String [] str = student.split(",");
        name.setText(str[0]);
        email.setText(str[1]);
        yourbirth.setText(str[3]);
        phonenumber.setText(str[4]);
        gender.setText(str[2]);

    }

    public void switchaddCourse(View view) {
        Intent intent = new Intent(HomePage.this, AddCourseActivity.class);
        startActivity(intent);
    }

    private InputStream OpenHttpConnection(String urlString) throws IOException {
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

    private String DownloadText(String URL) {
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
            while ((charRead = isr.read(inputBuffer)) > 0) {
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

    public void switchSearch(View view) {
        String url = "http://192.168.0.100/education_center/search.php?code=" + search.getText();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else {
            DownloadTextTasksearch runner = new DownloadTextTasksearch();
            runner.execute(url);

        }




    }

    private class DownloadTextTasksearch extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result){

            Intent intent = new Intent(HomePage.this, CourseInfo.class);
            intent.putExtra("info", result);
            startActivity(intent);

        }
    }


    private class DownloadTextTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            String[] course = result.split("<>");
            Course[] courses = new Course[course.length];
            String[] stringArray = new String[courses.length];


            for (int i = 0; i < course.length; i++) {
                String fields = course[i];
                String[] str = fields.split(",");

                stringArray[i] = "Course " +(i+1)+ "\n" +"Name: "  + str[0] +"\n" +
                        "Code: " + str[1]  +"\n"+
                        "Teacher: " +str[4] + "\n" +
                        "Field: "+str[5]+ "\n" +
                        "Duration: " + str[3] + "\n" +
                        "Fees: " + str[2];

                courses[i] = new Course(str[0], str[1], str[4],
                        str[5], Integer.parseInt(str[3]), Integer.parseInt(str[2]));
            }


            for(int i = 0; i < stringArray.length; i++){

            }
             ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1,
                        stringArray);
            lst.setAdapter(itemsAdapter);



        }
    }


    }
