package com.example.olioht;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KayttajatiedotActivity extends AppCompatActivity {

    EditText enField, snField, puhField, kayField, salField, sal2Field;
    TextView osasField;
    Button paivitaButton;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kaytie);
        myDb = new DatabaseHelper(this);

        enField = findViewById(R.id.etunimiText);
        snField = findViewById(R.id.sukunimiText);
        puhField = findViewById(R.id.puhText);
        kayField = findViewById(R.id.kayttisText);
        salField = findViewById(R.id.salis1Text);
        sal2Field = findViewById(R.id.salis2Text);
        osasField = findViewById(R.id.osastoText);
        paivitaButton = findViewById(R.id.updateButton);

        /* Käyttäjätietojen noutaminen */
        RekisterointiTiedot rek = RekisterointiTiedot.getInstance(); // Alustus;
        enField.setText(rek.etunimi);
        snField.setText(rek.sukunimi);
        puhField.setText(rek.puhelinnumero);
        kayField.setText(rek.kayttajatunnus);
        osasField.setText(rek.osasto);

    }

    public void updateInfo(View v){

        String etu = enField.getText().toString(); // etunimi
        String suku = snField.getText().toString(); // sukunimi
        String puh = puhField.getText().toString(); // puhelinnumero
        String kayt = kayField.getText().toString(); // kayttajatunnus
        String salis = salField.getText().toString(); // salasana
        String salis2 = sal2Field.getText().toString(); // salasana uudestaan

        // Resetoidaan kentät valkoisiksi ennen tarkistusta.
        enField.setBackgroundColor(Color.WHITE);
        snField.setBackgroundColor(Color.WHITE);
        puhField.setBackgroundColor(Color.WHITE);
        kayField.setBackgroundColor(Color.WHITE);
        salField.setBackgroundColor(Color.WHITE);
        sal2Field.setBackgroundColor(Color.WHITE);

        // Tarkastetaan ettei pakolliset tekstikentät ole tyhjiä.
        if(etu.equals("") || suku.equals("") || puh.equals("") || kayt.equals("")){
            Toast toastError= Toast.makeText(getApplicationContext(),
                    "Täytä vaadittavat tiedot",
                    Toast.LENGTH_SHORT);

            if (etu.equals("")) {
                enField.setBackgroundColor(Color.parseColor("#ff6464")); // Punainen taustaväri
                toastError.show();
            }
            if (suku.equals("")) {
                snField.setBackgroundColor(Color.parseColor("#ff6464"));
                toastError.show();
            }
            if (puh.equals("")) {
                puhField.setBackgroundColor(Color.parseColor("#ff6464"));
                toastError.show();
            }
            if (kayt.equals("")) {
                kayField.setBackgroundColor(Color.parseColor("#ff6464"));
                toastError.show();
            }

        } else {

            RekisterointiTiedot rekU = RekisterointiTiedot.getInstance(); // Alustus;

            if(salis.equals("")){
                rekU.setTiedot(etu, suku, puh, kayt, myDb);
            }
            else {
                String salisCrypt;
                salisCrypt = Encryption.getSHA512(salis, "434234dfdfs243");
                if (!(salisCrypt.equals(rekU.salasana))) {

                    Boolean salasanaCheck;
                    salasanaCheck = salasananTarkistus(salis, salis2, salField, sal2Field);

                    if (salasanaCheck) {
                        rekU.updatePW(salisCrypt, salisCrypt);
                        rekU.setTiedot(etu, suku, puh, kayt, myDb);
                    } else {
                        Toast toastError= Toast.makeText(getApplicationContext(),
                                "Tarkista salasanojen muodot",
                                Toast.LENGTH_SHORT);
                        toastError.show();
                        return; // Jos salasana ei täsmää niin silloin mitään tietoja ei päivitetä.
                    }
                }
            }
            // Salasanan päivitys, JOS salasanaa on muutettu

            Toast toast= Toast.makeText(getApplicationContext(),
                    "Tiedot päivitetty.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static boolean salasananTarkistus(final String salis, final String salis2, EditText salField, EditText sal2Field) {

        // SALASANAN TARKISTAMINEN
        // Pitää olla 1 numero, 1 Iso kirjain ja 1 erikoismerkki ja vähintään 12 merkkiä pitkä.

        if(salis.length() < 12 && !(sallitutMerkit(salis))) {
            // Salasanan pituuden ja sallittujen merkkien tarkistus
            salField.setBackgroundColor(Color.parseColor("#ff6464"));
            return false;

        } else if (!(salis.equals(salis2))) {
            // Tarkastetaan että salasana täsmää varmenteen kanssa.
            salField.setBackgroundColor(Color.parseColor("#ff6464"));
            sal2Field.setBackgroundColor(Color.parseColor("#ff6464"));
            return false;

        } else if (!(salis.matches(".*\\d.*"))) {
            // Tarkastaa sisältääkö salasana numeron
            salField.setBackgroundColor(Color.parseColor("#ff6464"));
            return false;

        } else if (!(salis.matches(".*[!@#$%^&*].*"))){
            // Tarkastaa sisältääkö salasana erikoismerkin
            salField.setBackgroundColor(Color.parseColor("#ff6464"));
            return false;

        } else if (!(salis.matches(".*[A-Z].*"))){
            // Tarkastaa sisältääkö salasana ison kirjaimen
            salField.setBackgroundColor(Color.parseColor("#ff6464"));
            return false;

        }  else if (!(salis.matches(".*[a-z].*"))) {
            // Tarkastaa sisältääkö salasana pienen kirjaimen
            salField.setBackgroundColor(Color.parseColor("#ff6464"));
            return false;
        }else {
            // Salasana täyttää vaatimukset.
            return true;
        }
    }

    public static boolean sallitutMerkit(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public void openMenuActivity(View v) {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openAsetuksetActivity(View view) {
        Intent intent = new Intent(this, AsetuksetActivity.class);
        startActivity(intent);

    }
}
