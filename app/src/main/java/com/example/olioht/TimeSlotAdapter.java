package com.example.olioht;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder>{
    private List<TimeSlot> timeSlotList;
    Context context;

    public TimeSlotAdapter(List<TimeSlot> timeSlotList, Context context) {
        this.timeSlotList = timeSlotList;
        this.context = context;
    }

    @Override
    public TimeSlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View TimeSlotView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_time_card, parent, false);
        TimeSlotViewHolder gvh = new TimeSlotViewHolder(TimeSlotView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(TimeSlotViewHolder holder, final int position) {

        //Haetaan ja asetetaan kortin sijainnin perusteella sille kuuluvat arvot
        holder.txtTimeSlot.setText(timeSlotList.get(position).getTimeSlotName());
        holder.txtState.setText(timeSlotList.get(position).getState());
        holder.txtState.setTextColor(Color.parseColor(timeSlotList.get(position).getColor()));
        holder.image.setImageResource(timeSlotList.get(position).getImage());
        holder.txtEnroll.setText(timeSlotList.get(position).getEnrollments() + timeSlotList.get(position).getMaxuser());
        holder.txtSportName.setText(timeSlotList.get(position).getSportName());

        //Kortin toiminnallisuus aktivoituu käyttäjän klikatessa
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Tallennetaan valitun aikakortin tiedot muuttujiin
                String state = timeSlotList.get(position).getState();
                String timeSlotName = timeSlotList.get(position).getTimeSlotName();
                String hall = timeSlotList.get(position).getHall();
                String gym = timeSlotList.get(position).getGym();
                String date = timeSlotList.get(position).getDate();
                int day = timeSlotList.get(position).getDay();
                int month = timeSlotList.get(position).getMonth();
                int year = timeSlotList.get(position).getYear();
                int hallid = timeSlotList.get(position).getHallId();
                int gymid = timeSlotList.get(position).getGymId();

                //Jos aikakortin tila on "vapaa"
                if(state.equals("Vapaa")) {

                    //Tallennetaan muuttujat intenttiin ja käynnistetään varausluokka
                    Intent intent = new Intent(context, ReservationActivity.class);
                    intent.putExtra("TIME", timeSlotName);
                    intent.putExtra("HALL", hall);
                    intent.putExtra("DATE", date);
                    intent.putExtra("GYM", gym);
                    intent.putExtra("DAY", day);
                    intent.putExtra("MONTH", month);
                    intent.putExtra("YEAR", year);
                    intent.putExtra("HALLID", hallid);
                    intent.putExtra("GYMID", gymid);
                    context.startActivity(intent);

                    //Jos aikakortin tila on "varattu"
                } else if (state.equals("Varattu")) {
                    String resUserName = timeSlotList.get(position).getResUserName();
                    String resUserPhone = timeSlotList.get(position).getResUserPhone();

                    //Tallennetaan muuttujat intenttiin ja käynnistetään ilmoittautumisluokka
                    Intent intent = new Intent(context, EnrollActivity.class);
                    intent.putExtra("TIME", timeSlotName);
                    intent.putExtra("HALL", hall);
                    intent.putExtra("DATE", date);
                    intent.putExtra("GYM", gym);
                    intent.putExtra("DAY", day);
                    intent.putExtra("MONTH", month);
                    intent.putExtra("YEAR", year);
                    intent.putExtra("HALLID", hallid);
                    intent.putExtra("GYMID", gymid);
                    intent.putExtra("RESUSERNAME", resUserName);
                    intent.putExtra("RESUSERPHONE", resUserPhone);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }

    //Asetetaan näkymille oikeat ID:t
    public class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        TextView txtTimeSlot;
        TextView txtState;
        TextView txtSportName;
        TextView txtEnroll;
        ImageView image;
        public TimeSlotViewHolder(View view) {
            super(view);
            txtTimeSlot=view.findViewById(R.id.txt_time_slot);
            txtState=view.findViewById(R.id.txt_time_slot_description);
            txtState.setTextColor(Color.parseColor("#FFF44336"));
            txtSportName=view.findViewById(R.id.txt_time_slot_sportName);
            txtEnroll=view.findViewById(R.id.txt_time_slot_enroll);
            image=view.findViewById(R.id.idImage);
        }
    }
}