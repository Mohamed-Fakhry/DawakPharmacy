package com.example.dawakpharmacy.ui.main;


import com.example.dawakpharmacy.model.Order;
import com.example.dawakpharmacy.ui.base.MvpPresenter;
import com.example.dawakpharmacy.ui.base.MvpView;

public interface MainContract {

    interface View extends MvpView {
        void onOrderAdded(Order order);
    }

    interface Presenter<V extends View> extends MvpPresenter<V> {
        void getOrders(String userId);

        void acceptOrder(Order order, String userId);

        void refuseOrder(Order order, String userId);
    }
}