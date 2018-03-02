package com.example.dawakpharmacy.ui;

import android.os.Bundle;

import com.example.dawakpharmacy.R;
import com.example.dawakpharmacy.ui.base.BaseActivity;
import com.example.dawakpharmacy.ui.login.LoginFragment;
import com.example.dawakpharmacy.utils.FragmentUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
    }

    @Override
    protected void setUp() {
        getActivityComponent().inject(this);

        FragmentUtils.replaceFragment(getSupportFragmentManager(),
                LoginFragment.newInstance(), R.id.container, false);
    }
}
