package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelittlewozniak.theitguys.pojo.Conversation;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by natha on 1/12/2019.
 */

public class AddConversationAsync extends AsyncTask<String, String, Conversation> {
    private Activity activity;

    public AddConversationAsync(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Conversation doInBackground(String... strings) {
        Conversation conversation = null;
        try {
            URL url = new URL("http://androidweb.azurewebsites.net/api/Conversation/Creer");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte[] tab = ("sujet=" + strings[0]).getBytes();
            urlConnection.setRequestProperty("Content-Length", Integer.toString(tab.length));
            urlConnection.setUseCaches(false);
            DataOutputStream wri = new DataOutputStream(urlConnection.getOutputStream());
            wri.write(tab);
            urlConnection.setConnectTimeout(100000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                InputStream stream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(stream, "UTF-8");
                Scanner scanner = new Scanner(inputStreamReader);
                String s = scanner.nextLine();
                Log.e("sdfsdf", s);
                try {
                    conversation = new ObjectMapper().readValue(s, new TypeReference<Conversation>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversation;
    }

    @Override
    protected void onPostExecute(Conversation conversation) {
        super.onPostExecute(conversation);
        if (conversation != null) {
            new ConversationListAsync(activity).execute();
        }
    }
}
