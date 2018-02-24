package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by phartmann on 21/02/2018.
 */

public class QueryNews {

    public static final String LOG_TAG = QueryNews.class.getSimpleName();

    public static String title;
    public static String section;
    public static String urlArticle;
    private static final int READ_TIMEOUT = 1000;
    private static final int CONNECT_TIMEOUT = 12000;

    /* Start to fetch data from API */
    public static List<NewsList> fetchData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = "";

        try {
            /* Try to make a HTTP Request */
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error on httpRequest");
        }
        return QueryNews.extractResponse(jsonResponse);
    }

    /* Verify URL before create one */
    private static URL createUrl(String stringUrl){
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception){
            Log.e(LOG_TAG, "Error when creating URL");
            return null;
        }
        return url;
    }

    /* Open Connection */
    private static String makeHttpRequest( URL url ) throws IOException {

        String jsonResponse = "";
        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;

        /* Returns json early if it' empty*/
        if (url == null){
            return jsonResponse;
        }

        try {
            /* Open Connection */
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            urlConnection.connect();

            /* Handle http code */
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                /* Otherwise show an Error */
                Log.e(LOG_TAG, "Error on Response Code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem with connection.");
        } finally {
            /* Release resources */
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /* Set a Buffer to Read a Stream (Convert binary to data) */
    private static String readFromStream( InputStream inputStream ) throws  IOException {

        StringBuilder output = new StringBuilder();

        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();

            /* Do a loop until finish read */
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /* Parse JSON data and Fill the list */
    private static ArrayList<NewsList> extractResponse(String jsonResponse){
        /* If response is null, return */
        if(TextUtils.isEmpty(jsonResponse)) {
            Log.e(LOG_TAG, "JSON Response is empty");
            return null;
        }

        /* Create an empty ArrayList */
        ArrayList<NewsList> newsLists = new ArrayList <>();

        /* Try to parse JSON */
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");

            /* Do a loop to catch all results*/
            for (int i = 0; i < results.length(); i++ ){
                JSONObject index = results.getJSONObject(i);
                title = index.optString("webTitle");
                section = index.optString("sectionName");
                urlArticle = index.optString("webUrl");

                /* Fill results on list */
                newsLists.add(new NewsList(title, section, urlArticle));
            }

        } catch (JSONException e){
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem parsing JSON response");
        }
        return newsLists;
    }
}
