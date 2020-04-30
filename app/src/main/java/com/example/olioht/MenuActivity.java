package com.example.olioht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

    }

    public void openKaytieActivity(View view) {
        Intent intent = new Intent(this, KayttajatiedotActivity.class);
        startActivity(intent);
    }

    public void openAsetuksetActivity(View view) {
        Intent intent = new Intent(this, AsetuksetActivity.class);
        startActivity(intent);
    }

    public void openVaraukseniActivity(View view) {
        Intent intent = new Intent(this, ReservSlotActivity.class);
        startActivity(intent);
    }

    public void openAjanvarausActivity(View view) {
        Intent intent = new Intent(this, AjanvarausActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        // Do nothing
    }
}
