package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thelittlewozniak.theitguys.pojo.Utilisateur;

import java.io.IOException;

/**
 * Created by natha on 1/13/2019.
 */

public class ProfileActivity extends AppCompatActivity{
    private Activity activity;
    private String iduser;
    private Utilisateur utilisateur;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        try {
            utilisateur= new ObjectMapper().readValue(getIntent().getExtras().getString("utilisateur"), new TypeReference<Utilisateur>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(utilisateur!=null){
            TextView pseudo=findViewById(R.id.pseudoTextView);
            pseudo.setText(utilisateur.getPseudo());
            TextView ville=findViewById(R.id.cityTextView);
            ville.setText(utilisateur.getVille());
        }
    }

}
