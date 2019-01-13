package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelittlewozniak.theitguys.pojo.Conversation;

import java.io.IOException;
import java.util.List;

/**
 * Created by natha on 1/12/2019.
 */

public class ConversationListActivity extends AppCompatActivity {
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_conversationlist);
        TableLayout tableLayout = findViewById(R.id.listConversation);
        List<Conversation> conversations = null;
        try {
            conversations = new ObjectMapper().readValue(getIntent().getExtras().getString("conversations"), new TypeReference<List<Conversation>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (conversations != null) {
            for (int i = 0; i < conversations.size(); i++) {
                TableRow tr = new TableRow(this);
                TextView tv = new TextView(this);
                tv.setText(conversations.get(i).getSujet());
                tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                tv.setPadding(40, 40, 40, 40);
                if (i % 2 != 0) {
                    tr.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
                    tv.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                } else {
                    tr.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                }
                final int j=conversations.get(i).getId();
                tr.addView(tv);
                tr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ConversationGetAsync(activity).execute(String.valueOf(j));
                    }
                });
                tableLayout.addView(tr);
            }
        }
        Button addConversation = findViewById(R.id.buttonAddConversation);
        addConversation.setOnClickListener(new View.OnClickListener() {
                                               public void onClick(View v) {
                                                   activity.startActivity(new Intent(activity, AddConversationActivity.class));
                                                   activity.finish();
                                               }
                                           }
        );
        Button profile=findViewById(R.id.profileListConversation);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ProfileAsync(activity).execute(String.valueOf(Session.getInstance().getUser().getId()));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new ConversationListAsync(activity).execute();
    }
}
