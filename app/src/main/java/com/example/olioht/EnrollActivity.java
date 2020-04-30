package com.example.olioht;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnrollActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    TextView hall_info;
    TextView gym_info;
    TextView time_info;
    TextView date_info;
    TextView ilmo_info;
    TextView resUserName_info;
    TextView resUserPhone_info;

    String time;
    String hall;
    String gym;
    String date;
    String resUserName;
    String resUserPhone;

    int day;
    int month;
    int year;
    int hallid;
    int gymid;
    int userid_tekija;
    int userid_oma;
    int rvid;
    int maxusers;
    int enrollAmount;

    Boolean ilmonnu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        myDb = new DatabaseHelper(this);

        //Otetaan käyttäjän ID muuttujaan
        RekisterointiTiedot rek = RekisterointiTiedot.getInstance();
        userid_oma = rek.kayttajaID;

        //Tallennetaan intentin tiedot muuttujiin
        time = getIntent().getStringExtra("TIME");
        hall = getIntent().getStringExtra("HALL");
        gym = getIntent().getStringExtra("GYM");
        date = getIntent().getStringExtra("DATE");
        resUserName = getIntent().getStringExtra("RESUSERNAME");
        resUserPhone = getIntent().getStringExtra("RESUSERPHONE");
        day = getIntent().getIntExtra("DAY", 0);
        month = getIntent().getIntExtra("MONTH", 0);
        year = getIntent().getIntExtra("YEAR", 0);
        hallid = getIntent().getIntExtra("HALLID", 0);
        gymid = getIntent().getIntExtra("GYMID", 0);

        //Asetetaan oikeat ID:t
        hall_info = findViewById(R.id.hall_info);
        gym_info = findViewById(R.id.gym_info);
        time_info = findViewById(R.id.time_info);
        date_info = findViewById(R.id.date_info);
        ilmo_info = findViewById(R.id.ilmo_info);
        resUserName_info = findViewById(R.id.resUserName_info);
        resUserPhone_info = findViewById(R.id.resUserPhone_info);

        Cursor res = myDb.getRvUsIdMax(date, time, gymid);
        if(res.getCount() == 0) {
            System.out.println("############ VIRHE LOGIIKASSA RVUSIDMAX ###############");
            return;
        }

        //Tallennetaan varauksen tiedot muuttujiin
        while (res.moveToNext()) {
            rvid = res.getInt(0);
            userid_tekija = res.getInt(1);
            maxusers = res.getInt(2);
        }
        res.close();

        Cursor res2 = myDb.getEnrollmentInfo(rvid);
        int i = 1;
        if(res2.getCount() == 0) {
            System.out.println("############ VIRHE LOGIIKASSA ENROLLMENTINFO ###############");
        } else {
            while (res2.moveToNext()) {
                //Lasketaan ilmoittautujien määrä tietokannan tietojen perusteella ja tarkastetaan onko käyttäjä jo ilmoittautunut
                i++;
                int userid = res2.getInt(0);
                if(userid_oma == userid) {
                    ilmonnu = true;
                }
            }
        }
        enrollAmount = i;
        res2.close();

        //Esitetään varauksen tiedot ilmoittautujalle
        hall_info.setText("Halli: " + hall);
        gym_info.setText("Sali: " + gym);
        time_info.setText("Aika: " + time);
        date_info.setText("PVM: " + day + "." + month + "." + year);
        date_info.setText("PVM: " + day + "." + month + "." + year);
        ilmo_info.setText("Osallistujia: " + i + "/" + maxusers);
        resUserName_info.setText("Varaaja: " + resUserName);
        resUserPhone_info.setText("Puh: " + resUserPhone);
    }

    //Käynnistetään imoittautuminen
    public void activateEnrollment(View view) {

        //Tarkastetaan ilmoittautujan oikeellisuus
        if (userid_oma == userid_tekija) {
            Toast.makeText(EnrollActivity.this,
                    "Ilmoittautuminen omaan varaukseen ei ole mahdollista",
                    Toast.LENGTH_SHORT).show();
        } else if (ilmonnu) {
            Toast.makeText(EnrollActivity.this,
                    "Olet jo ilmoittautunut",
                    Toast.LENGTH_SHORT).show();
        } else if (enrollAmount == maxusers) {
            Toast.makeText(EnrollActivity.this,
                    "Tämä varaus on jo täynnä",
                    Toast.LENGTH_SHORT).show();
        }  else {
            //Suoritetaan varaus ja palataan kalenterinäkymään
            myDb.insertEnrollmentData(userid_oma, rvid);

            Toast.makeText(EnrollActivity.this,
                    "Ilmoittautuminen lisätty järjestelmään",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EnrollActivity.this, AjanvarausActivity.class);
            startActivity(intent);
        }
    }
}