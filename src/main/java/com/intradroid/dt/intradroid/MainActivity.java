package com.intradroid.dt.intradroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.LinearLayout.LayoutParams;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;


public class MainActivity extends ActionBarActivity {

    private String token;

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

                Header.searchOnEvent((ImageView) findViewById(R.id.search_button), (EditText)findViewById(R.id.input_search), this);

                final ImageView photo = (ImageView) findViewById(R.id.photo);
                final TextView credit = (TextView) findViewById(R.id.credit);
                final TextView name = (TextView) findViewById(R.id.big_name);
                final TextView login = (TextView) findViewById(R.id.login);
                final TextView promo = (TextView) findViewById(R.id.promo);
                final TextView cycle = (TextView) findViewById(R.id.cycle);
                final TextView active = (TextView) findViewById(R.id.time_active);
                final TextView time_goal = (TextView) findViewById(R.id.time_goal);

                URL url = new URL("https://cdn.local.epitech.eu/userprofil/profilview/" + infos.getInfos().getLogin() + ".jpg");
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                photo.setImageBitmap(bmp);

                name.setText(infos.getInfos().getTitle());
                login.setText(infos.getInfos().getLogin());
                promo.setText(infos.getInfos().getSchool_title() + " " + infos.getInfos().getPromo());
                cycle.setText("Cycle " + infos.getInfos().getCourse_code());
                credit.setText(infos.getCurrent().getAchieved());
                active.setText(infos.getCurrent().getActive_log().substring(0, infos.getCurrent().getActive_log().lastIndexOf(".")) + "h");
                time_goal.setText(infos.getCurrent().getActive_log().substring(0, infos.getCurrent().getActive_log().lastIndexOf(".")) + "h < " + infos.getCurrent().getNslog_min() + "h");

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout parent = (LinearLayout) findViewById(R.id.messages);

                for (history history : infos.getHistory()) {
                    parent.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                    View message = inflater.inflate(R.layout.messages, null);
                    TextView from = (TextView) message.findViewById(R.id.from);
                    TextView content = (TextView) message.findViewById(R.id.content);
                    from.setText("De " + history.getUser().getTitle());
                    content.setText(history.getTitle());
                    message.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    parent.addView(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }
}
