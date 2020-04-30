package com.example.olioht;

public class Common {

    public static String convertTimeSlotToString(int slot) {
        switch (slot)
        {
            case  0:
                return "7:00-8:00";
            case  1:
                return "8:00-9:00";
            case  2:
                return "9:00-10:00";
            case  3:
                return "10:00-11:00";
            case  4:
                return "11:00-12:00";
            case  5:
                return "12:00-13:00";
            case  6:
                return "13:00-14:00";
            case  7:
                return "14:00-15:00";
            case  8:
                return "15:00-16:00";
            case  9:
                return "16:00-17:00";
            case  10:
                return "17:00-18:00";
            case  11:
                return "18:00-19:00";
            case  12:
                return "19:00-20:00";
            default:
                return "Suljettu";
        }
    }
}