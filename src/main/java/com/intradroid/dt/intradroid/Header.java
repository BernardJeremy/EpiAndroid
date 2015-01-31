package com.intradroid.dt.intradroid;

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

    public static void displayPhoto(final ActionBarActivity context, EditText input){
        try {

            RequestAPI.getImageQuery("https://cdn.local.epitech.eu/userprofil/profilview/" + input.getText().toString() + ".jpg", new BinaryHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] fileData) {
                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    ImageView toastView = new ImageView(context.getApplicationContext());

                    Bitmap bmp = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
                    if (bmp == null) {
                        Toast.makeText(context.getApplicationContext(), "Bad Login", Toast.LENGTH_SHORT).show();
                    } else {
                        bmp = Bitmap.createScaledBitmap(bmp, 450, 500, false);
                        toastView.setImageBitmap(bmp);
                        toast.setView(toastView);
                        toast.show();
                    }
                }

                @Override
                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] binaryData, java.lang.Throwable error)
                {
                    Toast.makeText(context.getApplicationContext(), "Bad Login", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), "Bad Login", Toast.LENGTH_SHORT).show();
        }
    }

    public static void searchOnEvent(ImageView img, final EditText input, final ActionBarActivity context)
    {

        input.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    Header.displayPhoto(context, input);
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
                    Header.displayPhoto(context, input);
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
