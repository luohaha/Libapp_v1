package com.example.root.libapp_v1.HttpModule;

import android.content.Entity;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
             * make those response to HttpEntity
             * */
            HttpEntity httpEntity = response.getEntity();
            /**
             * get the length of httpEntry
             * */
            long length = httpEntity.getContentLength();
            /**
             * create an inputstream from httpEntry
             * */
            InputStream is = httpEntity.getContent();
            if (is != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buf = new byte[128];
                int ch = -1;
                /**
                 * the length we get now, the destination is length
                 * */
                int lengthNow = 0;
                while ((ch = is.read(buf)) != -1) {
                    byteArrayOutputStream.write(buf, 0, ch);
                    lengthNow += ch;
                    /**
                     * sleep 100ms
                     * */
                    Thread.sleep(100);
                }
                result = new String(byteArrayOutputStream.toByteArray());
            }
            JSONObject returnObject = new JSONObject(result);
            return returnObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * do post request and get data from http server
     * @param url the post url
     * @param param the data we want to post
     * @return the json we get
     */
    public static JSONObject doPost(String url, List<NameValuePair> param) {
        /**
         * create the post request
         * */
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);
            /**
             *  an arraylist to contain NameValuePair
             * */
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(param);
            request.setEntity(formEntity);
            HttpResponse response = httpClient.execute(request);
            /**
             * get the response and use buffered reader to restore it
             * */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer();
            String lineStr = "";
            /**
             * using the lineStr to restore per line data
             * */
            while ((lineStr = bufferedReader.readLine()) != null) {
                stringBuffer.append(lineStr);
            }
            bufferedReader.close();
            /**
             * using hte resultStr to restore the stringBuffer's data
             * */
            String resultStr= stringBuffer.toString();
            JSONObject resultJSON = new JSONObject(resultStr);
            return resultJSON;
         } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
