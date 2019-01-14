package com.thelittlewozniak.theitguys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thelittlewozniak.theitguys.pojo.Utilisateur;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by natha on 1/13/2019.
 */

public class RegisterAsyncTask extends AsyncTask<String, String, Utilisateur> {
    private Activity activity;
    private Intent intent;
    private final ProgressDialog dialog;
    public RegisterAsyncTask(Activity activity){this.activity=activity;dialog = new ProgressDialog(activity);}

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Registering...");
        this.dialog.show();
    }
    @Override
    protected Utilisateur doInBackground(String... strings) {
        Utilisateur u = null;
        try {
            URL url = new URL("http://androidweb.azurewebsites.net/api/Utilisateur/Creer");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte[] tab = ("pseudo=" + strings[0] + "&motDePasse=" + strings[1]+"&sexe="+strings[3]+"&ville="+strings[2]).getBytes();
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
                try {
                    u = new ObjectMapper().readValue(scanner.next(), new TypeReference<Utilisateur>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    protected void onPostExecute(Utilisateur utilisateur) {
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        super.onPostExecute(utilisateur);
        if (utilisateur != null) {
            Session.getInstance().setUser(utilisateur);
            intent = new Intent(activity, ConversationListActivity.class);
            intent.putExtra("user", String.valueOf(utilisateur.getId()));
            new ConversationListAsync(activity).execute();
        }
        else{
            intent = new Intent(activity, RegistrationActivity.class);
            intent.putExtra("error", "username already used");
            activity.startActivity(intent);
            activity.finish();
        }
    }
}
