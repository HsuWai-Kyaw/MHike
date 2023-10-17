package com.uog.mhike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uog.mhike.database.Hike;
import com.uog.mhike.database.Observation;

public class HikeDetailViewActivity extends AppCompatActivity {
private int id;
public static final int SAVE_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_detail_view);

        TextView lblName = findViewById(R.id.lblHikeDetailName);
        TextView lblLocation = findViewById(R.id.lblHikeDetailLocation);
        TextView lblDate = findViewById(R.id.lblHikeDetailDate);
        TextView lblParking = findViewById(R.id.lblHikeDetailParking);
        TextView lblLength = findViewById(R.id.lblHikeDetailLength);
        TextView lblDifficulty = findViewById(R.id.lblHikeDetailDifficulty);
        TextView lblDescription = findViewById(R.id.lblHikeDetailDescription);
        TextView lblAdditional1 = findViewById(R.id.lblHikeDetailAdditional1);
        TextView lblAdditional2 = findViewById(R.id.lblHikeDetailAdditional2);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

// String name, location, date, parking, length, difficulty, description, additional1, additional2;
            id=bundle.getInt(Hike.ID,0);
            String name = bundle.getString(Hike.NAME);
            String location = bundle.getString(Hike.LOCATION);
            String date = bundle.getString(Hike.DATE);
            String parking = bundle.getString(Hike.PARKING);
            String length = bundle.getString(Hike.LENGTH, "0");
            String difficulty = bundle.getString(Hike.DIFFICULTY);
            String description = bundle.getString(Hike.DESCRIPTION);
            String additional1 = bundle.getString(Hike.ADDITIONAL1);
            String additional2 = bundle.getString(Hike.ADDITIONAL2);

            lblName.setText(name);
            lblLocation.setText(location);
            lblDate.setText(date);
            lblParking.setText(parking);
            lblLength.setText(length);
            lblDifficulty.setText(difficulty);
            lblDescription.setText(description);
            lblAdditional1.setText(additional1);
            lblAdditional2.setText(additional2);
        }

        Button btnAddObservation = findViewById(R.id.btnAddObservation);
        btnAddObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ObservationEntryActivity.class);
                intent.putExtra(Observation.HIKE_ID, id);
                startActivityForResult(intent, SAVE_REQUEST_CODE);

            }
        });
    }
}