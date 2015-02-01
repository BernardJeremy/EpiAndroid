package com.intradroid.dt.intradroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.net.URL;


public class MainActivity extends ActivityManagement {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        current = 0;

        String param[] = {token};
        String paramName[] = {"token"};
        RequestAPI.performQuery("infos", paramName, param, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                try {
                    String result = String.valueOf(response);
                    if (!result.equals("")) {

                        result.replace("class", "_class");

                        InfoJSON infos = RequestAPI.getMapper().readValue(result, InfoJSON.class);
                        setContentView(R.layout.activity_main);

                        initMenuDrawer();
                        Header.searchOnEvent((ImageView) findViewById(R.id.search_button), (EditText) findViewById(R.id.input_search), MainActivity.this, token);

                        semesterCurrent = infos.getInfos().getSemester();

                        setProfileImage(infos);
                        setAllData(infos);


                        final TextView active = (TextView) findViewById(R.id.time_active);
                        final TextView time_goal = (TextView) findViewById(R.id.time_goal);

                        active.setText(infos.getCurrent().getActive_log().substring(0, infos.getCurrent().getActive_log().lastIndexOf(".")) + "h");
                        time_goal.setText(infos.getCurrent().getActive_log().substring(0, infos.getCurrent().getActive_log().lastIndexOf(".")) + "h of " + infos.getCurrent().getNslog_min() + "h");

                        displayMessages(infos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void displayMessages(InfoJSON infos)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) findViewById(R.id.messages);

        for (history history : infos.getHistory()) {
            parent.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            View message = inflater.inflate(R.layout.messages, null);
            TextView from = (TextView) message.findViewById(R.id.from);
            TextView content = (TextView) message.findViewById(R.id.content);
            from.setText("De " + history.getUser().getTitle());
            content.setText(android.text.Html.fromHtml(history.getTitle()));
            message.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            parent.addView(message);
        }
    }
}
