package com.example.root.libapp_v1.HttpModule;

import android.content.Entity;
import android.util.Log;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by Yixin on 15-4-27.
 */
public class DoGetAndPost {
    /**
     * do get request and get result
     * @param url the get http url
     * @return the json we get
     */
    public static JSONObject doGet(String url) {
        JSONObject returnObject = null;
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
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity());
            }
            returnObject = new JSONObject(result);
            /**
             * return JSONObject
             * */
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("hahaha", e.toString());
        }
        return returnObject;
    }

    /**
     * do post request and get data from http server
     * @param url the post url
     * @param param the data we want to post
     * @return the json we get
     */
    public static JSONObject doPost(String url, JSONObject param) {
        /**
         * create the post request
         * */

        //StringEntity stringEntity = new StringEntity(param.toString());
        return null;
    }
}
