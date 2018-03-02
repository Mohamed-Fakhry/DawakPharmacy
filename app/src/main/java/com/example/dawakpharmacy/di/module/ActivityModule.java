
package com.example.dawakpharmacy.di.module;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.dawakpharmacy.di.ActivityContext;
import com.example.dawakpharmacy.di.PerActivity;
import com.example.dawakpharmacy.model.Pharmacy;
import com.example.dawakpharmacy.ui.login.LoginContract;
import com.example.dawakpharmacy.ui.login.LoginPresenter;
import com.example.dawakpharmacy.ui.main.MainContract;
import com.example.dawakpharmacy.ui.main.MainPresenter;
import com.example.dawakpharmacy.model.Order;
import com.example.dawakpharmacy.ui.main.adapter.OrderAdapter;
import com.example.dawakpharmacy.ui.signup.SignUpContract;
import com.example.dawakpharmacy.ui.signup.SignUpPresenter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    SignUpContract.Presenter<SignUpContract.View> provideSignUpPresenter
            (SignUpPresenter<SignUpContract.View> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LoginContract.Presenter<LoginContract.View> provideLoginPresenter
            (LoginPresenter<LoginContract.View> presenter) {
        return presenter;
    }

    @Provides
    MainContract.Presenter<MainContract.View> provideMainPresenter
            (MainPresenter<MainContract.View> presenter) {
        return presenter;
    }

    @Provides
    OrderAdapter provideOrderAdapter(ArrayList<Order> orders) {
        return new OrderAdapter(orders);
    }

    @Provides
    ArrayList<Order> provideOrders() {
        return new ArrayList<>();
    }

    @Provides
    Pharmacy providePharmacy() {
        return new Pharmacy();
    }
}
