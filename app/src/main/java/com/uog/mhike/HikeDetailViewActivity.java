package com.uog.mhike;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uog.mhike.adapter.HikeAdapter;
import com.uog.mhike.adapter.ObservationAdapter;
import com.uog.mhike.database.DatabaseHelper;
import com.uog.mhike.database.Hike;
import com.uog.mhike.database.Observation;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HikeDetailViewActivity extends AppCompatActivity {
private int id;
public static final int SAVE_REQUEST_CODE=1;

    private List<Observation> observationList=new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private ObservationAdapter observationAdapter;
    private RecyclerView recyclerObservation;
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

        recyclerObservation = findViewById(R.id.recyclerObservation);
        recyclerObservation.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper= new DatabaseHelper(getBaseContext());
        observationAdapter = new ObservationAdapter(observationList);
        observationAdapter.setListener(new ObservationAdapter.ClickListener() {
            @Override
            public void onButtonClick(int position, View v, long id) {

                Observation observation=observationList.get(position);
                if (id ==R.id.btnObservationView){
                    //TODO goto detail view
                    Intent intent = new Intent(getBaseContext(), ObservationViewActivity.class);
                    intent.putExtra(Observation.OBSERVATION, observation.getObservation());
                    intent.putExtra(Observation.COMMENT, observation.getComment());
                    intent.putExtra(Observation.DATE_TIME, observation.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")));
                    intent.putExtra(Observation.STR1, observation.getStr1());
                    intent.putExtra(Observation.STR2, observation.getStr2());
                    startActivity(intent);

                }else if( id ==R.id.btnObservationEdit){
                    //TODO edit
                    Intent intent = new Intent(getBaseContext(), ObservationEntryActivity.class);
                    intent.putExtra(Observation.HIKE_ID, id);
                    intent.putExtra(Observation.ID, observation.getId());
                    intent.putExtra(Observation.OBSERVATION, observation.getObservation());
                    intent.putExtra(Observation.COMMENT, observation.getComment());
                    startActivityForResult(intent, SAVE_REQUEST_CODE);

                }else if ( id ==R.id.btnObservationDelete){
                    //TODO delete
                    databaseHelper.deleteObservation(observation.getId());
                    listObservation();
                }
            }
        });
        recyclerObservation.setAdapter(observationAdapter);
        listObservation();
    }

    private void listObservation(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    observationList = databaseHelper.searchObservation(id);
                    observationAdapter.setObservationList(observationList);
                    observationAdapter.notifyDataSetChanged(); //refresh data
                }catch (Exception e){e.printStackTrace();}
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( requestCode == SAVE_REQUEST_CODE && resultCode == RESULT_OK){
            listObservation();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}