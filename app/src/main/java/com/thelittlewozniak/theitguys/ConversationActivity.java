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
import com.thelittlewozniak.theitguys.pojo.Message;

import java.io.IOException;
import java.util.List;

/**
 * Created by natha on 1/12/2019.
 */

public class ConversationActivity extends AppCompatActivity {
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_conversation);
        TableLayout tableLayout = findViewById(R.id.tableConversation);
        List<Message> messages = null;
        try {
            messages = new ObjectMapper().readValue(getIntent().getExtras().getString("messages"), new TypeReference<List<Message>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (messages != null) {
            for (int i = 0; i < messages.size(); i++) {
                TableRow tr = new TableRow(this);
                TextView tv = new TextView(this);
                tv.setText(messages.get(i).getText());
                tv.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                tv.setPadding(40, 40, 40, 40);
                if (messages.get(i).getUtilisateur().getSexe()) {
                    tr.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
                    tv.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                } else {
                    tr.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                }
                tr.addView(tv);
                tableLayout.addView(tr);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
