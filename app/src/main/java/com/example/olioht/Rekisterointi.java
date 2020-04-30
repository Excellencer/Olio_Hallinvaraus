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

import android.graphics.Color;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rekisterointi extends AppCompatActivity {

    protected Rekisterointi() {
        RekisterointiTiedot rek = RekisterointiTiedot.getInstance(); // Alustus;
    }

    protected boolean rekisButtonClick(EditText enField, EditText snField, EditText puhField, EditText kayField, EditText salField, EditText sal2Field, Spinner osasField){

        // Säilötään tietoja Singleton oliossa, maksunäkymä vaiheen ajaksi.
        String etu = enField.getText().toString(); // etunimi
        String suku = snField.getText().toString(); // sukunimi
        String puh = puhField.getText().toString(); // puhelinnumero
        String kayt = kayField.getText().toString(); // kayttajatunnus
        String salis = salField.getText().toString(); // salasana
        String salis2 = sal2Field.getText().toString(); // salasana uudestaan
        String osas = osasField.getSelectedItem().toString(); // osasto
        Boolean lapi = false;

        // Resetoidaan kentät valkoisiksi ennen tarkistusta.
        enField.setBackgroundColor(Color.WHITE);
        snField.setBackgroundColor(Color.WHITE);
        puhField.setBackgroundColor(Color.WHITE);
        kayField.setBackgroundColor(Color.WHITE);
        salField.setBackgroundColor(Color.WHITE);
        sal2Field.setBackgroundColor(Color.WHITE);
        osasField.setBackgroundColor(Color.WHITE);

        // Integer SpinnerChoice = osasField.getSelectedItemPosition(); // Jos positio on 0, silloin ei olla valittu mitään. || SpinnerChoice == 0
        // Tarkastetaan ettei pakolliset tekstikentät ole tyhjiä.
        if(etu.equals("") || suku.equals("") || puh.equals("") || kayt.equals("") || salis.equals("") || salis2.equals("")) {

            if (etu.equals("")) {
                enField.setBackgroundColor(Color.parseColor("#ff6464")); // Punainen taustaväri
            }
            if (suku.equals("")) {
                snField.setBackgroundColor(Color.parseColor("#ff6464"));
            }
            if (puh.equals("")) {
                puhField.setBackgroundColor(Color.parseColor("#ff6464"));
            }
            if (kayt.equals("")) {
                kayField.setBackgroundColor(Color.parseColor("#ff6464"));
            }
            if (salis.equals("")) {
                salField.setBackgroundColor(Color.parseColor("#ff6464"));
            }
            if (salis2.equals("")) {
                sal2Field.setBackgroundColor(Color.parseColor("#ff6464"));
            }

        } else {

            Boolean salasanaCheck;
            salasanaCheck = salasananTarkistus(salis, salis2, salField, sal2Field);

            if(salasanaCheck){
                // ########### SHA512 + SALT

                salis = Encryption.getSHA512(salis, "434234dfdfs243");

                // ###########


                RekisterointiTiedot rek1 = RekisterointiTiedot.getInstance();
                rek1.sailoTiedot(etu, suku, puh, kayt, salis, salis2, osas);
                lapi = true;
            }
        }
        return lapi;
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

        } else if (!(salis.matches(".*[a-z].*"))){
            // Tarkastaa sisältääkö salasana pienen kirjaimen
            salField.setBackgroundColor(Color.parseColor("#ff6464"));
            return false;

        } else {
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
}