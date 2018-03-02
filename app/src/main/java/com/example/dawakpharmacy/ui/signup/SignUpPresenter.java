package com.example.dawakpharmacy.ui.signup;

import com.example.dawakpharmacy.R;
import com.example.dawakpharmacy.data.DataManager;
import com.example.dawakpharmacy.model.Pharmacy;
import com.example.dawakpharmacy.ui.base.BaseLisener;
import com.example.dawakpharmacy.ui.base.BasePresenter;
import com.example.dawakpharmacy.utils.CommonUtils;

import javax.inject.Inject;

public class SignUpPresenter<V extends SignUpContract.View>
        extends BasePresenter<V> implements SignUpContract.Presenter<V>, BaseLisener<String, String> {

    DataManager dataManager;

    @Inject
    Pharmacy pharmacy;

    @Inject
    public SignUpPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void signUp(Pharmacy pharmacy) {

        if (pharmacy.getName().isEmpty()) {
            getMvpView().showMessage(R.string.error_pharmacy_name_empty);
            return;
        }

        if (pharmacy.getEmail().isEmpty()) {
            getMvpView().showMessage(R.string.error_email_required);
            return;
        } else if (!CommonUtils.isEmailValid(pharmacy.getEmail())) {
            getMvpView().showMessage(R.string.error_invalid_email);
            return;
        }

        if (pharmacy.getPassword().isEmpty()) {
            getMvpView().showMessage(R.string.error_password_empty);
            return;
        }

        if (getMvpView().isNetworkConnected()) {
            getMvpView().showLoading();
            this.pharmacy = pharmacy;
            dataManager.signUp(pharmacy.getEmail(), pharmacy.getPassword(), this);
        } else {
            getMvpView().showMessage(R.string.error_no_iternet_connection);
        }
    }

    @Override
    public void onSuccess(String userId) {
        getMvpView().hideLoading();
        if (getMvpView().isNetworkConnected()) {
            pharmacy.setId(userId);
            dataManager.setPharmacy(pharmacy);
        }
        if (isViewAttached())
            getMvpView().onSignUpSuccess();
    }

    @Override
    public void onFail(String error) {
        if (isViewAttached()) {
            getMvpView().hideLoading();
            getMvpView().showMessage(error);
        }
    }
}
