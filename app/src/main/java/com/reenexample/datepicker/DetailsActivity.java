package com.reenexample.datepicker;

import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {


    private TextView date;
    private TextView time;
    private TextView title;
    private TextView details;
    ViewPager view_pager;
    String mDrawableName = "1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        date = (TextView)findViewById(R.id.tvdate);
        time = (TextView)findViewById(R.id.tvtime);
        title = (TextView)findViewById(R.id.tvtitle);
        details = (TextView)findViewById(R.id.tvdetails);

        Bundle extras = getIntent().getExtras();

        String Date = extras.getString("key");
        String Time = extras.getString("key1");
        String Title = extras.getString("key2");
        String Details = extras.getString("key3");
//        int pos = Integer.valueOf(extras.getString("key4"));
        //Toast.makeText(getApplicationContext(),Title,Toast.LENGTH_LONG);


        date.setText(Date);
        time.setText(Time);
       title.setText(Title);
       details.setText(Details);




    }
}
