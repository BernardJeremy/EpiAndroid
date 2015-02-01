package com.intradroid.dt.intradroid;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.BinaryHttpResponseHandler;

import java.net.URL;
import java.util.List;

/**
 * Created by bernar_w on 27/01/2015.
 */
public class ActivityManagement extends ActionBarActivity {

    private String[] menuItem;
    private DrawerLayout layout;
    private ListView list;

    protected String token;
    protected int current;

    public ActivityManagement ()
    {
    }

    protected void initMenuDrawer()
    {
        menuItem = getResources().getStringArray(R.array.items_menu);
        layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        list = (ListView) findViewById(R.id.left_drawer);

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];

        int i = 0;

        for (String value : menuItem) {
            drawerItem[i] = new ObjectDrawerItem(value);
            i += 1;
        }

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new DrawerItemClickListener());

        this.setClickButton();

    }

    private void setClickButton()
    {
        ImageView button = (ImageView) findViewById(R.id.button_menu);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (layout.isDrawerOpen(Gravity.LEFT)) {
                    layout.closeDrawer(Gravity.LEFT);
                } else {
                    layout.openDrawer(Gravity.LEFT);
                }
            }});
    }

    private void selectItem(int position)
    {
        Intent intent = null;
        boolean isMark = false;

        switch (position) {
            case 0:
                break;
            case 1:
                intent = new Intent(this, user.class);
                isMark = true;
                break;
            case 2:
                intent = new Intent(this, user.class);
                isMark = false;
                break;
            case 3:
                intent = new Intent(this, planning.class);
                break;
            default:
                break;
        }

        if (position != 0 && intent != null && this.current != position) {
            intent.putExtra("token", token);
            intent.putExtra("isMark", isMark);
            startActivity(intent);
        } else if (intent != null){
            Toast.makeText(this, "Already on this page !", Toast.LENGTH_SHORT).show();
        }

        list.setItemChecked(position, true);
        list.setSelection(position);
        layout.closeDrawer(list);

        if (position == 0 && current != 0) {
            finish();
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
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
            RequestAPI.getImageQuery("https://cdn.local.epitech.eu/userprofil/profilview/" + infos.getInfos().getLogin() + ".jpg", new BinaryHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] fileData) {
                    final ImageView photo = (ImageView) findViewById(R.id.photo);

                    Bitmap bmp = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
                    photo.setImageBitmap(bmp);
                }

                @Override
                public void onFailure(int statusCode, org.apache.http.Header[] headers, byte[] binaryData, java.lang.Throwable error)
                {
                    System.out.println("Fail Load Img");
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
