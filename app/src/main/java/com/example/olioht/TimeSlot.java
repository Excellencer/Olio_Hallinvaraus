package com.example.olioht;

public class TimeSlot {

    private String timeSlot;
    private String enrollments;
    private String hall;
    private String gym;
    private String date;

    private int day;
    private int month;
    private int year;
    private int hallid;
    private int gymid;
    private int image;

    private String resUserName;
    private String resUserPhone;
    private String sportName;
    private String maxuser;
    private String state;
    private String color;

    public TimeSlot(String timeSlot, String hall, String gym, String date, int day, int month, int year, int hallid, int gymid, String state, int image, String color, String resUserName, String resUserPhone, String sportName, String maxuser, String enrollments) {
        this.image = image;
        this.timeSlot = timeSlot;
        this.hall = hall;
        this.gym = gym;
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hallid = hallid;
        this.gymid = gymid;
        this.state = state;
        this.color = color;

        this.resUserName = resUserName;
        this.resUserPhone = resUserPhone;
        this.sportName = sportName;
        this.maxuser = maxuser;
        this.enrollments = enrollments;

    }

    public int getImage() {
        return image;
    }

    public String getTimeSlotName() {
        return timeSlot;
    }

    public String getHall() { return hall; }

    public String getGym() { return gym; }

    public String getDate() { return date; }

    public int getDay() { return day; }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    public int getHallId() { return hallid; }

    public int getGymId() { return gymid; }

    public String getState() {
        return state;
    }

    public String getColor() { return color; }

    public String getResUserName() {
        return resUserName;
    }

    public String getResUserPhone() {
        return resUserPhone;
    }

    public String getSportName() {
        return sportName;
    }

    public String getMaxuser() {
        return maxuser;
    }

    public String getEnrollments() {
        return enrollments;
    }
}