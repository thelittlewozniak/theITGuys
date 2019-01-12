package com.thelittlewozniak.theitguys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by natha on 1/12/2019.
 */

public class ConversationListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversationlist);
        TextView e=findViewById(R.id.userId);
        e.setText(getIntent().getExtras().getString("user"));
    }
}
