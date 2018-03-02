package com.example.dawakpharmacy.di.component;

import com.example.dawakpharmacy.di.PerActivity;
import com.example.dawakpharmacy.di.module.ActivityModule;
import com.example.dawakpharmacy.ui.MainActivity;
import com.example.dawakpharmacy.ui.login.LoginFragment;
import com.example.dawakpharmacy.ui.main.MainFragment;
import com.example.dawakpharmacy.ui.signup.SignUpFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(SignUpFragment fragment);

    void inject(LoginFragment fragment);

    void inject(MainFragment fragment);
}
