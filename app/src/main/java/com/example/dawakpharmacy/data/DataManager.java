package com.example.dawakpharmacy.data;

import com.example.dawakpharmacy.model.Pharmacy;
import com.example.dawakpharmacy.ui.base.BaseLisener;
import com.example.dawakpharmacy.model.Order;

public interface DataManager {

    void signUp(String email, String password, BaseLisener<String, String> lisener);

    void login(String email, String password, BaseLisener<String, String> lisener);

    void setPharmacy(Pharmacy pharmacy);

    void getOrders(String userId, BaseLisener<Order, String> lisener);

    void acceptOrder(Order order, String userId);

    void refuseOrder(Order order, String userId);
}
