package com.example.olioht;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "hallit.db";
    public static final String TABLE_NAME = "halli_table";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SIZE INTEGER)");

        db.execSQL("CREATE TABLE 'User' (userID INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR(50) UNIQUE, firstName VARCHAR(50), lastName VARCHAR(50), phone VARCHAR(50), member INTEGER, adminBool INTEGER, password VARCHAR(200))");

        db.execSQL("CREATE TABLE 'Hall' (hallID INTEGER PRIMARY KEY AUTOINCREMENT, hallName VARCHAR(50) UNIQUE)");

        db.execSQL("CREATE TABLE 'Gym' (gymID INTEGER PRIMARY KEY AUTOINCREMENT, gymName VARCHAR(50) UNIQUE, hallID INTEGER NOT NULL REFERENCES 'Hall'(hallID) ON DELETE CASCADE)");

        db.execSQL("CREATE TABLE 'Sport' (sportID INTEGER PRIMARY KEY AUTOINCREMENT, SPORTNAME VARCHAR(50) UNIQUE NOT NULL)");

        db.execSQL("CREATE TABLE 'Reservation' (rvID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER NOT NULL REFERENCES 'User'(userID) ON DELETE CASCADE, rvDate DATETIME, rvTime VARCHAR(50), maxUsers INTEGER, sport INTEGER NOT NULL REFERENCES 'Sport' (sportID) ON DELETE CASCADE, gymID INTEGER NOT NULL REFERENCES 'Gym'(gymID) ON DELETE CASCADE)");

        db.execSQL("CREATE TABLE 'Enroll' (enrollID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER NOT NULL REFERENCES 'User'(userID) ON DELETE CASCADE, rvID INTEGER NOT NULL REFERENCES 'Reservation'(rvID) ON DELETE CASCADE)");

        db.execSQL("PRAGMA FOREIGN_KEYS = 1");

        db.execSQL("INSERT INTO 'Sport' (SPORTNAME) VALUES ('Ei lajia')");
        db.execSQL("INSERT INTO 'Sport' (SPORTNAME) VALUES ('Muu laji')");
        db.execSQL("INSERT INTO 'Sport' (SPORTNAME) VALUES ('koripallo')");
        db.execSQL("INSERT INTO 'Sport' (SPORTNAME) VALUES ('jalkapallo')");

        db.execSQL("INSERT INTO User (email, firstName, lastName, phone, member, adminBool, password) VALUES ('Kalle42', 'Kalle', 'Liljeström', '04005529393', 1, 0, 'd51914e7ec0903d79b7c0427ffa99cc2f07548986926a65dbfca1a527f32697ff649a832f94bf840c8253b8e2568858209ee3fa8b3e2ce54b744bb3965c6161f')");
        db.execSQL("INSERT INTO User (email, firstName, lastName, phone, member, adminBool, password) VALUES ('UrheiluHullu2', 'Simo', 'Silakka', '04435325325', 1, 0, '75c41aa5eb6bfc9fab0474d3a9731cfb8c883c02ceda490c31389e03bbb8802b0df600d4e4912210f2ddae2a8932bc8d4b953fb4e494cbae85c6361f6f685eef')");


        db.execSQL("INSERT INTO Hall  (hallName) VALUES ('Halli 1')");
        db.execSQL("INSERT INTO Hall  (hallName) VALUES ('Halli 2')");
        db.execSQL("INSERT INTO Gym  (gymName, hallID) VALUES ('Pieni Sali', 1)");
        db.execSQL("INSERT INTO Gym  (gymName, hallID) VALUES ('Iso Sali', 1)");
        db.execSQL("INSERT INTO Gym  (gymName, hallID) VALUES ('Keskikokoinen Sali', 2)");
        db.execSQL("INSERT INTO Gym  (gymName, hallID) VALUES ('Suuri Sali', 2)");

        db.execSQL("INSERT INTO Reservation (userID, rvDate, rvTime, maxUsers, sport, gymID) VALUES (1, '2020-05-01', '8:00-9:00', 10, 1, 1)");
        db.execSQL("INSERT INTO Reservation (userID, rvDate, rvTime, maxUsers, sport, gymID) VALUES (1, '2020-05-01', '13:00-13:00', 15, 4, 1)");
        db.execSQL("INSERT INTO Reservation (userID, rvDate, rvTime, maxUsers, sport, gymID) VALUES (1, '2020-04-30', '15:00-16:00', 15, 4, 1)");


        db.execSQL("INSERT INTO Reservation (userID, rvDate, rvTime, maxUsers, sport, gymID) VALUES (1, '2020-09-24', '11:00-12:00', 20, 2, 1)");
        db.execSQL("INSERT INTO Reservation (userID, rvDate, rvTime, maxUsers, sport, gymID) VALUES (1, '2020-09-25', '8:00-9:00', 5, 3, 1)");
        db.execSQL("INSERT INTO Reservation (userID, rvDate, rvTime, maxUsers, sport, gymID) VALUES (2, '2020-05-01', '11:00-12:00', 5, 3, 1)");
        db.execSQL("INSERT INTO Reservation (userID, rvDate, rvTime, maxUsers, sport, gymID) VALUES (2, '2020-05-01', '13:00-14:00', 5, 4, 1)");

        db.execSQL("INSERT INTO Enroll (userID, rvID) VALUES (2, 1)");
        db.execSQL("INSERT INTO Enroll (userID, rvID) VALUES (1, 5)");
        /*
        db.insertReservationData(1, "2020-04-29", "8:00-9:00", 10, 1, 1);
        db.insertReservationData(1, "2020-09-24", "8:00-9:00", 10, 1, 1);
        db.insertReservationData(1, "2020-09-25", "8:00-9:00", 10, 1, 1);
        db.insertReservationData(1, "2020-06-26", "8:00-9:00", 10, 1, 1);
        db.insertReservationData(2, "2020-06-27", "8:00-9:00", 10, 1, 2);
        db.insertReservationData(2, "2020-06-28", "8:00-9:00", 10, 1, 2);
        db.insertReservationData(2, "2020-06-02", "8:00-9:00", 10, 1, 2);

        db.insertEnrollmentData(1, 4);
        db.insertEnrollmentData(1, 5);
        db.insertEnrollmentData(2, 1);
        db.insertEnrollmentData(2, 2);
        db.insertEnrollmentData(2, 3);
        db.insertEnrollmentData(1, 6);
        db.insertEnrollmentData(1, 1);

         */

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public Cursor getHallInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select HALLNAME from HALL", null);
        return res;
    }

    public Cursor getHallId(String hallName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select HALLID from HALL WHERE HALLNAME LIKE '%" + hallName + "%'", null);
        return res;
    }

    public Cursor getGymId(String gymName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select GYMID from GYM WHERE GYMNAME LIKE '%" + gymName + "%'", null);
        return res;
    }

    public Cursor getRvUsIdMax(String resDate, String timeSlot, Integer gymID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select RVID, USERID, MAXUSERS from RESERVATION WHERE RVDATE LIKE '%" + resDate + "%' AND RVTIME LIKE '%" + timeSlot + "%' AND GYMID = " + gymID, null);
        return res;
    }
    public Cursor getUserInfoAll(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from USER WHERE userID = " + id, null);
        return res;
    }

    public Cursor getUserInfo(Integer userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select FIRSTNAME, PHONE FROM USER WHERE USERID = " + userID, null);
        return res;
    }

    public Cursor getEnrollmentInfo(Integer rvID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select USERID FROM ENROLL WHERE RVID = " + rvID, null);
        return res;
    }

    public Cursor getGymInfo(Integer hallid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select GYMNAME from GYM WHERE HALLID = " + hallid, null);
        return res;
    }

    public Cursor getTimeSlotData(Integer hallID, Integer gymID, String resDate, String timeSlot) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT RVID, USERID, SPORT, MAXUSERS FROM RESERVATION WHERE RVDATE LIKE '%" + resDate + "%' AND RVTIME LIKE '%" + timeSlot + "%' AND GYMID = " + gymID, null);
        return res;
    }

    public Cursor getSportData(Integer sportID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT SPORTNAME FROM SPORT WHERE SPORTID = " + sportID, null);
        return res;
    }

    public Cursor getSportInfoAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT SPORTNAME FROM SPORT", null);
        return res;
    }

    public Cursor getSportID(String sportname) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT SPORTID FROM SPORT WHERE SPORTNAME LIKE '%" + sportname + "%'", null);
        return res;
    }

    public Cursor getTimeSlotDataForSport(Integer sportID, Integer gymID, String resDate, String timeSlot) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT RVTIME FROM RESERVATION WHERE RVDATE LIKE '%" + resDate + "%' AND RVTIME LIKE '%" + timeSlot + "%' AND GYMID = " + gymID + " AND sport =" + sportID, null);
        return res;
    }

    public void updateData(String etu, String suku, String puh, String kayt, String salis, Integer osas, Integer kayID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",kayt);
        contentValues.put("firstName",etu);
        contentValues.put("lastName",suku);
        contentValues.put("phone",puh);
        contentValues.put("member",osas);
        contentValues.put("password",salis);
        db.update("User", contentValues, "userID=?", new String[]{String.valueOf(kayID)});
    }

    public boolean insertUserData(String email, String firstname, String lastname, String phone, Integer member, Integer adminBool, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("firstName",firstname);
        contentValues.put("lastName",lastname);
        contentValues.put("phone",phone);
        contentValues.put("member",member);
        contentValues.put("password",password);
        long result = db.insert("User",null,contentValues);

        if(result == -1)
            return false;
        else
            return true;

    }

    public boolean insertHallData(String hallname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HALLNAME",hallname);
        long result = db.insert("Hall",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertGymData(String gymname, Integer hallid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("GYMNAME",gymname);
        contentValues.put("HALLID",hallid);
        long result = db.insert("Gym",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertReservationData(Integer userID, String rvDate, String rvTime, Integer maxUsers, Integer sport, Integer gymID) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERID",userID);
        contentValues.put("RVDATE",rvDate);
        contentValues.put("RVTIME",rvTime);
        contentValues.put("MAXUSERS",maxUsers);
        contentValues.put("SPORT",sport);
        contentValues.put("GYMID",gymID);
        long result = db.insert("Reservation",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertEnrollmentData(Integer userID, Integer rvID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("USERID",userID);
        contentValues.put("RVID",rvID);
        long result = db.insert("Enroll",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Integer login(String kayttaja, String salasana) {
        int kaytID = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT userID FROM USER WHERE email LIKE '%" + kayttaja + "%' AND password LIKE '%" + salasana + "%'", null);

        if (res.moveToFirst()) {
            kaytID = Integer.parseInt(res.getString(0));
        } else {
            kaytID = -1;
        }
        res.close();
        return kaytID;
    }

    public Cursor getSize(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select SIZE from " + TABLE_NAME + " WHERE NAME LIKE '%" + name + "%'", null);
        return res;

    }


    public boolean insertSportData(String sportname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SPORTNAME",sportname);
        long result = db.insert("Sport",null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<ReservSlot> getOwnReservations(Integer userID) {

        SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy");

        ArrayList<ReservSlot> rReservList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sportName,  rvDate, rvTime, maxUsers, gymName, hallName, Reservation.rvID FROM Reservation INNER JOIN Sport ON Sport.sportID = Reservation.sport INNER JOIN Gym ON Gym.gymID = Reservation.gymID INNER JOIN Hall ON Hall.hallID = Gym.hallID WHERE userID = " + userID + " AND rvDate >= date('now', 'localtime') ORDER BY rvDate", null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    rReservList.add(new ReservSlot(cursor.getString(0), newFormat.format(oldFormat.parse(cursor.getString(1))), cursor.getString(2), Integer.parseInt(cursor.getString(3)), cursor.getString(4), cursor.getString(5), Integer.parseInt(cursor.getString(6)),"Varaus", R.drawable.invisible_69dp, "#0087FF"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        cursor = db.rawQuery("SELECT sportName,  rvDate, rvTime, maxUsers, gymName, hallName, Reservation.rvID FROM Reservation INNER JOIN Sport ON Sport.sportID = Reservation.sport INNER JOIN Gym ON Gym.gymID = Reservation.gymID INNER JOIN Hall ON Hall.hallID = Gym.hallID INNER JOIN Enroll ON Enroll.rvID = Reservation.rvID WHERE  rvDate >= dateTime('now', 'localtime') AND Enroll.userID =" + userID + " ORDER BY rvDate", null);
                    if (cursor.moveToFirst()) {
                        do {
                            try {
                    rReservList.add(new ReservSlot(cursor.getString(0), newFormat.format(oldFormat.parse(cursor.getString(1))), cursor.getString(2), Integer.parseInt(cursor.getString(3)), cursor.getString(4), cursor.getString(5) ,Integer.parseInt(cursor.getString(6)),"Ilmoittautuminen", R.drawable.invisible_69dp, "#009448"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        return rReservList;
    }

    public void updateReservation(Integer rvID, Integer sportID, Integer maxUsers){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MaxUsers", maxUsers);
        contentValues.put("sport", sportID);
        db.update("Reservation", contentValues, "rvID=?", new String[]{String.valueOf(rvID)});
    }

    public Cursor getUserInfoRV(Integer rvID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT firstName, lastName, phone FROM USER INNER JOIN Reservation ON Reservation.userID = user.userID WHERE rvID =" + rvID, null);
        return res;
    }

    public void deleteReservation(Integer rvID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Reservation", "rvID=?", new String[]{String.valueOf(rvID)});
    }

    public void deleteEnrollment(Integer userID, Integer rvID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Enroll", "userID =" + userID + " AND rvID = " + rvID, null);
    }

}
