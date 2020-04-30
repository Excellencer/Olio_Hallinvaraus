package com.example.olioht;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MyEnrollActivity extends AppCompatActivity {

    private DatabaseHelper myDb;

    private TextView hall_info;
    private TextView gym_info;
    private TextView time_info;
    private TextView date_info;
    private TextView ilmo_info;
    private TextView resUserName_info;
    private TextView resUserPhone_info;

    private String time;
    private String hall;
    private String gym;
    private String date;
    private String resUserName;
    private String resUserPhone;
    private String sport;

    private int userid_oma;
    private int rvID;
    private int maxUsers;
    private int enrollAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_enroll);

        myDb = new DatabaseHelper(this);

        //Otetaan käyttäjän ID muuttujaan
        RekisterointiTiedot rek = RekisterointiTiedot.getInstance();
        userid_oma = rek.kayttajaID;

        //Tallennetaan intentin tiedot muuttujiin
        time = getIntent().getStringExtra("TIME");
        hall = getIntent().getStringExtra("HALL");
        gym = getIntent().getStringExtra("GYM");
        date = getIntent().getStringExtra("DATE");
        sport = getIntent().getStringExtra("SPORT");
        rvID = getIntent().getIntExtra("RVID",0);
        maxUsers = getIntent().getIntExtra("MAXUSERS", 0);

        //Asetetaan oikeat ID:t
        hall_info = findViewById(R.id.hall_info);
        gym_info = findViewById(R.id.gym_info);
        time_info = findViewById(R.id.time_info);
        date_info = findViewById(R.id.date_info);
        ilmo_info = findViewById(R.id.ilmo_info);
        resUserName_info = findViewById(R.id.resUserName_info);
        resUserPhone_info = findViewById(R.id.resUserPhone_info);

       Cursor res = myDb.getUserInfoRV(rvID);
        res.moveToFirst();
        resUserName = res.getString(0) + " " + res.getString(1);
        resUserPhone= res.getString(2);
        res.close();

        Cursor res2 = myDb.getEnrollmentInfo(rvID);
        int i = 1;
        if(res2.getCount() > 0) {
            while (res2.moveToNext()) {
                //Lasketaan ilmoittautujien määrä tietokannan tietojen perusteella ja tarkastetaan onko käyttäjä jo ilmoittautunut
                i++;
            }
        }
        enrollAmount = i;
        res2.close();

        //Esitetään varauksen tiedot ilmoittautujalle
        hall_info.setText("Halli: " + hall);
        gym_info.setText("Sali: " + gym);
        time_info.setText("Aika: " + time);
        date_info.setText("PVM: " + date);
        ilmo_info.setText("Osallistujia: " + enrollAmount + "/" + maxUsers);
        resUserName_info.setText("Varaaja: " + resUserName);
        resUserPhone_info.setText("Puh: " + resUserPhone);
    }

    //Käynnistetään imoittautuminen
    public void delEnrollment(View view) {

        myDb.deleteEnrollment(userid_oma, rvID);

        Toast.makeText(MyEnrollActivity.this,
                "Ilmoittautuminen poistettu järjestelmästä",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyEnrollActivity.this, ReservSlotActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

}