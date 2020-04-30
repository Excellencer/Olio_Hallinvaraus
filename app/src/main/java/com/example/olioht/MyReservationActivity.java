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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MyReservationActivity extends AppCompatActivity {

    private DatabaseHelper myDb;
    private Spinner spinner;

    private TextView ilmo_info;
    private TextView hall_info;
    private TextView gym_info;
    private TextView time_info;
    private TextView date_info;
    private EditText maxUserInput;

    private String time;
    private String hall;
    private String gym;
    private String date;
    private String sport;

    private int sportid;
    private int maxusers;
    private int oma_UserID;
    private int rvID;
    private int enrollAmount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reservation);

        myDb = new DatabaseHelper(this);



        RekisterointiTiedot rek = RekisterointiTiedot.getInstance(); // Alustus;
        oma_UserID = rek.kayttajaID;

        time = getIntent().getStringExtra("TIME");
        hall = getIntent().getStringExtra("HALL");
        gym = getIntent().getStringExtra("GYM");
        date = getIntent().getStringExtra("DATE");
        sport = getIntent().getStringExtra("SPORT");
        rvID = getIntent().getIntExtra("RVID",0);
        maxusers = getIntent().getIntExtra("MAXUSERS",0);

        addItemsOnSportSpinner(sport);

        hall_info = findViewById(R.id.hall_info);
        gym_info = findViewById(R.id.gym_info);
        time_info = findViewById(R.id.time_info);
        date_info = findViewById(R.id.date_info);
        maxUserInput = findViewById(R.id.maxuser_input);
        ilmo_info = findViewById(R.id.ilmo_info);

        Cursor res2 = myDb.getEnrollmentInfo(rvID);
        int i = 1;
        if(res2.getCount() != 0) {
            while (res2.moveToNext()) {
                //Lasketaan ilmoittautujien määrä tietokannasta
                i++;
            }
        }
        enrollAmount = i;
        res2.close();

        hall_info.setText("Halli: " + hall);
        gym_info.setText("Sali: " + gym);
        time_info.setText("Aika: " + time);
        date_info.setText("PVM: " + date);
        ilmo_info.setText("Osallistujia: " + enrollAmount + "/" + maxusers);

    }

    //Päivitetään ajanvarauksen tiedot
    public void updateReservation(View view) {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy");
        String current_date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        Date current;
        try {
            current = sdformat.parse(current_date);
            Date selected_date = sdformat.parse(date);
            if(selected_date.compareTo(current) <= 0) {
                Toast.makeText(MyReservationActivity.this,
                        "Et voi muokata tämän päivän varauaksia",
                        Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }



        //Tallennetaan käyttäjän syöttämä osallistujamäärä muuttujaan
        if (maxUserInput.getText().toString().equals("")){
            Toast.makeText(MyReservationActivity.this,
                    "Aseta osallistujamäärä",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        maxusers = Integer.parseInt(maxUserInput.getText().toString());

        //Haetaan sportID valitun lajin perusteella

        getSportID(String.valueOf(spinner.getSelectedItem()));

        //Tarkastetaan käyttäjän syötteen oikeellisuus
        if (maxusers <= 0) {
            Toast.makeText(MyReservationActivity.this,
                    "Osallistujamäärä liian pieni",
                    Toast.LENGTH_SHORT).show();
        } else {
            //Suoritetaan päivitys
            myDb.updateReservation(rvID, sportid,  maxusers);
            Intent intent = new Intent(MyReservationActivity.this, ReservSlotActivity.class);

            Toast.makeText(MyReservationActivity.this,
                    "Ajanvaraus päivitetty",
                    Toast.LENGTH_SHORT).show();

            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }
    public void delReservation (View view) {
        SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy");
        String current_date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        Date current;
        try {
            current = sdformat.parse(current_date);
            Date selected_date = sdformat.parse(date);
            if(selected_date.compareTo(current) <= 0) {
                Toast.makeText(MyReservationActivity.this,
                        "Et voi poistaa tämän päivän varauaksia",
                        Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        myDb.deleteReservation(rvID);

        Toast.makeText(MyReservationActivity.this,
                "Varaus poistettu järjestelmästä",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyReservationActivity.this, ReservSlotActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    // Lisätään tietokannasta spinneriin tietoa lajeista
    public void addItemsOnSportSpinner(String sport) {

        Cursor res = myDb.getSportInfoAll();
        if(res.getCount() == 0) {
            return;
        }

        spinner = findViewById(R.id.spinner_laji);
        List<String> SportList = new ArrayList<String>();

        SportList.add(sport);

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
        //Virheilmoitus tietokantahaun palauttaessa tyhjän

        while (res.moveToNext()) {
            sportid = res.getInt(0);
        }
        res.close();
    }
}