package com.uog.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uog.mhike.database.DatabaseHelper;
import com.uog.mhike.database.Observation;

import java.time.ZonedDateTime;

public class ObservationEntryActivity extends AppCompatActivity {

    private int id;
    private int hikeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_entry);

        EditText txtObservation  = findViewById(R.id.txtObservation);
        EditText txtComment = findViewById(R.id.txtComment);
        Button btnSave = findViewById(R.id.btnObservationSave);

        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            hikeId = bundle.getInt(Observation.HIKE_ID);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strobservation = txtObservation.getText().toString();
                if (strobservation == null || strobservation.trim().isEmpty()){
                    Toast.makeText(getBaseContext(), "Please enter the observation", Toast.LENGTH_LONG).show();
                    txtObservation.requestFocus();
                    return;
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
        long result =0;
        if (id ==0){
            Observation observation = new Observation(hikeId, strobservation, ZonedDateTime.now(),
                    txtComment.getText().toString(), null,null, null,null);
            result = databaseHelper.saveObservation(observation);

        }else{
            //TODO update

        }
                if(result>0){
                    //successful
                    new AlertDialog.Builder(ObservationEntryActivity.this).setTitle("Success")
                            .setMessage("Successfully saved Hike Data.").setIcon(R.drawable.baseline_info_24).show();
                    Toast.makeText(getBaseContext(), "Observation data saved successfully.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    //fail
                    new AlertDialog.Builder(ObservationEntryActivity.this).setTitle("Failed")
                            .setMessage("Something went wrong!").setIcon(R.drawable.baseline_error_24).show();
                    Toast.makeText(getBaseContext(), "Observation data was not saved.", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}