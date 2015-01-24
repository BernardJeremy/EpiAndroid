package com.intradroid.dt.intradroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;


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

        //Log.v("DONE Query", "RESULT" + result);

        try {
            if (!result.equals("")) {
                ObjectMapper mapper = new ObjectMapper();

                result.replace("class", "_class");

                InfoJSON infos = mapper.readValue(result, InfoJSON.class);
                setContentView(R.layout.activity_main);

                final TextView name = (TextView) findViewById(R.id.big_name);
                final TextView login = (TextView) findViewById(R.id.login);
                final TextView promo = (TextView) findViewById(R.id.promo);
                final TextView cycle = (TextView) findViewById(R.id.cycle);
                final TextView active = (TextView) findViewById(R.id.time_active);
                final TextView time_goal = (TextView) findViewById(R.id.time_goal);
                final LinearLayout messageBox = (LinearLayout) findViewById(R.id.messages);

                name.setText(infos.getInfos().getTitle());
                login.setText(infos.getInfos().getLogin());
                promo.setText(infos.getInfos().getSchool_title() + " " + infos.getInfos().getPromo());
                cycle.setText("Cycle " + infos.getInfos().getCourse_code());
                active.setText(infos.getCurrent().getActive_log().substring(0, infos.getCurrent().getActive_log().lastIndexOf(".")) + "h");
                time_goal.setText(infos.getCurrent().getActive_log().substring(0, infos.getCurrent().getActive_log().lastIndexOf(".")) + "h < " + infos.getCurrent().getNslog_min() + "h");
                for (history history : infos.getHistory()) {
                    TextView from = new TextView(this);
                    TextView content = new TextView(this);
                    from.setText("De " + history.getTitle());
                    content.setText(history.getUser().getTitle());
                    messageBox.addView(from);
                    int idx = messageBox.indexOfChild(from);
                    from.setTag(Integer.toString(idx));
                    idx = messageBox.indexOfChild(content);
                    from.setTag(Integer.toString(idx));
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
