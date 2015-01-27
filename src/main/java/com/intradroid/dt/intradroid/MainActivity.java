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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;


public class MainActivity extends MenuDrawer {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        RequestAPI request = new RequestAPI();

        String param[] = {token};
        String paramName[] = {"token"};
        String result = request.performQuery("infos", paramName, param);

        try {
            if (!result.equals("")) {
                ObjectMapper mapper = new ObjectMapper();

                result.replace("class", "_class");

                InfoJSON infos = mapper.readValue(result, InfoJSON.class);
                setContentView(R.layout.activity_main);

                initMenuDrawer();

                Header.searchOnEvent((ImageView) findViewById(R.id.search_button), (EditText) findViewById(R.id.input_search), this);
                this.setProfileImage(infos);
                this.setAllData(infos);
                this.displayMessages(infos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void setAllData(InfoJSON infos)
    {
        final TextView credit = (TextView) findViewById(R.id.credit);
        final TextView name = (TextView) findViewById(R.id.big_name);
        final TextView login = (TextView) findViewById(R.id.login);
        final TextView promo = (TextView) findViewById(R.id.promo);
        final TextView cycle = (TextView) findViewById(R.id.cycle);
        final TextView active = (TextView) findViewById(R.id.time_active);
        final TextView time_goal = (TextView) findViewById(R.id.time_goal);

        name.setText(infos.getInfos().getTitle());
        login.setText(infos.getInfos().getLogin());
        promo.setText(infos.getInfos().getSchool_title() + " " + infos.getInfos().getPromo());
        cycle.setText("Cycle " + infos.getInfos().getCourse_code());
        credit.setText(infos.getCurrent().getAchieved());
        active.setText(infos.getCurrent().getActive_log().substring(0, infos.getCurrent().getActive_log().lastIndexOf(".")) + "h");
        time_goal.setText(infos.getCurrent().getActive_log().substring(0, infos.getCurrent().getActive_log().lastIndexOf(".")) + "h < " + infos.getCurrent().getNslog_min() + "h");
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
