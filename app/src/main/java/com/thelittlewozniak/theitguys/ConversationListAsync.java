package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelittlewozniak.theitguys.pojo.Conversation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by natha on 1/12/2019.
 */

public class ConversationListAsync extends AsyncTask<String, String, List<Conversation>> {
    private Activity activity;
    private Intent intent;
    private final ProgressDialog dialog;

    public ConversationListAsync(Activity activity) {this.activity = activity;dialog=new ProgressDialog(activity);}

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Loading...");
        this.dialog.show();
    }

    @Override
    protected List<Conversation> doInBackground(String... strings) {
        List<Conversation> conversations = null;
        try {
            URL url = new URL("http://androidweb.azurewebsites.net/api/Conversation/GetAll");
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
            conversations = new ObjectMapper().readValue(result, new TypeReference<List<Conversation>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversations;
    }

    @Override
    protected void onPostExecute(List<Conversation> conversations) {
        super.onPostExecute(conversations);
        if(dialog.isShowing())
            dialog.dismiss();
        if (conversations != null) {
            intent = new Intent(activity, ConversationListActivity.class);
            ObjectMapper mapper = new ObjectMapper();
            try {
                intent.putExtra("conversations", mapper.writeValueAsString(conversations));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            activity.startActivityForResult(intent, 3);
            activity.finish();
        }
    }
}
