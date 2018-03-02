package com.example.dawakpharmacy.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dawakpharmacy.R;
import com.example.dawakpharmacy.model.Order;

import java.util.ArrayList;


/**
 * Created by Mohamed Fakhry on 02/03/2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderVH> {

    ArrayList<Order> orders;
    CallBack lisener;

    public OrderAdapter(ArrayList<Order> orders) {
        this.orders = orders;
    }

    @Override
    public OrderVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_oreder, null));
    }

    @Override
    public void onBindViewHolder(final OrderVH holder, final int position) {
        holder.bind(orders.get(position));
        holder.acceptL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisener.accept(orders.get(position));
                orders.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.refuseL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lisener.refuse(orders.get(position));
                orders.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public void addOrder(Order order) {
        if (orders != null) {
            orders.add(order);
            notifyDataSetChanged();
        }
    }

    public void addOrders(ArrayList<Order> orders) {
        if (orders != null) {
            this.orders.addAll(orders);
            notifyDataSetChanged();
        }
    }

    public void setLisener(CallBack lisener) {
        this.lisener = lisener;
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public interface CallBack {
        void accept(Order order);
        void refuse(Order order);
    }
}
