package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
    }
}
