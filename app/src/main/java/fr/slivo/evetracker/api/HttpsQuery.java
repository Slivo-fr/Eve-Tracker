package fr.slivo.evetracker.api;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpsQuery extends AsyncTask<String, Void ,String> {

    @Override
    protected String doInBackground(String... params) {

        String source = "";
        String query = params[0];
        URL oracle = null;

        try {
            oracle = new URL(query);
            HttpsURLConnection yc = (HttpsURLConnection )oracle.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                source +=inputLine;
            in.close();
            return source;

        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}


