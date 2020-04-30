package com.example.olioht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText kayttajaField, salasanaField;
    private String kayt = "";
    private String salis = "";
    private Integer kaytID = -1;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        kayttajaField = findViewById(R.id.lisaaField);
        salasanaField = findViewById(R.id.salasanaText);
        db = new DatabaseHelper(this);
    }

    public void kirjauduButton(View view) {
        boolean lapi = false;
        kayt = kayttajaField.getText().toString(); // kayttajatunnus
        salis = salasanaField.getText().toString(); // salasana

        Context context = getApplicationContext();

        if (kayt.equals("")) {
            Toast toastEiKayt= Toast.makeText(context,
                    "Anna käyttäjänimi",
                    Toast.LENGTH_SHORT);
            toastEiKayt.show();
            return;
        } else if (salis.equals("")){
            Toast toastEiSalis= Toast.makeText(context,
                    "Anna salasana",
                    Toast.LENGTH_SHORT);
            toastEiSalis.show();
            return;
        }

        // ########### SHA512 + SALT

        salis = Encryption.getSHA512(salis, "434234dfdfs243");


        // ###########




        kaytID = db.login(kayt, salis);

        if (kaytID >= 0) {
            Toast toastLapi= Toast.makeText(context,
                    "Tervetuloa!",
                    Toast.LENGTH_SHORT);
            toastLapi.show();

            RekisterointiTiedot rek = RekisterointiTiedot.getInstance(); // Alustus;
            rek.kayttajaID = kaytID;
            rek.downloadUserInfo(db);
            openMenuActivity(view);
        } else {
        Toast toastError= Toast.makeText(context,
                "Väärä käyttäjänimi tai salasana",
                Toast.LENGTH_SHORT);
        toastError.show();
        return;
        }
    }

    public void openRekisActivity(View view) {
        Intent intent = new Intent(this, RekisActivity.class);
        startActivity(intent);
    }
    public void openMenuActivity(View viewm) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }



    @Override
    public void onBackPressed() {
        // Do nothing
    }
}