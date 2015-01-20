package com.intradroid.dt.intradroid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

        Log.v("DONE Query", "RESULT" + result);

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

                name.setText(infos.getTitle());
                login.setText(infos.getLogin());
                promo.setText(infos.getSchool_title() + " " + infos.getPromo());
                cycle.setText("Cycle " + infos.getCourse_code());
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
