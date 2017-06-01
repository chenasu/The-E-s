package com.example.nizan.savepass;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static com.example.nizan.savepass.Login.text;

public class Add extends AppCompatActivity {

    private String JSON_STRING;
    private String addUN;
    private String addPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText userUN = (EditText) findViewById((R.id.userUN));
        final EditText userPW = (EditText) findViewById((R.id.userPW));

        Button submit = (Button)findViewById(R.id.addBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUN = userUN.getText().toString();
                addPW = userPW.getText().toString();
                getJSON(v);
            }
        });
    }


    public void getJSON(View view)
    {
        new Add.BackgroundTask().execute();

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

                String urlParameters = ("SavePass&userAddToTable&email=" + text + "&username=" + addUN + "&password=" + addPW);

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
            TextView textView = (TextView)findViewById(R.id.addTextView);
            try {
                JSONObject obj = new JSONObject(result);
                data = obj.getString("addedToDB");
                //textView.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(data.equals("true"))
            {
                textView.setText("Added successfully");
            }
        }

    }
}
