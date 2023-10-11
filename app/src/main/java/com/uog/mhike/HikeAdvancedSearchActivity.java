package com.uog.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class HikeAdvancedSearchActivity extends AppCompatActivity {

    private EditText txtAdvName, txtAdvLength;
    private Button btnAdvDate, btnAdvSearch;
    private Spinner spnAdvLocation;
    private TextView txtAdvDate;
    private String[] locations ={"location1", "location2" , "location3", "location4"};
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_advanced_search);

        txtAdvName= findViewById(R.id.txtAdvName);
        txtAdvDate=findViewById(R.id.txtAdvDate);
        txtAdvLength = findViewById(R.id.txtAdvLength);
        spnAdvLocation=findViewById(R.id.spnAdvLocation);
    }
}