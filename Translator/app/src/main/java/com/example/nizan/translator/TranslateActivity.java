package com.example.nizan.translator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TranslateActivity extends AppCompatActivity {

    String JSON_STRING;
    String text = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);


        final EditText eText = (EditText)findViewById(R.id.editInp);
        final TextView displayText = (TextView)findViewById(R.id.textView);
        Button btn = (Button) findViewById(R.id.button);

        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assert eText != null;
                text = eText.getText().toString();
                text = text.replaceAll("\\s","+");
                getJSON(v);
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
            json_url = "http://52.59.203.23/index.php?translate&targetLang=en&text="+text;
        }

        @Override
        protected String doInBackground(Void... voids){
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(JSON_STRING);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

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
            String Translated = null;
            TextView textView = (TextView)findViewById(R.id.textView);
            try {
                JSONObject obj = new JSONObject(result);
                Translated = obj.getString("Translated");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            textView.setText(Translated);
        }

    }
}
