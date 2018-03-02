package com.example.dawakpharmacy.data;


import android.support.annotation.NonNull;

import com.example.dawakpharmacy.data.firebase.AppFirebaseHelper;
import com.example.dawakpharmacy.data.firebase.FirebaseHelper;
import com.example.dawakpharmacy.model.Pharmacy;
import com.example.dawakpharmacy.ui.base.BaseLisener;
import com.example.dawakpharmacy.model.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Mohamed Fakhry on 18/02/2018.
 */
@Singleton
public class AppDataManager implements DataManager {

    FirebaseHelper firebaseAppHelper;

    @Inject
    public AppDataManager(AppFirebaseHelper firebaseAppHelper) {
        this.firebaseAppHelper = firebaseAppHelper;
    }

    @Override
    public void signUp(String email, String password, final BaseLisener<String, String> lisener) {
        firebaseAppHelper.signUp(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    lisener.onSuccess(task.getResult().getUser().getUid());
                else
                    lisener.onFail(task.getException().getMessage());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                lisener.onFail(e.getMessage());
            }
        });
    }

    @Override
    public void login(String email, String password, final BaseLisener<String, String> lisener) {
        firebaseAppHelper.login(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    lisener.onSuccess(task.getResult().getUser().getUid());
                else
                    lisener.onFail(task.getException().getMessage());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                lisener.onFail(e.getMessage());
            }
        });
    }

    @Override
    public void setPharmacy(Pharmacy pharmacy) {
        firebaseAppHelper.setPharmacy(pharmacy);
    }

    @Override
    public void getOrders(String userId, BaseLisener<Order, String> lisener) {
        firebaseAppHelper.getOrders(userId, lisener);
    }

    @Override
    public void acceptOrder(Order order, String userId) {
        firebaseAppHelper.acceptOrder(order, userId);
    }

    @Override
    public void refuseOrder(Order order, String userId) {
        firebaseAppHelper.refuseOrder(order, userId);
    }
}
