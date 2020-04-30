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
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;

public class SelosteActivity extends AppCompatActivity {

    private TextView textfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seloste);

        textfield = findViewById(R.id.litanjaText);
        textfield.setMovementMethod(new ScrollingMovementMethod());

        String fill = "Tämä on vain harjoituksen omainen Yrityksen henkilötietolain (10 ja 24 §) ja EU:n yleisen tietosuoja-asetuksen (GDPR) mukainen rekisteri- ja tietosuojaseloste. Laadittu 09.04.2020. Viimeisin muutos 09.04.2020.\n" +
                " \n" +
                "1. Rekisterinpitäjä\n" +
                "\n" +
                "Hallinhaalija Oy, Tiekatu 15, 01234 Kaupunki\n" +
                " \n" +
                "2. Rekisteristä vastaava yhteyshenkilö\n" +
                "\n" +
                "Tite Goblin, titegoblin@hallinhaalija.com\n" +
                " \n" +
                "3. Rekisterin nimi\n" +
                "\n" +
                "Olio-ohjelmoinnin harjoitustyö\n" +
                " \n" +
                "4. Oikeusperuste ja henkilötietojen käsittelyn tarkoitus\n" +
                "\n" +
                "TÄMÄ ON VAIN HARJOITUSKÄYTTÖÖN. TÄTÄ SOVELLUSTA EI TULE KÄYTTÄÄ KAUPALLISEEN KÄYTTÖÖN." +
                "\n" +
                "EU:n yleisen tietosuoja-asetuksen mukainen oikeusperuste henkilötietojen käsittelylle on\n" +
                "- henkilön suostumus (dokumentoitu, vapaaehtoinen, yksilöity, tietoinen ja yksiselitteinen)\n" +
                "- sopimus, jossa rekisteröity on osapuolena\n" +
                "- laki (mikä)\n" +
                "- julkisen tehtävän hoitaminen (mihin perustuu), tai\n" +
                "- rekisterinpitäjän oikeutettu etu (esim. asiakassuhde, työsuhde, jäsenyys).\n" +
                "Henkilötietojen käsittelyn tarkoitus on yhteydenpito asiakkaisiin, asiakassuhteen ylläpito, markkinointi tms.\n" +
                "Tietoja ei käytetä automatisoituun päätöksentekoon tai profilointiin. \n" +
                " \n" +
                "5. Rekisterin tietosisältö\n" +
                "\n" +
                "Rekisteriin tallennettavia tietoja ovat: henkilön nimi, asema, yritys/organisaatio, yhteystiedot (puhelinnumero, sähköpostiosoite, osoite), www-sivustojen osoitteet, verkkoyhteyden IP-osoite, tunnukset/profiilit sosiaalisen median palveluissa, tiedot tilatuista palveluista ja niiden muutoksista, laskutustiedot, muut asiakassuhteeseen ja tilattuihin palveluihin liittyvät tiedot.\n" +
                "Kerro tässä myös tietojen säilytysaika, mikäli mahdollista. Kerro myös, jos tiedot esimerkiksi anonymisoidaan tietyn ajan kuluttua.\n" +
                " \n" +
                "6. Säännönmukaiset tietolähteet\n" +
                "\n" +
                "Rekisteriin tallennettavat tiedot saadaan asiakkaalta mm. www-lomakkeilla lähetetyistä viesteistä, sähköpostitse, puhelimitse, sosiaalisen median palvelujen kautta, sopimuksista, asiakastapaamisista ja muista tilanteista, joissa asiakas luovuttaa tietojaan.\n" +
                " \n" +
                "7. Tietojen säännönmukaiset luovutukset ja tietojen siirto EU:n tai ETA:n ulkopuolelle\n" +
                "\n" +
                "Tietoja ei luovuteta säännönmukaisesti muille tahoille. Tietoja voidaan julkaista siltä osin kuin niin on sovittu asiakkaan kanssa.\n" +
                "Tietoja voidaan siirtää rekisterinpitäjän toimesta myös EU:n tai ETA:n ulkopuolelle.\n" +
                "Mikäli luovutat henkilötietoja eri tahoille, kerro tässä mahdolliset vastaanottajat tai vastaanottajaryhmät.\n" +
                " \n" +
                "8. Rekisterin suojauksen periaatteet\n" +
                "\n" +
                "Rekisterin käsittelyssä noudatetaan huolellisuutta ja tietojärjestelmien avulla käsiteltävät tiedot suojataan asianmukaisesti. Kun rekisteritietoja säilytetään Internet-palvelimilla, niiden laitteiston fyysisestä ja digitaalisesta tietoturvasta huolehditaan asiaankuuluvasti. Rekisterinpitäjä huolehtii siitä, että tallennettuja tietoja sekä palvelimien käyttöoikeuksia ja muita henkilötietojen turvallisuuden kannalta kriittisiä tietoja käsitellään luottamuksellisesti ja vain niiden työntekijöiden toimesta, joiden työnkuvaan se kuuluu.\n" +
                " \n" +
                "9. Tarkastusoikeus ja oikeus vaatia tiedon korjaamista\n" +
                "\n" +
                "Jokaisella rekisterissä olevalla henkilöllä on oikeus tarkistaa rekisteriin tallennetut tietonsa ja vaatia mahdollisen virheellisen tiedon korjaamista tai puutteellisen tiedon täydentämistä. Mikäli henkilö haluaa tarkistaa hänestä tallennetut tiedot tai vaatia niihin oikaisua, pyyntö tulee lähettää kirjallisesti rekisterinpitäjälle. Rekisterinpitäjä voi pyytää tarvittaessa pyynnön esittäjää todistamaan henkilöllisyytensä. Rekisterinpitäjä vastaa asiakkaalle EU:n tietosuoja-asetuksessa säädetyssä ajassa (pääsääntöisesti kuukauden kuluessa).\n" +
                " \n" +
                "10. Muut henkilötietojen käsittelyyn liittyvät oikeudet\n" +
                "\n" +
                "Rekisterissä olevalla henkilöllä on oikeus pyytää häntä koskevien henkilötietojen poistamiseen rekisteristä (\"oikeus tulla unohdetuksi\"). Niin ikään rekisteröidyillä on muut EU:n yleisen tietosuoja-asetuksen mukaiset oikeudet kuten henkilötietojen käsittelyn rajoittaminen tietyissä tilanteissa. Pyynnöt tulee lähettää kirjallisesti rekisterinpitäjälle. Rekisterinpitäjä voi pyytää tarvittaessa pyynnön esittäjää todistamaan henkilöllisyytensä. Rekisterinpitäjä vastaa asiakkaalle EU:n tietosuoja-asetuksessa säädetyssä ajassa (pääsääntöisesti kuukauden kuluessa).";

        textfield.setText(fill);
    }

    public void openKaytieActivity(View view) {
        Intent intent = new Intent(this, KayttajatiedotActivity.class);
        startActivity(intent);
    }
    public void openMenuActivity(View v) {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void openAsetuksetActivity(View view) {
        finish();
    }

}
