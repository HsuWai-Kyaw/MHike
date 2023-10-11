package com.uog.mhike;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.uog.mhike.database.Hike;

import java.time.LocalDate;

public class HikeEntryActivity extends AppCompatActivity {
    private EditText txtName, txtLength, txtDescription, txtAdditional1, txtAdditional2;
    private Spinner spnLocation, spnDifficulty;
    private TextView txtDate;
    private Button btnDate, btnNext;
    private RadioButton rdoYes, rdoNo;

    private String[] locations = {"Location1", "Location2", "Location3", "Location4"};
    private String[] difficulty = {"Low", "Medium", "High"};

    String location, diff; //**
    private int id;
    private int locationIndex=0;
    private int difficultyIndex =0;

    public static final int UPDATE_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_entry);

        txtName = findViewById(R.id.txtName);
        txtLength = findViewById(R.id.txtLength);
        txtDescription = findViewById(R.id.txtDescription);
        txtAdditional1 = findViewById(R.id.txtAdditional1);
        txtAdditional2 = findViewById(R.id.txtAdditional2);

        spnLocation = findViewById(R.id.spnLocation);
        spnDifficulty = findViewById(R.id.spnDifficulty);

        txtDate = findViewById(R.id.txtDate);

        btnDate = findViewById(R.id.btnDate);
        btnNext = findViewById(R.id.btnNext);

        rdoYes = findViewById(R.id.rdoYes);
        rdoNo = findViewById(R.id.rdoNo);

        //adding location string to spinner
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLocation.setAdapter(adapterLocation);
        spnLocation.setSelection(locationIndex);
        spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                location = locations[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //adding difficulty string to spinner
        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficulty);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDifficulty.setAdapter(adapterDifficulty);
        spnDifficulty.setSelection(difficultyIndex);
        spnDifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                diff = difficulty[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                gotoNext();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment=new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

        receiveDate();
    }

    public void setDate(LocalDate date) {
        txtDate.setText(date.toString());
    }



    private void gotoNext() {
        String name = txtName.getText().toString(); //**
        String date = txtDate.getText().toString(); //**
        String parking = rdoYes.isChecked() ? "Yes" : "No";
        String length = txtLength.getText().toString(); //**
        String description = txtDescription.getText().toString(); //option
        String additional1 = txtAdditional1.getText().toString(); //option
        String additional2 = txtAdditional2.getText().toString(); //option

//        if (location == null || location.trim().isEmpty()) {
//            new AlertDialog.Builder(this).setTitle("Error").setMessage("Please choose the location").show();
//
//        }
//        if (diff == null || diff.trim().isEmpty()) {
//            new AlertDialog.Builder(this).setTitle("Error").setMessage("Please choose the difficulty").show();
//
//        }
        if (name == null || name.trim().isEmpty()) {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Please enter the name of hike").show();
            txtName.requestFocus();
            return;
        }
        if (date == null || date.trim().isEmpty()) {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Please select the date of hike").show();
            btnDate.requestFocus();
            return;
        }
        if (length == null || length.trim().isEmpty()) {
            new AlertDialog.Builder(this).setTitle("Error").setMessage("Please select the length of hike").show();
            txtLength.requestFocus();
            return;
        }

        Intent intent =new Intent(this,HikeDetailActivity.class);
        intent.putExtra(Hike.ID,id);
        intent.putExtra(Hike.NAME,name);
        intent.putExtra(Hike.LOCATION,location);
        intent.putExtra(Hike.DATE,date);
        intent.putExtra(Hike.PARKING,parking);
        intent.putExtra(Hike.LENGTH,length);
        intent.putExtra(Hike.DIFFICULTY,diff);
        intent.putExtra(Hike.DESCRIPTION,description);
        intent.putExtra(Hike.ADDITIONAL1,additional1);
        intent.putExtra(Hike.ADDITIONAL2,additional2);
        startActivityForResult(intent, UPDATE_REQUEST);
    }

    private void receiveDate(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

// String name, location, date, parking, length, difficulty, description, additional1, additional2;
            id=bundle.getInt(Hike.ID,0);
            String name = bundle.getString(Hike.NAME);
            location = bundle.getString(Hike.LOCATION);
            String date = bundle.getString(Hike.DATE);
            String parking = bundle.getString(Hike.PARKING);
            Double length = Double.parseDouble(bundle.getString(Hike.LENGTH, "0"));
            diff = bundle.getString(Hike.DIFFICULTY);
            String description = bundle.getString(Hike.DESCRIPTION);
            String additional1 = bundle.getString(Hike.ADDITIONAL1);
            String additional2 = bundle.getString(Hike.ADDITIONAL2);

            txtName.setText(name);
            txtDate.setText(date);
            txtLength.setText(length + "");
            txtDescription.setText(description);
            txtAdditional1.setText(additional1);
            txtAdditional2.setText(additional2);

            //for location
            for (int i=0; i< locations.length; i++){
                if (location.equals(locations[i])){
                    locationIndex =i;
                    spnLocation.setSelection(locationIndex);
                    break;
                }
            }
            //for difficulty
            for (int i=0; i< difficulty.length; i++){
                if (difficulty.equals(difficulty[i])){
                    difficultyIndex =i;
                    spnDifficulty.setSelection(difficultyIndex);
                    break;
                }
            }
            //for parking
            if (parking.equalsIgnoreCase("Yes")){
                rdoYes.setSelected(true);
            }else{
                rdoNo.setSelected(true);
            }

//            lblLocation.setText(location);
//            lblParking.setText(parking);
//            lblDifficulty.setText(difficulty);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( requestCode == UPDATE_REQUEST && resultCode == RESULT_OK){
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}