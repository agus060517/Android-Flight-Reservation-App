package com.techprax.comp3074_project13;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ItineraryActivity extends AppCompatActivity {

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int flightID;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        ListView flightList = (ListView) findViewById(R.id.itinerary);

        try{
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectItinerary(db);

            if (cursor != null && cursor.getCount() > 0) {

                //Toast.makeText(getApplicationContext(), String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();

                CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.custom_itinerary_view,
                        cursor,
                        new String[]{"ORIGIN", "DESTINATION", "AIRLINENAME",
                                "FLIGHTDURATION", "FLIGHTCLASSNAME", "DEPARTUREDATE"},
                        new int[]{R.id.txtOriginList, R.id.txtDestinationList,
                                R.id.txtAirline, R.id.txtTravelTime, R.id.txtClass, R.id.txtDepartureDateList},
                        0);

                flightList.setAdapter(listAdapter);
            }

            flightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    flightID = (int) id;
                    //Toast.makeText(getApplicationContext(), String.valueOf(flightID), Toast.LENGTH_SHORT).show();

                    intent = new Intent(getApplicationContext(), FlightDetailActivity.class);
                    intent.putExtra("FLIGHT_ID",flightID);


                    startActivity(intent);
                }
            });


        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }
}
