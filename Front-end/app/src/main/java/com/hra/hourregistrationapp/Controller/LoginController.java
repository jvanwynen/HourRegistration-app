package com.hra.hourregistrationapp.Controller;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController extends AsyncTask<String, Integer, Boolean> {

    public interface AsyncResponse {
        void processFinish(boolean output);
    }

    public AsyncResponse listener;

    public LoginController(AsyncResponse listener) {
        this.listener = listener;
    }

    private static final String TAG = "HTTPTAG";
    DefaultHttpClient httpClient = new DefaultHttpClient();
    HttpPost httpPost = new HttpPost("http://192.168.1.187:80/tokensignin");

    @Override
    protected Boolean doInBackground(String... strings) {
        for (String idToken : strings) {
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>(1);
                nameValuePairs.add(new BasicNameValuePair("idToken", idToken));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);
                int statusCode = response.getStatusLine().getStatusCode();
                final String responseBody = EntityUtils.toString(response.getEntity());
                Log.i(TAG, "Signed in as: " + responseBody);
                return true;

            } catch (ClientProtocolException e) {
                Log.d(TAG,"Error sending ID token to backend." , e);
                 return false;
            } catch (IOException e) {
                 Log.d(TAG,"Error sending ID token to backend." + e);
                 return false;
            }
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        listener.processFinish(result);
    }
}
