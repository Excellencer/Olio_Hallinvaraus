package com.example.olioht;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReservSlotActivity extends AppCompatActivity {

    private RecyclerView rRecyclerView;
    private ReservSlotAdapter rAdapter;
    private List<ReservSlot> rReservList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);
         DatabaseHelper db = new DatabaseHelper(this);

        rReservList = new ArrayList<>();

        RekisterointiTiedot rek = RekisterointiTiedot.getInstance(); // Alustus;


        //Otetaan recyclerview xml -tiedostosta
        rRecyclerView = findViewById(R.id.idRecyclerView);

        rRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Populate correct reservations
        rReservList = db.getOwnReservations(rek.kayttajaID);
        if(rReservList.isEmpty()) {
            rReservList.add(new ReservSlot("", "Ei varauksia tai ilmoittautumisia","", 0, "","", 0, "", R.drawable.invisible_69dp, "#FF0000"));
        }

        //set adapter to recyclerview
        rAdapter = new ReservSlotAdapter(rReservList, this);
        rRecyclerView.setAdapter(rAdapter);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }
}
