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

import android.database.Cursor;

class RekisterointiTiedot {

    private static RekisterointiTiedot rek = new RekisterointiTiedot(); // Singleton
    String etunimi, sukunimi, puhelinnumero, kayttajatunnus, salasana, salasanaVarmistus, osasto;
    Integer member, adminBool, kayttajaID;
    static RekisterointiTiedot getInstance(){ return rek; }

    private RekisterointiTiedot(){


        etunimi = "";
        sukunimi = "";
        puhelinnumero = "";
        kayttajatunnus = "";
        salasana = "";
        salasanaVarmistus = "";
        osasto = "";
        kayttajaID = -1;
        member = 0;
        adminBool = 0;
    }

    void sailoTiedot(String etu, String suku, String puh, String kayt, String salis, String salis2, String osas){

        etunimi = etu;
        sukunimi = suku;
        puhelinnumero = puh;
        kayttajatunnus = kayt;
        salasana = salis;
        salasanaVarmistus = salis2;
        osasto = osas;

    }

    void setTiedot(String etu, String suku, String puh, String kayt, DatabaseHelper myDb){

        etunimi = etu;
        sukunimi = suku;
        puhelinnumero = puh;
        kayttajatunnus = kayt;
        updateDB(myDb); // Päivittää tiedot tietokantaan.

    }

    void updatePW(String salis, String salis2){ // Päivittää salasanan, jos sitä on muutettu.
        salasana = salis;
        salasanaVarmistus = salis2;
    }

    void updateDB(DatabaseHelper myDb){

        // Käyttäjätiedot näkymästä päivittää tiedot tietokantaan.
        myDb.updateData(etunimi, sukunimi, puhelinnumero, kayttajatunnus, salasana, member, kayttajaID);

    }

    void uploadDB(DatabaseHelper myDb){

        if(osasto.equals("Ei jäsenyyttä")){
            member = 0;
        }
        else if(osasto.equals("Halli 1")){
            member = 1;
        }
        else if(osasto.equals("Halli 2")){
            member = 2;
        }
        else if(osasto.equals("Hallit 1 & 2")){
            member = 3;
        } else {
            member = 0;
        }
        // Rekisteröinti näkymästä luo uuden tilin tiedot tietokantaan.
        myDb.insertUserData(kayttajatunnus, etunimi, sukunimi, puhelinnumero, member, adminBool, salasana);
    }

    void downloadUserInfo(DatabaseHelper myDb){

        // Kirjautuessa sisään lataa käyttäjätiedot tähän olioon.
        Cursor res = myDb.getUserInfoAll(kayttajaID);
        if(res.getCount() == 0) {
            res.close();
            return;
        }

        while (res.moveToNext()) {
            kayttajatunnus = res.getString(1);
            etunimi = res.getString(2);
            sukunimi = res.getString(3);
            puhelinnumero = res.getString(4);
            member = res.getInt(5);
            adminBool = res.getInt(6);
            salasana = res.getString(7);

            if(member == 0){
                osasto = "Ei jäsenyyttä";
            }
            else if(member == 1){
                osasto = "Halli 1";
            }
            else if(member == 2){
                osasto = "Halli 2";
            }
            else if(member == 3){
                osasto = "Hallit 1 & 2";
            }
        }
        res.close();
    }


    public void clearData() {
        etunimi = "";
        sukunimi = "";
        puhelinnumero = "";
        kayttajatunnus = "";
        salasana = "";
        salasanaVarmistus = "";
        osasto = "";
        kayttajaID = -1;
        member = 0;
        adminBool = 0;
    }
}
