package com.mind.goodstracker.DiliveryPerson;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mind.goodstracker.R;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private Context mCtx;
    ArrayList<ShowTrip> tripList;
    String source;

    public TripAdapter(Context mCtx, ArrayList<ShowTrip> tripList) {
        this.mCtx = mCtx;
        this.tripList = tripList;
    }



    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_trip,null);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TripViewHolder holder, final int position) {
       // holder.teacher.setText(blackboard.getCreator());
        holder.title.setText(tripList.get(position).getTitle());
//        holder.source.setText(tripList.get(position).getSource());
//        holder.dest.setText(tripList.get(position).getDest());
        holder.tripCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mCtx,StartTrip.class);
                i.putExtra("source",(tripList.get(position).getSource()));
                i.putExtra("destination",(tripList.get(position).getDest()));
                mCtx.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return tripList.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder{

        TextView title,source,dest;
        LinearLayout linearLayout;
        CardView tripCard;
        public TripViewHolder(@NonNull View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.textTitle);
            source=(TextView)itemView.findViewById(R.id.textSource);
            dest=(TextView)itemView.findViewById(R.id.textDest);
            tripCard=(CardView)itemView.findViewById(R.id.tripCard);
        }
    }

}
