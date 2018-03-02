package com.example.dawakpharmacy.ui.login;


import com.example.dawakpharmacy.ui.base.MvpPresenter;
import com.example.dawakpharmacy.ui.base.MvpView;

public interface LoginContract {

    interface View extends MvpView {

        void openMainFragment();

        void saveUserId(String userId);
    }

    interface Presenter<V extends View> extends MvpPresenter<V> {

        void login(String email, String password);
    }
}