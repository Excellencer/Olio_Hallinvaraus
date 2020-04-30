package com.example.olioht;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private Spinner spinner;

    private TextView hall_info;
    private TextView gym_info;
    private TextView time_info;
    private TextView date_info;
    private EditText maxUserInput;

    private String time;
    private String hall;
    private String gym;
    private String date;

    private int day;
    private int month;
    private int year;
    private int hallid;
    private int gymid;
    private int sportid;
    private int maxusers;
    private int oma_UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        myDb = new DatabaseHelper(this);

        addItemsOnSportSpinner();

        RekisterointiTiedot rek = RekisterointiTiedot.getInstance(); // Alustus;
        oma_UserID = rek.kayttajaID;

        time = getIntent().getStringExtra("TIME");
        hall = getIntent().getStringExtra("HALL");
        gym = getIntent().getStringExtra("GYM");
        date = getIntent().getStringExtra("DATE");
        day = getIntent().getIntExtra("DAY", 0);
        month = getIntent().getIntExtra("MONTH", 0);
        year = getIntent().getIntExtra("YEAR", 0);
        hallid = getIntent().getIntExtra("HALLID", 0);
        gymid = getIntent().getIntExtra("GYMID", 0);

        hall_info = findViewById(R.id.hall_info);
        gym_info = findViewById(R.id.gym_info);
        time_info = findViewById(R.id.time_info);
        date_info = findViewById(R.id.date_info);
        maxUserInput = findViewById(R.id.maxuser_input);

        hall_info.setText("Halli: " + hall);
        gym_info.setText("Sali: " + gym);
        time_info.setText("Aika: " + time);
        date_info.setText("PVM: " + day + "." + month + "." + year);

    }

    //Käynnistetään ajanvaraus
    public void activateReservation(View view) {
        //Tallennetaan käyttäjän syöttämä osallistujamäärä muuttujaan
        if (maxUserInput.getText().toString().equals("")){
            Toast.makeText(ReservationActivity.this,
                    "Aseta osallistujamäärä",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        maxusers = Integer.parseInt(maxUserInput.getText().toString());
        //Haetaan ID valitun lajin perusteella varausta varten
        getSportID(String.valueOf(spinner.getSelectedItem()));
        //Tarkastetaan käyttäjän syötteen oikeellisuus
        if (maxusers <= 0) {
            Toast.makeText(ReservationActivity.this,
                    "Osallistujamäärä liian pieni",
                    Toast.LENGTH_SHORT).show();
        } else {
            //Suoritetaan varaus ja ohjataan takaisin kalenterinäkymään
            myDb.insertReservationData(oma_UserID, date, time, maxusers, sportid, gymid);
            Intent intent = new Intent(ReservationActivity.this, AjanvarausActivity.class);

            Toast.makeText(ReservationActivity.this,
                    "Ajanvaraus lisätty järjestelmään",
                    Toast.LENGTH_SHORT).show();

            startActivity(intent);
        }
    }

    // Lisätään tietokannasta spinneriin tietoa lajeista
    public void addItemsOnSportSpinner() {

        Cursor res = myDb.getSportInfoAll();
        if(res.getCount() == 0) {
            return;
        }

        spinner = findViewById(R.id.spinner_laji);
        List<String> SportList = new ArrayList<String>();

        SportList.add("Ei lajia");

        while (res.moveToNext()) {
            String SportName = res.getString(0);
            SportList.add(SportName);
        }

        ArrayAdapter<String> dataAdapterSport = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, SportList);
        dataAdapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapterSport);

        res.close();
    }

    //Otetaan lajin perusteella haettaessa kyseistä lajinimeä vastaava ID
    public void getSportID(String sportname) {
        Cursor res = myDb.getSportID(sportname);

        while (res.moveToNext()) {
            sportid = res.getInt(0);
        }
        res.close();
    }
}