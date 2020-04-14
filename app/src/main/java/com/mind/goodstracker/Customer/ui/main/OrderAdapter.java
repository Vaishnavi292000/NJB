package com.mind.goodstracker.Customer.ui.main;

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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context mCtx;
    ArrayList<ShowOrder> orderArrayList;

    public OrderAdapter(Context mCtx, ArrayList<ShowOrder> orderArrayList) {
        this.mCtx = mCtx;
        this.orderArrayList = orderArrayList;
    }



    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_customer,null);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, final int position) {
       // holder.teacher.setText(blackboard.getCreator());
        holder.title.setText(orderArrayList.get(position).getTitle());
        holder.product.setText(orderArrayList.get(position).getProductName());
        holder.orderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mCtx,OrderIdQRCode.class);
                i.putExtra("orderid",(orderArrayList.get(position).getId()));
                mCtx.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView title,product;
        LinearLayout linearLayout;
        CardView orderCard;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.textTitle);
            product=(TextView)itemView.findViewById(R.id.textProduct);
            orderCard=(CardView)itemView.findViewById(R.id.orderCard);
        }
    }

}
