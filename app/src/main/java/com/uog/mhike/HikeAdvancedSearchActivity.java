package com.uog.mhike;

import static com.uog.mhike.HikeEntryActivity.locations;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.uog.mhike.database.Hike;

import java.time.LocalDate;

public class HikeAdvancedSearchActivity extends AppCompatActivity {

    private EditText txtAdvName, txtAdvLength;
    private Button btnAdvDate, btnAdvSearch;
    private Spinner spnAdvLocation;
    private TextView txtAdvDate;
//    private String[] locations ={"location1", "location2" , "location3", "location4"};
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_advanced_search);

        txtAdvName= findViewById(R.id.txtAdvName);
        txtAdvDate=findViewById(R.id.txtAdvDate);
        txtAdvLength = findViewById(R.id.txtAdvLength);
        spnAdvLocation=findViewById(R.id.spnAdvLocation);
        btnAdvDate = findViewById(R.id.btnAdvDate);
        btnAdvSearch=findViewById(R.id.btnAdvSearch);

        //adding location string to spinner
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAdvLocation.setAdapter(adapterLocation);
        spnAdvLocation.setSelection(0);
        spnAdvLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location = locations[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAdvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment=new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(),"datePickerAdv");
            }
        });
        btnAdvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtAdvName.toString();
                String date = txtAdvDate.toString();
                String length = txtAdvLength.toString();
                try {
                    if (name == null || name.isEmpty()){
                        new AlertDialog.Builder(getBaseContext()).setTitle("Error").setMessage("Please enter the name of hike").show();
                        txtAdvName.requestFocus();
                        return;
                    }
                    if (location == null || location.isEmpty()){
                        new AlertDialog.Builder(getBaseContext()).setTitle("Error").setMessage("Please enter the name of hike").show();
                        spnAdvLocation.requestFocus();
                        return;
                    }
                    if ((date == null || date.isEmpty()) && (length==null || length.isEmpty())){
                        new AlertDialog.Builder(getBaseContext()).setTitle("Error").setMessage("Please enter date or length").show();
                        return;
                    }
                }catch (Exception e) {

                }


                Intent intent=new Intent();
                intent.putExtra(Hike.NAME,name.trim());
                intent.putExtra(Hike.LOCATION,location);
                intent.putExtra(Hike.DATE,date);
                intent.putExtra(Hike.LENGTH,length);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    public void setDate(LocalDate date) {
        txtAdvDate.setText(date.toString());
    }
}