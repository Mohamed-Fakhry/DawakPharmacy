package com.example.dawakpharmacy.ui.main;


import com.example.dawakpharmacy.R;
import com.example.dawakpharmacy.data.DataManager;
import com.example.dawakpharmacy.model.Order;
import com.example.dawakpharmacy.ui.base.BaseLisener;
import com.example.dawakpharmacy.ui.base.BasePresenter;

import javax.inject.Inject;

public class MainPresenter<V extends MainContract.View> extends BasePresenter<V>
        implements MainContract.Presenter<V>, BaseLisener<Order, String> {

    DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getOrders(String userId) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            dataManager.getOrders(userId, this);
        } else {
            getMvpView().showMessage(R.string.error_no_iternet_connection);
        }
    }

    @Override
    public void acceptOrder(Order order, String userId) {
        if (getMvpView().isNetworkConnected()) {
            dataManager.acceptOrder(order, userId);
        } else {
            getMvpView().showMessage(R.string.error_no_iternet_connection);
        }
    }

    @Override
    public void refuseOrder(Order order, String userId) {
        if (getMvpView().isNetworkConnected()) {
            dataManager.refuseOrder(order, userId);
        } else {
            getMvpView().showMessage(R.string.error_no_iternet_connection);
        }
    }

    @Override
    public void onSuccess(Order order) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().onOrderAdded(order);
        }
    }

    @Override
    public void onFail(String error) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().showMessage(error);
        }
    }
}