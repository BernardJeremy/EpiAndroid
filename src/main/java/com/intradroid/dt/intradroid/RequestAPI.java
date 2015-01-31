package com.intradroid.dt.intradroid;

/**
 * Created by bernar_w on 13/01/2015.
 */

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.*;

import java.util.Hashtable;

public class RequestAPI {

    final static private String url = "https://epitech-api.herokuapp.com/";
    final static private AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
    final static private ObjectMapper mapper = new ObjectMapper();

    final static private Hashtable<String, String> QueryTypeMap;
    static {
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
    }

    final static private Hashtable<String, String> RealQueryMap;
    static {
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

    private RequestAPI() {
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
       client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static RequestParams prepareParams(String paramName[], String param[])
    {
        RequestParams params = new RequestParams();

        for (int i = 0; i < paramName.length; ++i ) {
            params.put(paramName[i], param[i]);
        }
        return (params);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return url + relativeUrl;
    }

    public static ObjectMapper getMapper() { return mapper; }

    public static void performQuery(String type, String paramName[], String param[], JsonHttpResponseHandler responseHandler){
        try {

            System.out.println("Request : " + QueryTypeMap.get(type) + " in " + RealQueryMap.get(type));

            if (RealQueryMap.get(type).equals("GET")) {
               get(QueryTypeMap.get(type), prepareParams(paramName, param), responseHandler);
            } else {
               post(QueryTypeMap.get(type), prepareParams(paramName, param), responseHandler);
            }

        } catch (Exception e) {
            Log.v("EXCEPTION", e.toString());
        }
    }

    public static void getImageQuery(String url, BinaryHttpResponseHandler responseHandler)
    {
        client.get(url, null, responseHandler);
    }
}
