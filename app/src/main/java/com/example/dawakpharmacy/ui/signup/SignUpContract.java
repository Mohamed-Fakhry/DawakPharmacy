package com.example.dawakpharmacy.ui.signup;

import com.example.dawakpharmacy.model.Pharmacy;
import com.example.dawakpharmacy.ui.base.MvpPresenter;
import com.example.dawakpharmacy.ui.base.MvpView;

public interface SignUpContract {

    interface View extends MvpView {
        void onSignUpSuccess();

        void onSignUpFail();
    }

    interface Presenter<V extends View> extends MvpPresenter<V> {
        void signUp(Pharmacy pharmacy);
    }
}
