package com.example.root.libapp_v1.HttpModule;

import android.content.Entity;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by Yixin on 15-4-27.
 */
public class DoGetAndPost {
    static JSONObject DoGet(String url) {
        try {
            String result = null;
            /**
             * create a http client which can send message to http server
             * */
            DefaultHttpClient httpClient = new DefaultHttpClient();
            /**
             * create a get request object
             * */
            HttpGet request = new HttpGet(url);
            /**
             * the http client use this request and get a response
             * */
            HttpResponse response = httpClient.execute(request);
            /**
             * make those response to JSONObject
             * */
            result = EntityUtils.toString(response.getEntity());
            JSONObject object = new JSONObject(result);
            /**
             * return JSONObject
             * */
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
