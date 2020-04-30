package com.example.olioht;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AjanvarausActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    private Spinner spinner1, spinner2, spinner;
    private Button button;
    int day;
    int month;
    int year;
    private int hallid;
    private int gymid;
    String date;

    //Otetaan tämän hetkinen päivämäärä muuttujaan myöhempää vertailua varten
    String current_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajanvaraus);
        myDb = new DatabaseHelper(this);

        addItemsOnSpinner1();
        addListenerOnButton();
        addItemsOnSportSpinner();
        addListenerOnSpinnerItemSelection();

        System.out.println(current_date);

        //Luodaan kalenteri ja asetetaan sille id
        CalendarView calendarView = findViewById(R.id.calendarView);

        //Saadaan käytäjän syöttämä päivämäärä
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                day = i2;
                month = i1 + 1;
                year = i;

                date = year + "-" + decimalFormat(month) + "-" + decimalFormat(day);

            }
        });

        //Oletuksena päivämääräarvot näyttävät nykyistä päivää
        day = Integer.parseInt(current_date.substring(8,10));
        System.out.println(day);

        month = Integer.parseInt(current_date.substring(6,7));
        System.out.println(month);

        year = Integer.parseInt(current_date.substring(0,4));
        System.out.println(year);

        date = String.valueOf(year) + "-" + decimalFormat(month) + "-" + decimalFormat(day);
        System.out.println(date);

    }

    //Haluamme päivämäärän tiettyssä muodossa, jotta se olisi vertailu kelpoinen
    public String decimalFormat(Integer date_part) {
        DecimalFormat df = new DecimalFormat("00");
        String value = df.format(date_part);
        return value;
    }

    //Auttaa muodostamaan käyttäjälle suunnattuja virheilmoituksia
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    // Lisätään spinneriin (1) sisältöä
    public void addItemsOnSpinner1() {

        Cursor res = myDb.getHallInfo();
        //Virheilmoitus tietokantahaun palauttaessa tyhjän
        if(res.getCount() == 0) {
            //show message
            showMessage("Error", "Nothing found");
            return;
        }

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();

        while (res.moveToNext()) {
            String HallName = res.getString(0);
            list.add(HallName);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        res.close();
    }

    // Lisätään spinneriin (2) sisältöä
    public void addItemsOnSpinner2(Integer hallid) {

        Cursor res = myDb.getGymInfo(hallid);
        //Virheilmoitus tietokantahaun palauttaessa tyhjän
        if(res.getCount() == 0) {
            //show message
            showMessage("Error", "Nothing found");
            return;
        }

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> spinner2list = new ArrayList<String>();
        spinner2list.clear();

        while (res.moveToNext()) {
            String GymName = res.getString(0);

            spinner2list.add(GymName);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinner2list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

        res.close();
    }

    // Lisätään spinneriin sisältöä lajeista
    public void addItemsOnSportSpinner() {

        Cursor res = myDb.getSportInfoAll();
        //Virheilmoitus tietokantahaun palauttaessa tyhjän
        if(res.getCount() == 0) {
            //show message
            showMessage("Error", "Nothing found");
            return;
        }

        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> SportList = new ArrayList<String>();

        SportList.add("Kaikki lajit"); // ÄLÄ KOSKE!

        while (res.moveToNext()) {
            String SportName = res.getString(0);
            SportList.add(SportName);
        }

        ArrayAdapter<String> dataAdapterSport = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, SportList);
        dataAdapterSport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapterSport);

        res.close();
    }

    //Täytetään salitiedot (spinner2) heti halli-spinnerin (spinner1) saadessa arvon
    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor res = myDb.getHallId(String.valueOf(spinner1.getSelectedItem()));

                //Virheilmoitus tietokantahaun palauttaessa tyhjän
                if(res.getCount() == 0) {
                    //show message
                    showMessage("Error", "Nothing found");
                    return;
                }

                while (res.moveToNext()) {
                    hallid = res.getInt(0);
                    addItemsOnSpinner2(hallid);
                }

                res.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    // Asetetaan näkymän painikkeelle toiminta
    public void addListenerOnButton() {

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                    Date current = sdformat.parse(current_date);
                    Date selected_date = sdformat.parse(date);

                    //Vertaillaan käyttäjän syöttämää päivämäärää nykyhetkeen
                    //Samalla käynnistetään käyttäjän syötettä vastaava toiminnallisuus
                    if(selected_date.compareTo(current) > 0) {
                        //Date 'selected_date' occurs after Date 'current'

                        String sport = String.valueOf(spinner.getSelectedItem());
                        String hall = String.valueOf(spinner1.getSelectedItem());
                        String gym = String.valueOf(spinner2.getSelectedItem());

                        Cursor res = myDb.getGymId(gym);
                        //Virheilmoitus tietokantahaun palauttaessa tyhjän
                        if(res.getCount() == 0) {
                            //show message
                            showMessage("Error", "Nothing found1");
                            return;
                        }

                        while (res.moveToNext()) {
                            gymid = res.getInt(0);
                        }

                        // SUODATUS LAJIN PERUSTEELLA
                        Integer sportid = 0;

                        if(sport.equals("Kaikki lajit")){
                            sportid = 0;
                        } else {

                            Cursor res1 = myDb.getSportID(sport);
                            //Virheilmoitus tietokantahaun palauttaessa tyhjän
                            if(res1.getCount() == 0) {
                                //show message
                                showMessage("Error", "Nothing found2");
                                return;
                            }

                            while (res1.moveToNext()) {
                                sportid = res1.getInt(0);
                            }
                        }

                        res.close();

                        //Käyttäjän oikeuksien tarkastaminen
                        RekisterointiTiedot rek = RekisterointiTiedot.getInstance(); // Alustus

                        int memberNumber = rek.member;

                        if (memberNumber == 0) {
                            Toast.makeText(AjanvarausActivity.this,
                                    "Ei oikeutta varata tai ilmoittautua aikoihin.",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        } else if (memberNumber == 1) {
                            if (hallid != 1) {
                                Toast.makeText(AjanvarausActivity.this,
                                        "Käyttöoikeutesi on toiselle hallille",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else if (memberNumber == 2) {
                            if (hallid != 2) {
                                Toast.makeText(AjanvarausActivity.this,
                                        "Käyttöoikeutesi on toiselle hallille",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        //Luodaan ja käynnistetään intentti, joka sisältää kerättyjä arvoja seuraavaa activity varten
                        Intent intent = new Intent(AjanvarausActivity.this, TimeSlotActivity.class);
                        intent.putExtra("HALL", hall);
                        intent.putExtra("GYM", gym);
                        intent.putExtra("SPORT", sport);
                        intent.putExtra("DAY", day);
                        intent.putExtra("MONTH", month);
                        intent.putExtra("YEAR", year);
                        intent.putExtra("DATE", date);
                        intent.putExtra("HALLID", hallid);
                        intent.putExtra("GYMID", gymid);
                        intent.putExtra("SPORTID", sportid);
                        startActivity(intent);

                    } else if(selected_date.compareTo(current) == 0) {
//                        Date 'selected_date' occurs before Date 'current'

                        Toast.makeText(AjanvarausActivity.this,
                                "Varauksen voi tehdä viimeistään edellisenä päivänä",
                                Toast.LENGTH_SHORT).show();

                    } else if(selected_date.compareTo(current) < 0) {
//                        Date 'selected_date' occurs before Date 'current'

                        Toast.makeText(AjanvarausActivity.this,
                                "Try looking forward and let go of the past!",
                                Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(AjanvarausActivity.this,
                                "Not able to read date values :(",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    public void openKaytieActivity(View view) {
        Intent intent = new Intent(this, KayttajatiedotActivity.class);
        startActivity(intent);
    }

    public void openAsetuksetActivity(View view) {
        Intent intent = new Intent(this, AsetuksetActivity.class);
        startActivity(intent);
    }
    public void openMenuActivity(View v) {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}