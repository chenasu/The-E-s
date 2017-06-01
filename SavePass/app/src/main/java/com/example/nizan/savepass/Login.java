package com.example.nizan.savepass;

import android.content.Intent;
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

import static java.lang.Thread.sleep;

public class Login extends AppCompatActivity {

    private String JSON_STRING;
    public static String text = null;
    private String text1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText loginEmail = (EditText)findViewById(R.id.loginEmail);
        loginEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(loginEmail.getText().toString().equals("Email")) {
                    loginEmail.setText("");
                }
                return false;
            }
        });

        final EditText loginPW = (EditText) findViewById(R.id.LoginPW);
        loginPW.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(loginPW.getText().toString().equals("Password")) {
                    loginPW.setText("");
                }
                return false;
            }
        });

        Button enterBtn = (Button) findViewById(R.id.enterBtn);

        assert enterBtn != null;
        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text = loginEmail.getText().toString();
                text1 = loginPW.getText().toString();
                getJSON(v);
            }
        });

        Button registerBtn = (Button) findViewById(R.id.registerBtn);

        assert  registerBtn != null;
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }


    public void getJSON(View view)
    {
        new BackgroundTask().execute();

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

                String urlParameters = ("SavePass&login&email=" + text + "&password=" + text1);

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
            //TextView textView = (TextView)findViewById(R.id.textView);
            try {
                JSONObject obj = new JSONObject(result);
                msg = obj.getString("msg");
                //textView.setText(msg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //textView.setText(msg);
            if(msg.equals("Logged in successfully"))
            {
                startActivity(new Intent(getApplicationContext(), MainScreen.class));
            }
        }

    }

    public String getText()
    {
        return text;
    }
}
