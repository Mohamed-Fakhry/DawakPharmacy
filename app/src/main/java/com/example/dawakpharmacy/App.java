package com.example.dawakpharmacy;

import android.app.Application;

import com.example.dawakpharmacy.data.DataManager;
import com.example.dawakpharmacy.di.component.ApplicationComponent;
import com.example.dawakpharmacy.di.component.DaggerApplicationComponent;
import com.example.dawakpharmacy.di.module.ApplicationModule;

import javax.inject.Inject;


/**
 * Created by Mohamed Fakhry on 11/02/2018.
 */

public class App extends Application {

    @Inject
    DataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
