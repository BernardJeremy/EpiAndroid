package com.intradroid.dt.intradroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.BinaryHttpResponseHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Header extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);
    }

    public static void searchOnEvent(ImageView img, final EditText input, final ActionBarActivity context, final String token)
    {

        input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    Intent intent = new Intent(context, Rapport.class);
                    intent.putExtra("token", token);
                    String user = input.getText().toString();
                    System.out.println("IN HEADER : " + user);
                    intent.putExtra("user", user);
                    context.startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        img.setOnClickListener( new View.OnClickListener()
        {
            public void onClick(View view)
            {
                if (!input.getText().toString().isEmpty()) {
                    Intent intent = new Intent(context, Rapport.class);
                    intent.putExtra("token", token);
                    String user = input.getText().toString();
                    intent.putExtra("user", user);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_header, menu);
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
