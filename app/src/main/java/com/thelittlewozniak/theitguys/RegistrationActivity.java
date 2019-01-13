package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
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
        Button buttonRegister=findViewById(R.id.backbuttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView pseudo=findViewById(R.id.usernameText);
                TextView password=findViewById(R.id.passwordregtext);
                TextView city=findViewById(R.id.cityregtext);
                RadioButton female=findViewById(R.id.female);
                RadioButton male=findViewById(R.id.male);
                String pseudoText=pseudo.getText().toString();
                String passwordText=password.getText().toString();
                String cityText=city.getText().toString();
                String error=null;
                if(pseudoText.equals(""))
                    error+=" pseudo empty \n";
                if(passwordText.equals(""))
                    error+=" passord empty \n";
                if(cityText.equals(""))
                    error+=" city empty \n";
                if(!female.isChecked() && !male.isChecked())
                    error+=" select your gender \n";
                if(error!=null){
                    final AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
                    else
                        builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Error").setMessage(error).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {}}).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
                else{
                    new RegisterAsyncTask(activity).execute(pseudoText,passwordText,cityText,String.valueOf(male.isChecked()));
                }
            }
        });
        if(getIntent()!=null){
            if(getIntent().getExtras()!=null){
                if(getIntent().getExtras().getString("error")!=null){
                    final AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
                    else
                        builder = new AlertDialog.Builder(activity);
                    builder.setTitle("wrong login").setMessage("Your login is wrong").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {}}).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
            }
        }
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
