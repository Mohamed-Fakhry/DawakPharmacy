package com.example.dawakpharmacy.data.firebase;


import android.support.annotation.NonNull;
import android.util.Log;

import com.example.dawakpharmacy.model.Location;
import com.example.dawakpharmacy.model.Order;
import com.example.dawakpharmacy.model.Pharmacy;
import com.example.dawakpharmacy.ui.base.BaseLisener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

public class AppFirebaseHelper implements FirebaseHelper {

    private static final String PHARMACY = "pharmacy";
    private static final String USER = "user";
    private static final String ORDER = "order";
    private static final String DETAILS = "details";

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private GeoFire geoFire;

    public AppFirebaseHelper() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        geoFire = new GeoFire(database.getReference().child(PHARMACY));
    }

    @Override
    public Task<AuthResult> signUp(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    @Override
    public Task<AuthResult> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    @Override
    public void setPharmacy(final Pharmacy pharmacy) {
        Location location = pharmacy.getLocation();
        geoFire.setLocation(pharmacy.getId(), new GeoLocation(location.getLat(), location.getLang()), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (key != null) {
                    database.getReference().child(PHARMACY).child(key)
                            .child("details").setValue(pharmacy).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("test", "success");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("test", e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    public void getOrders(String userId, final BaseLisener<Order, String> lisener) {
        database.getReference().child(PHARMACY).child(userId).child(DETAILS).child(ORDER)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Order order = dataSnapshot.getValue(Order.class);
                        order.setId(dataSnapshot.getKey());
                        if (order.getAvailable() == null)
                            lisener.onSuccess(order);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        lisener.onFail(databaseError.getMessage());
                    }
                });
    }

    @Override
    public void acceptOrder(final Order order, final String userId) {
        database.getReference().child(USER).child(order.getUserId()).child(ORDER).child(order.getId())
                .child("available").setValue(true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child(PHARMACY).child(userId).child(DETAILS).child(ORDER)
                                .child(order.getId()).child("available").setValue(true);
                    }
                });
    }

    @Override
    public void refuseOrder(final Order order, final String userId) {
        database.getReference().child(USER).child(order.getUserId()).child(ORDER).child(order.getId())
                .child("available").setValue(false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference().child(PHARMACY).child(userId).child(DETAILS).child(ORDER)
                                .child(order.getId()).child("available").setValue(false);
                    }
                });
    }
}
