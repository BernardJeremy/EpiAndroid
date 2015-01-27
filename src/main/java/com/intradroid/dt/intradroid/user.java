package com.intradroid.dt.intradroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.net.URL;
import java.util.Collections;


public class user extends MenuDrawer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        boolean isMark = intent.getBooleanExtra("isMark", false);
        current = isMark ? 1 : 2;

        RequestAPI request = new RequestAPI();

        String param[] = {token};
        String paramName[] = {"token"};
        String result = request.performQuery("infos", paramName, param);

        String resultMark = request.performQuery(isMark ? "marks" : "all_modules", paramName, param);

        try {
            if (!result.equals("")) {
                setContentView(R.layout.activity_user);
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

                InfoJSON infos = mapper.readValue(result, InfoJSON.class);
                if (isMark) {
                    MarksJson markObj = mapper.readValue(resultMark, MarksJson.class);
                    Notes marks[] = markObj.getNotes();
                    marks = Arrays.copyOfRange(markObj.getNotes(), marks.length - 20, marks.length);
                    Collections.reverse(Arrays.asList(marks));
                    this.setMarks(marks);
                } else {
                    ModuleJSON markObj = mapper.readValue(resultMark, ModuleJSON.class);
                    Modules marks[] = markObj.getModules();
                    marks = Arrays.copyOfRange(markObj.getModules(), marks.length - 20, marks.length);
                    Collections.reverse(Arrays.asList(marks));
                    this.setModules(marks);
                }

                initMenuDrawer();
                Header.searchOnEvent((ImageView) findViewById(R.id.search_button), (EditText) findViewById(R.id.input_search), this);

                this.setAllData(infos);
                this.setProfileImage(infos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setModules(Modules modules[]) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) findViewById(R.id.grades_title_linear);

        TextView titleGrade = (TextView) findViewById(R.id.title_grades);
        titleGrade.setText("Modules");

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

    public void setAllData(InfoJSON infos)
    {
        final TextView credit = (TextView) findViewById(R.id.credit);
        final TextView name = (TextView) findViewById(R.id.big_name);
        final TextView login = (TextView) findViewById(R.id.login);
        final TextView promo = (TextView) findViewById(R.id.promo);
        final TextView cycle = (TextView) findViewById(R.id.cycle);

        name.setText(infos.getInfos().getTitle());
        login.setText(infos.getInfos().getLogin());
        promo.setText(infos.getInfos().getSchool_title() + " " + infos.getInfos().getPromo());
        cycle.setText("Cycle " + infos.getInfos().getCourse_code());
        credit.setText(infos.getCurrent().getAchieved());
    }

    public void setProfileImage(InfoJSON infos)
    {
        try {
            final ImageView photo = (ImageView) findViewById(R.id.photo);

            URL url = new URL("https://cdn.local.epitech.eu/userprofil/profilview/" + infos.getInfos().getLogin() + ".jpg");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            photo.setImageBitmap(bmp);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
