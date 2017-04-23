package a1.wheather;


import java.util.ArrayList;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RequestQueue queue;

    String url,temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> spinnerArrayAdapter;

        queue= Volley.newRequestQueue(this);
        url ="http://api.wunderground.com/api/a63fd0dd2cb6bd8b/conditions/q/CA/San_Francisco.json";
        ArrayList<String> dialogList= new ArrayList<String>();
        dialogList.add("ldkfn");
//////////////////////////////////////////////////////////////////////////////////////////////////


        Button tempButton= (Button)findViewById(R.id.web_button);
//        Spinner addCity =(Spinner)findViewById(R.id.addCity2);
//        spinnerArrayAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,dialogList);
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        addCity.setAdapter(spinnerArrayAdapter);
//
//        addCityButton.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        FloatingActionButton addCity= (FloatingActionButton)findViewById(R.id.addCity2);

        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder= new AlertDialog.Builder(MainActivity.this);
                View mView= getLayoutInflater().inflate(R.layout.search_bar,null);
                mBuilder.setView(mView);
                AlertDialog dialog= mBuilder.create();

                dialog.show();
            }
        });



        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Button tempButton= (Button)findViewById(R.id.web_button);

                                try {

                                    JSONObject obs=response.getJSONObject("current_observation");
                                    tempButton.setText(obs.getDouble("temp_c")+"º");
      //                              tempButton.setBackground( getResources().getDrawable(R.drawable.fall));
                                } catch (JSONException e) {
                                    tempButton.setText("JSON can not be parsed");
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Button chen= (Button)findViewById(R.id.web_button);

                                chen.setText("bla");

                            }
                        });

                queue.add(jsObjRequest);
               //// OnClick
            }
        });

        //temp_button.OnS


        TabHost tabs=(TabHost)findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec;

        TextView thisDay=(TextView)findViewById(R.id.thisDay);
        TextView otherDays=(TextView)findViewById(R.id.otherDays);

        spec=tabs.newTabSpec("tag");
        spec.setContent(R.id.otherDays);
        spec.setIndicator("otherDays");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("tag");
        spec.setContent(R.id.thisDay);
        spec.setIndicator("thisDay");
        tabs.addTab(spec);

//        thisDay.setBackground( getResources().getDrawable(R.drawable.fall2));




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
