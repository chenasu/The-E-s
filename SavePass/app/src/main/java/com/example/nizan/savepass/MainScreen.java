package com.example.nizan.savepass;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import static com.example.nizan.savepass.Login.text;

public class MainScreen extends AppCompatActivity {


    public String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getJSON();


        Button addBtn = (Button)findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Add.class));
            }
        });

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            // do something on back.
//            new PopupWindow();
//            return false;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }


    public void getJSON()
    {
        new MainScreen.BackgroundTask().execute();

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

                String urlParameters = ("SavePass&fetchInfo&email=" + text);

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
            String data = null;
            TextView textView = (TextView)findViewById(R.id.mainContent);
            try {
                JSONObject obj = new JSONObject(result);
                data = obj.getString("Data");
                textView.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            textView.setText(data);
        }

    }
}
