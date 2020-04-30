/*
 * Olio-ohjelmointi harjoitustyö kevät 2020
 * ----------------------
 * Authors: Kalle Liljeström, Miro Lähde, Marcus Palmu
 * Description:
 * Development environment: Android Studio
 * Version history: 1
 * License:
 *
 */

package com.example.olioht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AsetuksetActivity extends AppCompatActivity {

    EditText laji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asetukset);
        laji = findViewById(R.id.lisaaField);
    }

    public void openSelosteActivity(View v) {

        Intent intent = new Intent(this, SelosteActivity.class);
        startActivity(intent);
    }

    public void logout(View v) {

        // Tyhjentää välimuistin kirjautumistiedoista.
        RekisterointiTiedot rek = RekisterointiTiedot.getInstance(); // Alustus;
        rek.clearData();

        //Kirjautuu ulos.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openMenuActivity(View v) {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openKaytieActivity(View view) {
        Intent intent = new Intent(this, KayttajatiedotActivity.class);
        startActivity(intent);
    }

    public void lisaaLajiDB(View v){

        DatabaseHelper myDb = new DatabaseHelper(this);
        String sportname = laji.getText().toString().toLowerCase();
        if (sportname.equals("")) {
            Toast toastEiLaji= Toast.makeText(getApplicationContext(),
                    "Eihän tuo ole urheilulaji!",
                    Toast.LENGTH_SHORT);
            toastEiLaji.show();
            return;
        }
        if (myDb.insertSportData(sportname) == false) {
            Toast toastError = Toast.makeText(getApplicationContext(),
                    "Laji on jo olemassa.",
                    Toast.LENGTH_SHORT);
            toastError.show();
            return;
        }
        laji.setText("");

        Toast toastLapi = Toast.makeText(getApplicationContext(),
                "Laji lisätty!",
                Toast.LENGTH_SHORT);
        toastLapi.show();
    }
}
