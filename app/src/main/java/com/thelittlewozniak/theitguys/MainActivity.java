package com.thelittlewozniak.theitguys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thelittlewozniak.theitguys.pojo.Utilisateur;

public class MainActivity extends AppCompatActivity {

    private MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        Button button = findViewById(R.id.connect);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.email)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                String error=null;
                if(email.equals("") && password.equals("")){
                    error=" pseudo and password empty \n";
                }
                else if(email.equals("") && !password.equals("")){
                    error=" pseudo empty \n";
                }
                else if(!email.equals("") && password.equals("")){
                    error=" password empty \n";
                }
                else
                    new LoginAsyncTask(activity).execute(email, password);
                if(error!=null){
                    final AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
                    else
                        builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Error").setMessage(error).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int which) {}}).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
            }
        });
        Button register =findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity,RegistrationActivity.class));
                activity.finish();
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
}
