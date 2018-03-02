package com.example.dawakpharmacy.ui.login;


import com.example.dawakpharmacy.R;
import com.example.dawakpharmacy.data.DataManager;
import com.example.dawakpharmacy.ui.base.BaseLisener;
import com.example.dawakpharmacy.ui.base.BasePresenter;

import javax.inject.Inject;

public class LoginPresenter<V extends LoginContract.View> extends BasePresenter<V> implements LoginContract.Presenter<V>,BaseLisener<String,String> {

    DataManager dataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void login(String email, String password) {
        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            dataManager.login(email, password, this);
        } else  {
            getMvpView().showMessage(R.string.error_no_iternet_connection);
        }
    }

    @Override
    public void onSuccess(String data) {
        if (isViewAttached()){
            getMvpView().hideLoading();
            getMvpView().saveUserId(data);
            getMvpView().openMainFragment();
        }
    }

    @Override
    public void onFail(String error) {
        if (isViewAttached()){
            getMvpView().hideLoading();
            getMvpView().showMessage(error);
        }
    }
}