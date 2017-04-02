package a1.wheather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button chen= (Button)findViewById(R.id.chen);
        chen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button chen= (Button)findViewById(R.id.chen);
                int temp=10;
                if (temp<12){
                    chen.setBackground( getResources().getDrawable(R.drawable.fall2));
                }
            }
        });


        TabHost tabs=(TabHost)findViewById(R.id.tabhost);
        tabs.setup();
        TabHost.TabSpec spec;

        spec=tabs.newTabSpec("tag");
        spec.setContent(R.id.otherDays);
        spec.setIndicator("otherDays");
        tabs.addTab(spec);

        spec=tabs.newTabSpec("tag");
        spec.setContent(R.id.thisDay);
        spec.setIndicator("thisDay");
        tabs.addTab(spec);


    }
}
