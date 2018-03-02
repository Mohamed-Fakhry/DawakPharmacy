package com.example.dawakpharmacy.ui.login;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dawakpharmacy.R;
import com.example.dawakpharmacy.di.component.ActivityComponent;
import com.example.dawakpharmacy.ui.base.BaseFragment;
import com.example.dawakpharmacy.ui.main.MainFragment;
import com.example.dawakpharmacy.ui.signup.SignUpFragment;
import com.example.dawakpharmacy.utils.FragmentUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginContract.View {

    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.passwordET)
    EditText passwordET;

    @Inject
    LoginContract.Presenter<LoginContract.View> presenter;

    public LoginFragment() {}

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        setUnBinder(ButterKnife.bind(this, view));
    }

    @OnClick(R.id.signInB)
    public void signIn() {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        presenter.login(email, password);
    }

    @OnClick(R.id.signUpL)
    public void navigateToSignUp() {
        FragmentUtils.replaceFragment(getActivity().getSupportFragmentManager(),
                SignUpFragment.newInstance(), R.id.container, true);
    }

    @Override
    public void openMainFragment() {
        FragmentUtils.replaceFragment(getActivity().getSupportFragmentManager(),
                MainFragment.newInstance(), R.id.container, false);
    }

    @Override
    public void saveUserId(String userId) {
        SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        preferences.edit().putString("userId", userId).apply();
    }
}
