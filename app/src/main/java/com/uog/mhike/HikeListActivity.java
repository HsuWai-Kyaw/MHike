package com.uog.mhike;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uog.mhike.adapter.HikeAdapter;
import com.uog.mhike.database.DatabaseHelper;
import com.uog.mhike.database.Hike;

import java.util.ArrayList;
import java.util.List;

public class HikeListActivity extends AppCompatActivity {

    private List<Hike> hikeList=new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private HikeAdapter hikeAdapter;
    private RecyclerView recyclerView;
    public static final int UPDATE_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper= new DatabaseHelper(getBaseContext());
        hikeAdapter= new HikeAdapter(hikeList);
        hikeAdapter.setListener(new HikeAdapter.ClickListener() {
            @Override
            public void onButtonClick(int position, View v, long id) {
                Hike hike=hikeList.get(position);
                if (id==R.id.btnDetail){
                    //Todo go to hike detail and observation entry

                    
                } else if (id==R.id.btnEdit) {
                    //Todo pass data into HikeEntryActivity
                    gotoEntry(hike);
                    
                }else if (id==R.id.btnDelete) {
                    //Todo delete the record
                    long result = databaseHelper.deleteHike(hike.getId());
                    if (result != 1) {
                        //data deleting error
                        new AlertDialog.Builder(getBaseContext()).setTitle("Error").setMessage("Can't Delete.").show();
                    } else {
                        //extract data again
                        search("");
                    }
                }
            }
        });
        recyclerView.setAdapter(hikeAdapter);
        FloatingActionButton fab = findViewById(R.id.fabAddHike);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getBaseContext(), HikeEntryActivity.class);
                startActivityForResult(intent, UPDATE_REQUEST);
            }
        });
        search("");

        EditText txtSearch = findViewById(R.id.txtSearch);
        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnAdvancedSearch = findViewById(R.id.btnAdvancedSearch);
        btnSearch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                search(txtSearch.getText().toString());
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        search();
    }

    private void search( String keyword){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    hikeList = databaseHelper.searchHike(keyword);
//                    Log.i( "MyName",  hikeList.size() + "");
                    hikeAdapter.setHikeList(hikeList);
                    hikeAdapter.notifyDataSetChanged(); //refresh data
                }catch (Exception e){e.printStackTrace();}
            }
        });
    }

    /* *
    * Pass the data to HikeEntryActivity to edit hike
    * */
    private void gotoEntry(Hike hike){
        Intent intent =new Intent(this,HikeEntryActivity.class);
        intent.putExtra(Hike.ID,hike.getId());
        intent.putExtra(Hike.NAME,hike.getName());
        intent.putExtra(Hike.LOCATION,hike.getLocation());
        intent.putExtra(Hike.DATE,hike.getDate());
        intent.putExtra(Hike.PARKING,hike.getParking());
        intent.putExtra(Hike.LENGTH,hike.getLength()+"");
        intent.putExtra(Hike.DIFFICULTY,hike.getDifficulty());
        intent.putExtra(Hike.DESCRIPTION,hike.getDifficulty());
        intent.putExtra(Hike.ADDITIONAL1,hike.getAdditional1());
        intent.putExtra(Hike.ADDITIONAL2,hike.getAdditional2());
        startActivityForResult(intent, UPDATE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ( requestCode == UPDATE_REQUEST && resultCode == RESULT_OK){
            search("");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}