package com.example.dawakpharmacy.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dawakpharmacy.R;
import com.example.dawakpharmacy.di.component.ActivityComponent;
import com.example.dawakpharmacy.model.Order;
import com.example.dawakpharmacy.ui.base.BaseFragment;
import com.example.dawakpharmacy.ui.main.adapter.OrderAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends BaseFragment implements MainContract.View, OrderAdapter.CallBack {

    @Inject
    MainContract.Presenter<MainContract.View> presenter;

    @Inject
    OrderAdapter adapter;
    @Inject
    ArrayList<Order> orders;

    @BindView(R.id.orderRV)
    RecyclerView orderRV;

    String userId;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    protected void setUp(View view) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
            SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            userId = preferences.getString("userId", null);
            presenter.getOrders(userId);
        }

        adapter.setLisener(this);
        orderRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        orderRV.setAdapter(adapter);
    }

    @Override
    public void onOrderAdded(Order order) {
        adapter.addOrder(order);
    }

    @Override
    public void accept(Order order) {
        presenter.acceptOrder(order, userId);
    }

    @Override
    public void refuse(Order order) {
        presenter.refuseOrder(order, userId);
    }
}
