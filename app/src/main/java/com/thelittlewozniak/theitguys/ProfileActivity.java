package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelittlewozniak.theitguys.pojo.Utilisateur;

import java.io.IOException;

/**
 * Created by natha on 1/13/2019.
 */

public class ProfileActivity extends AppCompatActivity {
    private Activity activity;
    private String iduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Utilisateur utilisateur=null;
        try {
            utilisateur= new ObjectMapper().readValue(getIntent().getExtras().getString("utilisateur"), new TypeReference<Utilisateur>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(utilisateur!=null){
            TextView pseudo=findViewById(R.id.pseudoText);
            pseudo.setText(utilisateur.getPseudo());
            TextView ville=findViewById(R.id.cityText);
            ville.setText(utilisateur.getVille());
        }
    }
}
