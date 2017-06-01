package com.example.nizan.savepass;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends AppCompatActivity {

    String JSON_STRING;
    String username;
    String password;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText regUN = (EditText) findViewById((R.id.regUnEditText));
        regUN.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(regUN.getText().toString().equals("Username")) {
                    regUN.setText("");
                }
                return false;
            }
        });

        final EditText regPW = (EditText) findViewById((R.id.regPwEditText));
        regPW.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(regPW.getText().toString().equals("Password")) {
                    regPW.setText("");
                }
                return false;
            }
        });

        final EditText regEmail = (EditText) findViewById((R.id.regEmailEditText));
        regEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(regEmail.getText().toString().equals("Email")) {
                    regEmail.setText("");
                }
                return false;
            }
        });

        Button finishRegBtn = (Button) findViewById(R.id.finishRegBtn);
        finishRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = regUN.getText().toString();
                password = regPW.getText().toString();
                email = regEmail.getText().toString();
                getJSON(v);
            }
        });

    }

    public void getJSON(View view)
    {
        new Register.BackgroundTask().execute();

    }


    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        String json_url;
        @Override
        protected void onPreExecute(){
            json_url = "http://52.59.203.23/index.php";
        }

        @Override
        protected String doInBackground(Void... voids){
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("User-Agent", "Project-Query");
                httpURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                String urlParameters = ("SavePass&register&username=" + username+ "&password=" + password + "&email=" + email);

                httpURLConnection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());

                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = httpURLConnection.getResponseCode();
                System.out.println(responseCode);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine()) != null)
                {
                    sb.append(JSON_STRING);
                }

                bufferedReader.close();
                httpURLConnection.disconnect();

                return sb.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "NULL";
        }

        @Override
        protected void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result){
            String msg = null;
            try {
                JSONObject obj = new JSONObject(result);
                msg = obj.getString("signedup");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TextView output = (TextView) findViewById(R.id.textView6);
            if(msg == null)
            {
                output.setText("Email/Username already exists");
            }
            else if(msg.equals("true"))
            {
                output.setText("Registered successfully!");
            }
            else
                output.setText("Error");
        }

    }

}
