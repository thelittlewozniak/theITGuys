package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelittlewozniak.theitguys.pojo.Conversation;
import com.thelittlewozniak.theitguys.pojo.Message;
import com.thelittlewozniak.theitguys.pojo.Utilisateur;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by natha on 1/12/2019.
 */

public class ConversationGetAsync extends AsyncTask<String, String, List<Message>> {
    private Activity activity;
    private Intent intent;
    private String id;
    public ConversationGetAsync(Activity activity){this.activity=activity;}
    @Override
    protected List<Message> doInBackground(String... strings) {
        List<Message> messages = null;
        id=strings[0];
        try {
            URL url = new URL("http://androidweb.azurewebsites.net/api/Message/Get?id="+id);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "text/plain");
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            br.close();
            is.close();
            urlConnection.disconnect();
            messages = new ObjectMapper().readValue(result, new TypeReference<List<Message>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }
    @Override
    protected void onPostExecute(List<Message> messages){
        super.onPostExecute(messages);
        if (messages != null) {
            intent = new Intent(activity, ConversationActivity.class);
            ObjectMapper mapper = new ObjectMapper();
            try {
                intent.putExtra("messages", mapper.writeValueAsString(messages));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            intent.putExtra("idConv",String.valueOf(id));
            activity.startActivityForResult(intent, 3);
            activity.finish();
        }
    }
}
