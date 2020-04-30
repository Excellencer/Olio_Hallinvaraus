package com.example.olioht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Random;


public class CodeActivity extends AppCompatActivity {

    private String inputCode = "";
    private String randomCode = "";
    private EditText codeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code);

        codeInput = findViewById(R.id.lisaaField);

    }

    public void codeDisplay(View v) {
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        Toast toast= Toast.makeText(CodeActivity.this,
                "6-numeroinen koodisi: " + String.format("%06d", randomInteger),
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        randomCode = String.format("%06d", randomInteger);

        System.out.println("random" + randomCode);
    }

    public void openMainActivity(View view) {

        DatabaseHelper db = new DatabaseHelper(this);
        RekisterointiTiedot rek = RekisterointiTiedot.getInstance(); // Alustus;

        inputCode = codeInput.getText().toString();
        System.out.println("input" + inputCode);
        if (randomCode.equals(inputCode)) {
            rek.uploadDB(db);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}

