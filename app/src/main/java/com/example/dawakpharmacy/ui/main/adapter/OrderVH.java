package com.example.dawakpharmacy.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dawakpharmacy.R;
import com.example.dawakpharmacy.model.Order;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Fakhry on 02/03/2018.
 */

class OrderVH extends RecyclerView.ViewHolder {

    @BindView(R.id.medicineNameTV)
    TextView medicineNameTV;
    @BindView(R.id.quantityTV)
    TextView quantityTV;
    @BindView(R.id.acceptL)
    View acceptL;
    @BindView(R.id.refuseL)
    View refuseL;

    public OrderVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Order order) {
        medicineNameTV.setText(order.getName());
        quantityTV.setText(String.valueOf(order.getQuantity()));

        if (order.getAvailable() == null) {
            itemView.setVisibility(View.VISIBLE);
        } else if (order.getAvailable()) {
            itemView.setVisibility(View.GONE);
        } else {
            itemView.setVisibility(View.GONE);
        }
    }
}
