package com.intradroid.dt.intradroid;

/**
 * Created by bernar_w on 13/01/2015.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import java.util.Hashtable;

public class RequestAPI {

    private String url;
    private Hashtable<String, String> QueryTypeMap;
    private Hashtable<String, String> RealQueryMap;

    public RequestAPI() {
        this.url = "https://epitech-api.herokuapp.com/";

        QueryTypeMap = new Hashtable<String, String>();
        QueryTypeMap.put("login", "login");
        QueryTypeMap.put("infos", "infos");
        QueryTypeMap.put("planning", "planning");
        QueryTypeMap.put("all_susies", "susies");
        QueryTypeMap.put("one_susie", "susie");
        QueryTypeMap.put("subscribe_susie", "susie");
        QueryTypeMap.put("unsubscribe_susie", "susie");
        QueryTypeMap.put("all_projects", "projects");
        QueryTypeMap.put("one_project", "project");
        QueryTypeMap.put("subscribe_project", "project");
        QueryTypeMap.put("unsubscribe_project", "project");
        QueryTypeMap.put("project_file", "project/files");
        QueryTypeMap.put("all_modules", "modules");
        QueryTypeMap.put("one_module", "module");
        QueryTypeMap.put("subscribe_module", "module");
        QueryTypeMap.put("unsubscribe_module", "module");
        QueryTypeMap.put("event", "event");
        QueryTypeMap.put("subscribe_event", "event");
        QueryTypeMap.put("unsubscribe_event", "event");
        QueryTypeMap.put("marks", "marks");
        QueryTypeMap.put("messages", "messages");
        QueryTypeMap.put("alerts", "alerts");
        QueryTypeMap.put("photo", "photo");
        QueryTypeMap.put("token", "token");
        QueryTypeMap.put("trombi", "trombi");

        RealQueryMap = new Hashtable<String, String>();
        RealQueryMap.put("login", "GET");
        RealQueryMap.put("infos", "POST");
        RealQueryMap.put("planning", "GET");
        RealQueryMap.put("all_susies", "GET");
        RealQueryMap.put("one_susie", "GET");
        RealQueryMap.put("subscribe_susie", "POST");
        RealQueryMap.put("unsubscribe_susie", "DELETE");
        RealQueryMap.put("all_projects", "GET");
        RealQueryMap.put("one_project", "GET");
        RealQueryMap.put("subscribe_project", "POST");
        RealQueryMap.put("unsubscribe_project", "DELETE");
        RealQueryMap.put("project_file", "GET");
        RealQueryMap.put("all_modules", "GET");
        RealQueryMap.put("one_module", "GET");
        RealQueryMap.put("subscribe_module", "POST");
        RealQueryMap.put("unsubscribe_module", "DELETE");
        RealQueryMap.put("event", "GET");
        RealQueryMap.put("subscribe_event", "POST");
        RealQueryMap.put("unsubscribe_event", "DELETE");
        RealQueryMap.put("marks", "GET");
        RealQueryMap.put("messages", "GET");
        RealQueryMap.put("alerts", "GET");
        RealQueryMap.put("photo", "GET");
        RealQueryMap.put("token", "POST");
        RealQueryMap.put("trombi", "GET");
    }

    public String get(String type, String paramName[],String param[]) throws Exception{

        HttpClient client = new DefaultHttpClient();
        String getUrl = this.url + QueryTypeMap.get(type) + "?";
        for (int i = 0; i < paramName.length; ++i ) {
            getUrl +=  paramName[i] + "=" + URLEncoder.encode(param[i], "UTF-8") + "&";
        }
        HttpGet request = new HttpGet(getUrl);

        request.addHeader("User-Agent", "");

        HttpResponse response = client.execute(request);

        Log.v("perform Query", "Sending request " + RealQueryMap.get(type) + " to URL : " + getUrl);
        Log.v("perform Query", "Response Code : " + response.getStatusLine().getStatusCode());

        if (response.getStatusLine().getStatusCode() >= 300){
            return ("");
        }

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        Log.v("perform Query", result.toString());
        Log.v("perform Query", "Type : " + type);

        return result.toString();

    }

    public String post(String type, String paramName[], String param[]) throws Exception{
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(this.url + QueryTypeMap.get(type));

        post.setHeader("User-Agent", "");

        List<NameValuePair> urlParameters = new ArrayList<>();
        for (int i = 0; i < paramName.length; ++i ) {
            urlParameters.add(new BasicNameValuePair(paramName[i], URLEncoder.encode(param[i], "UTF-8")));
        }

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        Log.v("perform Query", "Sending request " + RealQueryMap.get(type) + " to URL : " + this.url + QueryTypeMap.get(type));
        Log.v("perform Query", "Response Code : " + response.getStatusLine().getStatusCode());
        Log.v("perform Query", "Post parameters : " + post.getEntity());

        if (response.getStatusLine().getStatusCode() >= 300){
            return ("");
        }

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line ;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        Log.v("perform Query", result.toString());
        return result.toString();
    }

    public String delete(String type, String paramName[], String param[]) throws Exception{

        List<NameValuePair> urlParameters = new ArrayList<>();
        for (int i = 0; i < paramName.length; ++i ) {
            urlParameters.add(new BasicNameValuePair(paramName[i], URLEncoder.encode(param[i], "UTF-8")));
        }

        HttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(this.url + QueryTypeMap.get(type) + "?" + URLEncodedUtils.format(urlParameters, "UTF-8"));
        delete.setHeader("User-Agent", "");

        HttpResponse response = client.execute(delete);
        Log.v("perform Query", "Sending request " + RealQueryMap.get(type) + " to URL : " + this.url + QueryTypeMap.get(type));
        Log.v("perform Query", "Response Code : " + response.getStatusLine().getStatusCode());

        if (response.getStatusLine().getStatusCode() >= 300){
            return ("");
        }

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line ;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        Log.v("perform Query", result.toString());
        return result.toString();
    }

    public String performQuery(String type, String paramName[], String param[]){
        try {

            Log.v("PREPARE Query", "URL : " + this.url);
            if (RealQueryMap.get(type).equals("GET"))
                return this.get(type, paramName, param);
            else
                return RealQueryMap.get(type).equals("POST") ? this.post(type, paramName, param) : this.delete(type, paramName, param);

        } catch (Exception e) {
            Log.v("EXCEPTION", e.toString());
            return "";
        }
    }
}
