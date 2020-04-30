package com.example.olioht;

public class ReservSlot {
    private int image;
    private String sport;
    private String gym;
    private String hall;
    private int maxUsers;
    private String date;
    private String time;
    private String enroll;
    private int rvID;

    private String color;

    public ReservSlot(String sport, String date, String time, int maxUsers, String gym, String hall, int rvID, String enroll, int image, String color) {
        this.sport = sport;
        this.gym = gym;
        this.date = date;
        this.hall = hall;
        this.image = image;
        this.time = time;
        this.maxUsers = maxUsers;
        this.enroll = enroll;
        this.color = color;
        this.rvID = rvID;
    }


    public int getImage() { return image; }

    public int getMaxUsers() { return maxUsers; }

    public String getTime() { return time;}

    public String getDate() { return date; }

    public String getGym() { return gym; }

    public String getHall() { return hall; }

    public String getSport() { return sport; }

    public String getEnroll() { return enroll; }

    public String getColor() { return color; }

    public int getRvID() { return rvID; }

}
