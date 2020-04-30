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

public class ReservSlotAdapter extends RecyclerView.Adapter<ReservSlotAdapter.ReservSlotViewHolder>{
    private List<ReservSlot> reservSlotList;
    private Context context;

    public ReservSlotAdapter(List<ReservSlot> ReservSlotList, Context context) {
        this.reservSlotList = ReservSlotList;
        this.context = context;
    }

    @Override
    public ReservSlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View ReservSlotView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserv_card, parent, false);
        ReservSlotViewHolder gvh = new ReservSlotViewHolder(ReservSlotView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(ReservSlotViewHolder holder, final int position) {
        if (reservSlotList.get(position).getDate().equals("Ei varauksia tai ilmoittautumisia")) {
            holder.txtReservDate.setText(reservSlotList.get(position).getDate());
            holder.txtReservTime.setText(reservSlotList.get(position).getTime());
            holder.txtReservDate.setTextColor(Color.parseColor(reservSlotList.get(position).getColor()));
            return;
        }

        holder.txtReservDate.setText(reservSlotList.get(position).getDate() + "   /   " + reservSlotList.get(position).getTime());
        holder.txtReservTime.setText(reservSlotList.get(position).getHall() + "   /   " + reservSlotList.get(position).getGym());
        holder.txtReservSport.setText(reservSlotList.get(position).getSport() + "   /   " + reservSlotList.get(position).getEnroll());
        holder.txtReservSport.setTextColor(Color.parseColor(reservSlotList.get(position).getColor()));
        holder.image.setImageResource(reservSlotList.get(position).getImage());


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Tallennetaan valitun kortin tiedot muuttujiin
                String enroll = reservSlotList.get(position).getEnroll();
                String hall = reservSlotList.get(position).getHall();
                String gym = reservSlotList.get(position).getGym();
                String date = reservSlotList.get(position).getDate();
                String time = reservSlotList.get(position).getTime();
                String sport = reservSlotList.get(position).getSport();
                int rvID = reservSlotList.get(position).getRvID();
                int maxUsers =reservSlotList.get(position).getMaxUsers();


                //Jos kortti on varaus
                if(enroll.equals("Varaus")) {

                    //Tallennetaan muuttujat intenttiin ja käynnistetään varausluokka
                    Intent intent = new Intent(context, MyReservationActivity.class);
                    intent.putExtra("HALL", hall);
                    intent.putExtra("DATE", date);
                    intent.putExtra("GYM", gym);
                    intent.putExtra("TIME", time);
                    intent.putExtra("SPORT", sport);
                    intent.putExtra("RVID", rvID);
                    intent.putExtra("MAXUSERS", maxUsers);
                    context.startActivity(intent);

                    //Jos kortti on ilmoittautuminen
                } else if (enroll.equals("Ilmoittautuminen")) {

                    //Tallennetaan muuttujat intenttiin ja käynnistetään ilmoittautumisluokka
                    Intent intent = new Intent(context, MyEnrollActivity.class);
                    intent.putExtra("HALL", hall);
                    intent.putExtra("DATE", date);
                    intent.putExtra("GYM", gym);
                    intent.putExtra("TIME", time);
                    intent.putExtra("SPORT", sport);
                    intent.putExtra("RVID", rvID);
                    intent.putExtra("MAXUSERS", maxUsers);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservSlotList.size();
    }

    public class ReservSlotViewHolder extends RecyclerView.ViewHolder {
        TextView txtReservDate;
        TextView txtReservTime;
        TextView txtReservSport;
        ImageView image;
        public ReservSlotViewHolder(View view) {
            super(view);
            txtReservDate=view.findViewById(R.id.txt_reserv_slot_date);
            txtReservTime=view.findViewById(R.id.txt_reserv_slot_hall);
            txtReservSport=view.findViewById(R.id.txt_reserv_slot_sport);
            image=view.findViewById(R.id.reservImage);
        }
    }
}