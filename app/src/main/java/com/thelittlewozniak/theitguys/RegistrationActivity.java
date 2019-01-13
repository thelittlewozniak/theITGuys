package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natha on 1/13/2019.
 */

public class RegistrationActivity extends AppCompatActivity {
    private Activity activity;
    public final static int NUM_REQUETE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.activity_registration);
        Button buttonBack=findViewById(R.id.backbuttonRegister);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity,MainActivity.class));
                activity.finish();
            }
        });
        Button buttonVille=findViewById(R.id.buttonselectcity);
        buttonVille.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegistrationActivity.this,VilleActivity.class);
                startActivityForResult(intent,NUM_REQUETE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                TextView tv=findViewById(R.id.cityregtext);
                tv.setText(data.getExtras().getString("ville"));
            }
        }
    }
}
