package com.example.dawakpharmacy.ui.signup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dawakpharmacy.R;
import com.example.dawakpharmacy.di.component.ActivityComponent;
import com.example.dawakpharmacy.model.Pharmacy;
import com.example.dawakpharmacy.ui.base.BaseFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Mohamed Fakhry on 18/02/2018.
 */

public class SignUpFragment extends BaseFragment
        implements SignUpContract.View, OnMapReadyCallback, OnSuccessListener<Location> {

    @BindView(R.id.emailET)
    EditText emailET;
    @BindView(R.id.passwordET)
    EditText passwordET;
    @BindView(R.id.nameTV)
    EditText nameTV;
    @BindView(R.id.phoneTV)
    EditText phoneTV;
    @BindView(R.id.openTC)
    TextView openTC;
    @BindView(R.id.closeTC)
    TextView closeTC;

    @Inject
    SignUpContract.Presenter<SignUpContract.View> presenter;
    @Inject
    Pharmacy pharmacy;

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 101;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

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
        pharmacy.setOpenDate("12:00 am");
        pharmacy.setCloseDate("12:00 pm");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.signUpB)
    public void signUp() {
        String pharmacyName = nameTV.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String phone = phoneTV.getText().toString().trim();

        pharmacy.setName(pharmacyName);
        pharmacy.setEmail(email);
        pharmacy.setPassword(password);
        pharmacy.setPhone(phone);

        presenter.signUp(pharmacy);
    }

    @Override
    public void onSignUpSuccess() {
        showMessage("Success");
    }

    @Override
    public void onSignUpFail() {
        showMessage("Fail");
    }

    @OnClick(R.id.openTC)
    public void onOpenTC() {
        TimePickerFragment timePickerDialog = new TimePickerFragment();
        timePickerDialog.setTimeCallback(new TimePickerFragment.TimeCallback() {
            @Override
            public void time(int hours, int minute, boolean isAm) {
                String amOrPm = isAm ? "am" : "pm";
                pharmacy.setOpenDate(hours + ":" + minute + " " + amOrPm);
                openTC.setText(pharmacy.getOpenDate());
            }
        });
        timePickerDialog.show(getChildFragmentManager(), "TimePicker");
    }

    @OnClick(R.id.closeTC)
    public void onCloseTC() {
        TimePickerFragment timePickerDialog = new TimePickerFragment();
        timePickerDialog.setTimeCallback(new TimePickerFragment.TimeCallback() {
            @Override
            public void time(int hours, int minute, boolean isAm) {
                String amOrPm = isAm ? "am" : "pm";
                pharmacy.setCloseDate(hours + ":" + minute + " " + amOrPm);
                closeTC.setText(pharmacy.getCloseDate());
            }
        });
        timePickerDialog.show(getChildFragmentManager(), "TimePicker");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            getLocationPermission();

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            if (getBaseActivity().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    && getBaseActivity().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION))
                updateLocationUI();
            else
                getBaseActivity().requestPermissionsSafely(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    setPharmacyLocation(latLng);
                }
            });
        }
    }

    private void setPharmacyLocation(LatLng latLng) {
        mMap.clear();
        pharmacy.setLocation(latLng.latitude, latLng.longitude);

        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_pharmacy)));
    }

    private void getLocationPermission() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if ((grantResults.length > 1) && (grantResults[0] +
                        grantResults[1]) == PackageManager.PERMISSION_GRANTED) {
                    updateLocationUI();
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(getBaseActivity(), this);

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    @Override
    public void onSuccess(Location location) {
        LatLng userPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 16.0f));
        setPharmacyLocation(userPosition);
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_pharmacy);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
