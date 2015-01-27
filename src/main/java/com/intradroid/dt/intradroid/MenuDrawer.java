package com.intradroid.dt.intradroid;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by bernar_w on 27/01/2015.
 */
public class MenuDrawer extends ActionBarActivity {

    private String[] menuItem;
    private DrawerLayout layout;
    private ListView list;

    protected String token;

    public MenuDrawer ()
    {
    }

    protected void initMenuDrawer()
    {
        menuItem= getResources().getStringArray(R.array.items_menu);
        layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        list = (ListView) findViewById(R.id.left_drawer);

        System.out.println(list);

        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];

        int i = 0;

        for (String value : menuItem) {
            drawerItem[i] = new ObjectDrawerItem(value);
            i += 1;
        }

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new DrawerItemClickListener());

    }

    private void selectItem(int position)
    {
        switch (position) {
            case 0:
                System.out.println("Home");
                break;
            case 1:
                System.out.println("Marks");
                break;
            case 2:
                System.out.println("Modules");
                break;
            case 3:
                System.out.println("Planning");
                break;
        default:
            break;
        }
        list.setItemChecked(position, true);
        list.setSelection(position);
        layout.closeDrawer(list);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }
}
