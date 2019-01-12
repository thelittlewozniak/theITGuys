package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by natha on 1/12/2019.
 */

public class AddConversationActivity extends AppCompatActivity {
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addconversation);
        activity=this;
        final EditText subjet=findViewById(R.id.conversationSubjectEditText);
        Button button=findViewById(R.id.backButtonCreationConversation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConversationListAsync(activity).execute();
            }
        });
        Button buttonCreate=findViewById(R.id.addConversationButton);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddConversationAsync(activity).execute(subjet.getText().toString());
            }
        });
    }
}
