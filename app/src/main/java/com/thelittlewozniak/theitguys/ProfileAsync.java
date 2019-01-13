package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelittlewozniak.theitguys.pojo.Utilisateur;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by natha on 1/13/2019.
 */

public class ProfileAsync extends AsyncTask<String, String, Utilisateur> {
    private Activity activity;
    private Intent intent;
    public ProfileAsync(Activity activity){this.activity=activity;}
    @Override
    protected Utilisateur doInBackground(String... strings) {
        Utilisateur utilisateur = null;
        try {
            URL url = new URL("http://androidweb.azurewebsites.net/api/Utilisateur/Get?id="+strings[0]);
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
            utilisateur = new ObjectMapper().readValue(result, new TypeReference<Utilisateur>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    @Override
    protected void onPostExecute(Utilisateur utilisateur) {
        super.onPostExecute(utilisateur);
        if (utilisateur != null) {
            intent = new Intent(activity, ProfileActivity.class);
            ObjectMapper mapper = new ObjectMapper();
            try {
                intent.putExtra("utilisateur", mapper.writeValueAsString(utilisateur));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            activity.startActivityForResult(intent, 3);
        }
    }
}
