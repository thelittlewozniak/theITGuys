package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thelittlewozniak.theitguys.pojo.Utilisateur;

import org.json.JSONObject;

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
    private double latitude;
    private double longitude;
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
        if(utilisateur!=null){
            try{
                URL url = new URL("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?key=AIzaSyAdLhe5aaJVX7--a7xYQMfK-nLEINWDMLY&input="+utilisateur.getVille()+"&inputtype=textquery&fields=geometry");
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
                JsonParser parser=new JsonParser();
                JsonElement jsonTree = parser.parse(result);
                if(jsonTree.isJsonObject()){
                    JsonObject jsonObject = jsonTree.getAsJsonObject();

                    JsonElement candidates = jsonObject.get("candidates");
                    if(candidates.isJsonArray()){
                        JsonArray jsonArray=candidates.getAsJsonArray();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            if(jsonArray.get(i).isJsonObject()){
                                JsonObject canditate=jsonArray.get(i).getAsJsonObject();
                                JsonElement geometry=canditate.get("geometry");
                                JsonObject data=geometry.getAsJsonObject();
                                JsonElement location=data.get("location");
                                JsonObject latlong=location.getAsJsonObject();
                                latitude=latlong.get("lat").getAsDouble();
                                longitude=latlong.get("lng").getAsDouble();
                            }
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }

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
            intent.putExtra("latitude",latitude);
            intent.putExtra("longitude",longitude);
            activity.startActivityForResult(intent, 3);
        }
    }
}
