package com.example.olioht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class RekisActivity extends AppCompatActivity {


    EditText enField, snField, puhField, kayField, salField, sal2Field;
    Spinner osasField;
    Button hyvaksyButton;
    ArrayList<String> osastoLista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        osastoLista.add("Ei jäsenyyttä");
        osastoLista.add("Halli 1");
        osastoLista.add("Halli 2");
        osastoLista.add("Hallit 1 & 2");

        enField = findViewById(R.id.etunimiText);
        snField = findViewById(R.id.sukunimiText);
        puhField = findViewById(R.id.puhText);
        kayField = findViewById(R.id.kayttisText);
        salField = findViewById(R.id.salis1Text);
        sal2Field = findViewById(R.id.salis2Text);
        osasField = findViewById(R.id.osastoSpin);
        hyvaksyButton = findViewById(R.id.kirjauduButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, osastoLista);
        osasField.setAdapter(adapter);

    }


    public void rekButton(View v) {
        boolean lapi;
        Rekisterointi clickRek = new Rekisterointi();
        lapi = clickRek.rekisButtonClick(enField, snField, puhField, kayField, salField, sal2Field, osasField);
        if (lapi) {
            openCodeActivity(v);
        }
    }

    public void openCodeActivity(View view) {
        Intent intent = new Intent(this, CodeActivity.class);
        startActivity(intent);
    }
}