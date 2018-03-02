package com.example.dawakpharmacy.data.firebase;

import com.example.dawakpharmacy.model.Order;
import com.example.dawakpharmacy.model.Pharmacy;
import com.example.dawakpharmacy.ui.base.BaseLisener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by Mohamed Fakhry on 18/02/2018.
 */

public interface FirebaseHelper {

    Task<AuthResult> signUp(String email, String password);

    Task<AuthResult> login(String email, String password);

    void setPharmacy(Pharmacy pharmacy);

    void getOrders(String userId, BaseLisener<Order, String> lisener);

    void acceptOrder(Order order, String userId);

    void refuseOrder(Order order, String userId);
}
