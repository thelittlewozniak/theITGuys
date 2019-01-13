package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by natha on 1/13/2019.
 */

public class VilleActivity extends AppCompatActivity {
    Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ville);
        activity=this;
        final List<String> cities=new ArrayList<>();
        cities.add("Charleroi");
        cities.add("Liège");
        cities.add("Bruxelles");
        cities.add("Namur");
        cities.add("Wavre");
        cities.add("Louvain La Neuve");
        cities.add("Mons");
        cities.add("Maredsous");
        cities.add("Jette");
        TableLayout tab=findViewById(R.id.villeList);
        for (int i = 0; i < cities.size(); i++) {
            TableRow tr=new TableRow(this);
            TextView tv=new TextView(this);
            tv.setText(cities.get(i));
            tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            tv.setPadding(40, 40, 40, 40);
            tr.addView(tv);
            final String city=cities.get(i);
            tr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(activity,RegistrationActivity.class);
                    intent.putExtra("ville",city);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
            tab.addView(tr);
        }
    }
}
