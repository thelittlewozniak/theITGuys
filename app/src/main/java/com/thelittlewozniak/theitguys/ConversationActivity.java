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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelittlewozniak.theitguys.pojo.Conversation;
import com.thelittlewozniak.theitguys.pojo.Message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by natha on 1/12/2019.
 */

public class ConversationActivity extends AppCompatActivity {
    private Activity activity;
    private String idconv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        idconv=getIntent().getExtras().getString("idconv");
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
                    tr.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                } else {
                    tr.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
                    tv.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
                }
                TextView usernmae=new TextView(this);
                usernmae.setTypeface(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
                usernmae.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                usernmae.setPadding(40, 40, 40, 40);
                usernmae.setText(messages.get(i).getUtilisateur().getPseudo());
                TextView time=new TextView(this);
                time.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
                time.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                time.setText(new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.FRANCE).format(messages.get(i).getDate()));
                if(Session.getInstance().getUser().getId()==messages.get(i).getUtilisateur().getId()){
                    tr.addView(usernmae);
                    tr.addView(tv);
                    tr.addView(time);
                }
                else{
                    tr.addView(tv);
                    tr.addView(usernmae);
                    tr.addView(time);
                }
                final int id=messages.get(i).getUtilisateur().getId();
                tr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new ProfileAsync(activity).execute(String.valueOf(id));
                    }
                });
                tableLayout.addView(tr);
            }
        }
        Button button =findViewById(R.id.backButtonConversation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConversationListAsync(activity).execute();
            }
        });
        Button addMessage=findViewById(R.id.buttonSendMessage);
        addMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText message=findViewById(R.id.messageEditText);
                new AddMessageAsync(activity).execute(message.getText().toString(),idconv);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new ConversationGetAsync(activity).execute(idconv);
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        new ConversationGetAsync(activity).execute(idconv);
    }*/
}
