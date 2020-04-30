package com.example.olioht;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TimeSlotActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TimeSlotAdapter mAdapter;
    private List<TimeSlot> mTimeList;

    private Integer rvid;
    private Integer sportID;
    private Integer userid;
    private Integer maxusers;

    private String sportName;
    private String resUserName;
    private String resUserPhone;

    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
        myDb = new DatabaseHelper(this);

        //Tallennetaan intentin kautta saadut arvot muuttujiin
        String hall = getIntent().getStringExtra("HALL");
        String gym = getIntent().getStringExtra("GYM");
        String wantedSport = getIntent().getStringExtra("SPORT"); // LAJI #####################################
        String date = getIntent().getStringExtra("DATE");
        int day = getIntent().getIntExtra("DAY", 0);
        int month = getIntent().getIntExtra("MONTH", 0);
        int year = getIntent().getIntExtra("YEAR", 0);
        int hallid = getIntent().getIntExtra("HALLID", 0);
        int gymid = getIntent().getIntExtra("GYMID", 0);
        int wantedSportID = getIntent().getIntExtra("SPORTID", 0); // LAJI #####################################

        //Otetaan recyclerview xml -tiedostosta
        mRecyclerView = (RecyclerView) findViewById(R.id.idRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // When a sport is selected it filters with the sport.
        if (!(wantedSportID == 0)) {

            //Populate correct reservation states
            mTimeList = new ArrayList<>();
            for (int i = 0; i < 13; i++) {
                String time = Common.convertTimeSlotToString(i);
                getReservationData(hallid, gymid, date, time);
                if (getSportReservationData(wantedSportID, gymid, date, time)) {
                    String enrollments = Integer.toString(getEnrollmentInfo(rvid)) + "/";
                    String maxusers = Integer.toString(this.maxusers);
                    getResUserInfo(userid);
                    String state = "Varattu";
                    //Luodaan uusi kortti, jolle annetaan sitä koskevat tiedot
                    mTimeList.add(new TimeSlot(time, hall, gym, date, day, month, year, hallid, gymid, state, R.drawable.invisible_69dp, "#FFF44336", resUserName, resUserPhone, wantedSport, maxusers, enrollments));
                }
            }

        } else {

            //Populate correct reservation states
            mTimeList = new ArrayList<>();
            for (int i = 0; i < 13; i++) {
                String time = Common.convertTimeSlotToString(i);
                if (getReservationData(hallid, gymid, date, time)) {
                    getSportInfo(sportID);
                    String enrollments = Integer.toString(getEnrollmentInfo(rvid)) + "/";
                    String maxusers = Integer.toString(this.maxusers);
                    getResUserInfo(userid);
                    String state = "Varattu";
                    //Luodaan uusi kortti, jolle annetaan sitä koskevat tiedot
                    mTimeList.add(new TimeSlot(time, hall, gym, date, day, month, year, hallid, gymid, state, R.drawable.invisible_69dp, "#FFF44336", resUserName, resUserPhone, sportName, maxusers, enrollments));
                } else {
                    String state = "Vapaa";
                    //Luomme useita erilaisia kortteja yhdellä pohjalla, joten "filler" muuttujaa tarvitaan yhtenäistämiseen
                    String filler = "";
                    //Luodaan uusi kortti, jolle annetaan sitä koskevat tiedot
                    mTimeList.add(new TimeSlot(time, hall, gym, date, day, month, year, hallid, gymid, state, R.drawable.invisible_69dp, "#FF4CAF50", filler, filler, filler, filler, filler));
                }
            }
        }

        //set adapter to recyclerview
        mAdapter = new TimeSlotAdapter(mTimeList, this);
        mRecyclerView.setAdapter(mAdapter);

    }

    //Kerätään tietokannasta varausdataa, jolla täydennetään seuraava kortti sekä osaa tiedoista hyödynnetään seuraavassa activityssä
    public boolean getReservationData(Integer hallid, Integer gymid, String resDate, String timeSlot) {

        //Virheilmoitus tietokantahaun palauttaessa tyhjän
        Cursor res = myDb.getTimeSlotData(hallid, gymid, resDate, timeSlot);
        if(res.getCount() == 0) {
            System.out.println("############ VAPAANAAA ###############");
            res.close();
            return false;
        }

        while (res.moveToNext()) {
            rvid = res.getInt(0);
            userid = res.getInt(1);
            sportID = res.getInt(2);
            maxusers = res.getInt(3);
        }
        res.close();
        return true;
    }

    //Kerätään kortin lajitiedot sportid:n perusteella
    public void getSportInfo(int sportid) {

        Cursor res = myDb.getSportData(sportid);
        //Virheilmoitus tietokantahaun palauttaessa tyhjän
        if(res.getCount() == 0) {
            System.out.println("############ VIRHE LOGIIKASSA SPORTINFO ###############");
            res.close();
        }

        while (res.moveToNext()) {
            sportName = res.getString(0);
        }
        res.close();
    }

    //Lasketaan ilmoittautujien määrä (varauksen tekijä lasketaan mukaan)
    public int getEnrollmentInfo(int rvid) {
        int i = 1;
        Cursor res = myDb.getEnrollmentInfo(rvid);
        //Virheilmoitus tietokantahaun palauttaessa tyhjän
        if(res.getCount() == 0) {
            System.out.println("############ VIRHE LOGIIKASSA ENROLL ###############");
            res.close();
            return i;
        }

        while (res.moveToNext()) {
            i++;
        }
        res.close();
        return i;
    }

    //Otetaan varauksen tekijän tiedot ylös
    public void getResUserInfo(int userid) {
        Cursor res = myDb.getUserInfo(userid);
        //Virheilmoitus tietokantahaun palauttaessa tyhjän
        if(res.getCount() == 0) {
            System.out.println("############ VIRHE LOGIIKASSA USERINFO ###############");
            res.close();
        }

        while (res.moveToNext()) {
            resUserName = res.getString(0);
            resUserPhone = res.getString(1);
        }
        res.close();
    }

    //Jos haku on tehty lajin perusteella, otetaan sitä vastaavat tiedot tietokannasta
    public boolean getSportReservationData(Integer sportid, Integer gymid, String resDate, String timeSlot) {

        Cursor res = myDb.getTimeSlotDataForSport(sportid, gymid, resDate, timeSlot);
        if(res.getCount() == 0) {
            return false;
        }

        res.close();
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AjanvarausActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}