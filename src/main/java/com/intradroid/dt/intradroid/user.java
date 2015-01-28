package com.intradroid.dt.intradroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.Collections;


public class user extends MenuDrawer {

    boolean isMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        isMark = intent.getBooleanExtra("isMark", false);
        current = isMark ? 1 : 2;

        RequestAPI request = new RequestAPI();

        String param[] = {token};
        String paramName[] = {"token"};

        setContentView(R.layout.activity_user);
        initMenuDrawer();
        Header.searchOnEvent((ImageView) findViewById(R.id.search_button), (EditText) findViewById(R.id.input_search), this);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        request.performQuery("infos", paramName, param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                try {
                    String result = String.valueOf(response);

                    InfoJSON infos = mapper.readValue(result, InfoJSON.class);

                    setAllData(infos);
                    setProfileImage(infos);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        request.performQuery(isMark ? "marks" : "all_modules", paramName, param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                try {
                    String result = String.valueOf(response);

                    if (isMark) {
                        MarksJson markObj = mapper.readValue(result, MarksJson.class);
                        Notes marks[] = markObj.getNotes();
                        marks = Arrays.copyOfRange(markObj.getNotes(), marks.length - 20, marks.length);
                        Collections.reverse(Arrays.asList(marks));
                        setMarks(marks);
                    } else {
                        ModuleJSON markObj = mapper.readValue(result, ModuleJSON.class);
                        Modules marks[] = markObj.getModules();
                        marks = Arrays.copyOfRange(markObj.getModules(), marks.length - 20, marks.length);
                        Collections.reverse(Arrays.asList(marks));
                        setModules(marks);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setModules(Modules modules[]) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) findViewById(R.id.grades_title_linear);

        TextView titleGrade = (TextView) findViewById(R.id.title_grades);
        titleGrade.setText(R.string.modules_title);

        int i = 0;
        View table;

        for (Modules module : modules) {
            parent.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            if (i % 2 != 0) {
                table = inflater.inflate(R.layout.table_row, null);
            } else {
                table = inflater.inflate(R.layout.table_row_blue, null);
            }
            TextView title = (TextView) table.findViewById(R.id.grades_title);
            TextView value = (TextView) table.findViewById(R.id.grades_value);
            title.setText(module.getTitle());
            value.setText(module.getGrade());
            table.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            parent.addView(table);
            i += 1;
        }
    }

    public void setMarks(Notes marks[]) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) findViewById(R.id.grades_title_linear);

        TextView titleGrade = (TextView) findViewById(R.id.title_grades);
        titleGrade.setText(R.string.grade_title);

        int i = 0;
        View table;

        for (Notes mark : marks) {
            parent.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            if (i % 2 != 0) {
                table = inflater.inflate(R.layout.table_row, null);
            } else {
                table = inflater.inflate(R.layout.table_row_blue, null);
            }
            TextView title = (TextView) table.findViewById(R.id.grades_title);
            TextView value = (TextView) table.findViewById(R.id.grades_value);
            title.setText(mark.getTitle());
            value.setText(mark.getFinal_note());
            table.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            parent.addView(table);
            i += 1;
        }
    }
}
