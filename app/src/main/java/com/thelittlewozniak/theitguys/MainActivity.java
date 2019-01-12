package com.thelittlewozniak.theitguys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thelittlewozniak.theitguys.pojo.Utilisateur;

public class MainActivity extends AppCompatActivity {

    private MainActivity activity;
    private Utilisateur utilisateur;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        button=findViewById(R.id.connect);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email=((EditText)findViewById(R.id.email)).getText().toString();
                String password=((EditText)findViewById(R.id.password)).getText().toString();
                new LoginAsyncTask(activity).execute(email,password);
            }
        });
    }
}
