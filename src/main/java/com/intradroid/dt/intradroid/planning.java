package com.intradroid.dt.intradroid;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.AsExistingPropertyTypeSerializer;

import android.text.format.Time;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class planning extends ActionBarActivity {

    private String token;
    static private String[] name_days = {
            "Monday",
            "Tuesday",
            "Wesnesday",
            "Thurday",
            "Friday",
            "Saturday",
            "Sunday",
    };

    static private String[] name_months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        Calendar time = Calendar.getInstance();
        //time.set(2015, 01, 01);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String current_day = dateFormat.format(time.getTime());

        final RequestAPI request = new RequestAPI();
        String param[] = {token, current_day, current_day};
        //String param[] = {token, "2015-02-02", "2015-02-02"};
        String paramName[] = {"token", "start", "end"};
        String result = request.performQuery("planning", paramName, param);

        try {
            setContentView(R.layout.activity_planning);
            setDateWeek(time);
            if (!result.equals("{}")) {
                ObjectMapper mapper = new ObjectMapper();

                result.replace("class", "_class");

                EventPlanningJSON event_planning[] = mapper.readValue(result, EventPlanningJSON[].class);

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout days_parent = (LinearLayout) findViewById(R.id.filters);
                for (int i = 0; i < 7; i++) {
                    View days = inflater.inflate(R.layout.day_planning, null);
                    TextView day = (TextView) days.findViewById(R.id.day);
                    day.setText(getDateDay(time, i));
                    days.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    days_parent.addView(days);
                    LinearLayout day_block = (LinearLayout) findViewById(R.id.day_block);
                    day_block.setId(1000 + i);
                    click_day(day, request, time, dateFormat, i);
                    create_slot(request, time, i, event_planning, dateFormat);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getHour(String time)
    {
        int index = time.indexOf(" ");
        return (time.substring(index + 1, index + 6));
    }

    private String getFormatTime(int time, int nbr)
    {
        String  nb_time;

        if (time < 10)
            nb_time = "0" + (time + nbr);
        else
            nb_time = Integer.toString(time + nbr);
        return (nb_time);
    }

    private void setDateWeek(Calendar time)
    {
        Calendar save_time = time;
        TextView date_week = (TextView) findViewById(R.id.date_week);
        int day = time.get(Calendar.DAY_OF_WEEK);
        if (Integer.parseInt(getFormatTime(time.get(Calendar.DAY_OF_MONTH), 0)) == 1 && (time.get(Calendar.DAY_OF_WEEK) - 2) != 0)
        {
            time.add(Calendar.MONTH, -1);
            time.add(Calendar.DAY_OF_MONTH, time.getActualMaximum(Calendar.DAY_OF_MONTH) - (8 - day));
        }
        day = Integer.parseInt(getFormatTime(time.get(Calendar.DAY_OF_MONTH), 0)) - (time.get(Calendar.DAY_OF_WEEK) - 2);
        int day2 = Integer.parseInt(getFormatTime(time.get(Calendar.DAY_OF_MONTH), 0)) + (8 - time.get(Calendar.DAY_OF_WEEK));
        String month = getFormatTime(time.get(Calendar.MONTH), 1);
        String month2 = month;

        if (Integer.parseInt(getFormatTime(time.get(Calendar.DAY_OF_MONTH), 0)) + (8 - time.get(Calendar.DAY_OF_WEEK)) > time.getActualMaximum(Calendar.DAY_OF_MONTH))
        {
            day2 -= time.getActualMaximum(Calendar.DAY_OF_MONTH);
            month2 = getFormatTime(time.get(Calendar.MONTH), 2);
        }
        String date = "Du " + day + "/" + month + " au " + getFormatTime(day2, 0) + "/" + month2;
        date_week.setText(date);
        time = save_time;
    }

    private String getDateDay(Calendar time, int nbr)
    {
        Calendar new_time = Calendar.getInstance();
        SimpleDateFormat name_day = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        SimpleDateFormat name_month = new SimpleDateFormat("MMM", Locale.ENGLISH);
        int day = time.get(Calendar.DAY_OF_MONTH) - (time.get(Calendar.DAY_OF_WEEK) - 2);

        day += nbr;
        new_time.set(time.get(Calendar.YEAR), time.get(Calendar.MONTH), day);
        if (day > time.getActualMaximum(Calendar.DAY_OF_MONTH))
        {
            day -= time.getActualMaximum(Calendar.DAY_OF_MONTH);
            new_time.set(time.get(Calendar.YEAR), time.get(Calendar.MONTH) + 1, day);
        }
        String date_day = name_day.format(new_time.getTime()) + " " + getFormatTime(new_time.get(Calendar.DAY_OF_MONTH), 0) + " " + name_month.format(new_time.getTime());
        return (date_day);
    }

    private void setTokenIcon(ImageView token, EventPlanningJSON event)
    {
        if (event.isAllow_token())
            token.setImageResource(R.drawable.icon_token);
        else
            token.setImageResource(R.drawable.icon_late_token);
    }

    private void setSubIcon(ImageView sub, EventPlanningJSON event)
    {
        if (event.isPast() && event.getEvent_registered() != null && (event.getEvent_registered().equals("present") || event.getEvent_registered().equals("registered")))
            sub.setImageResource(R.drawable.icon_late_unsubscribe);
        else if (event.isPast())
            sub.setImageResource(R.drawable.icon_late_subscribe);
        else if (!event.isPast() && event.getEvent_registered() != null && event.getEvent_registered().equals("registered"))
            sub.setImageResource(R.drawable.icon_unsubscribe);
        else if (!event.isPast())
            sub.setImageResource(R.drawable.icon_subscribe);
    }

    private void subscribe(final EventPlanningJSON event, final ImageView sub, final RequestAPI request)
    {
        sub.setOnClickListener(
            new View.OnClickListener()
            {
                public void onClick(View view)
                {
                    try {
                        String param[] = {token, Integer.toString(event.getScolaryear()), event.getCodemodule(),
                                event.getCodeinstance(), event.getCodeacti(), event.getCodeevent()};
                        String paramName[] = {"token", "scolaryear", "codemodule", "codeinstance", "codeacti", "codeevent"};
                        String result = request.performQuery("subscribe_event", paramName, param);

                        if (result.contains("{\t}"))
                        {
                            event.setEvent_registered("registered");
                            setSubIcon(sub, event);
                        } else {
                            Toast.makeText(planning.this, result, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    private Calendar parseWeek(CharSequence date_week, int i)
    {
        Calendar time = Calendar.getInstance();
        int month;
        int day;

        day = Integer.parseInt(date_week.subSequence(3, 5).toString());
        month = Integer.parseInt(date_week.subSequence(6, 8).toString()) - 1;
        //time.set(time.get(Calendar.YEAR), month, day);

        day += i;
        time.set(time.get(Calendar.YEAR), month, day);
        if (day > time.getActualMaximum(Calendar.DAY_OF_MONTH))
        {
            day -= time.getActualMaximum(Calendar.DAY_OF_MONTH);
            time.set(time.get(Calendar.YEAR), time.get(Calendar.MONTH) + 1, day);
        }
        System.out.println(time.getTime());
        return (time);
    }

    private void click_day(final TextView day, final RequestAPI request, final Calendar time, final DateFormat dateFormat, final int i)
    {
        day.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        try {
                            Calendar new_time = parseWeek(((TextView)findViewById(R.id.date_week)).getText(), i);
                            String current_day = dateFormat.format(new_time.getTime());
                            String param[] = {token, current_day, current_day};
                            String paramName[] = {"token", "start", "end"};
                            String result = request.performQuery("planning", paramName, param);

                            if (!result.equals("{}")) {
                                ObjectMapper mapper = new ObjectMapper();

                                result.replace("class", "_class");

                                EventPlanningJSON event_planning[] = mapper.readValue(result, EventPlanningJSON[].class);

                                //if (i == time.get(Calendar.DAY_OF_WEEK) - 2)
                                //{
                                    LayoutInflater inflater_child = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    LinearLayout event_parent;
                                    event_parent = (LinearLayout) findViewById(1000 + i);
                                    for (int j = 0; j < event_planning.length - 1; j++)
                                    {
                                        if (/*event_planning[j].getStart().contains(dateFormat.format(time.getTime())) &&*/ event_planning[j].getSemester() == 5) // change 5 by user_semester
                                        {
                                            if (event_planning[j].isModule_registered())
                                            {
                                                if (event_planning[j].getEvent_registered() != null && (event_planning[j].getEvent_registered().equals("present") || event_planning[j].getEvent_registered().equals("registered")))
                                                {
                                                    View slot_planning = inflater_child.inflate(R.layout.slot_planning, null);
                                                    TextView hours = (TextView) slot_planning.findViewById(R.id.hours);
                                                    hours.setText("-" + getHour(event_planning[j].getStart()) + "-" + getHour(event_planning[j].getEnd()) + ": ");
                                                    TextView name = (TextView) slot_planning.findViewById(R.id.name);
                                                    name.setText(event_planning[j].getTitlemodule() + " - " + event_planning[j].getActi_title() + " - " +
                                                            event_planning[j].getType_title() + " - " + (event_planning[j].getRoom()).getCode());
                                                    ImageView sub = (ImageView) slot_planning.findViewById(R.id.sub);
                                                    setSubIcon(sub, event_planning[j]);
                                                    subscribe(event_planning[j], sub, request);
                                                    ImageView token = (ImageView) slot_planning.findViewById(R.id.token);
                                                    setTokenIcon(token, event_planning[j]);
                                                    slot_planning.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                                                    event_parent.addView(slot_planning);
                                                }
                                            }
                                        }
                                    }
                                //}
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void create_slot(RequestAPI request, Calendar time, int i, EventPlanningJSON[] event_planning, DateFormat dateFormat)
    {
        if (i == time.get(Calendar.DAY_OF_WEEK) - 2)
        {
            LayoutInflater inflater_child = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout event_parent;
            event_parent = (LinearLayout) findViewById(1000 + i);
            for (int j = 0; j < event_planning.length - 1; j++)
            {
                if (event_planning[j].getStart().contains(dateFormat.format(time.getTime())) && event_planning[j].getSemester() == 5) // change 5 by user_semester
                {
                    if (event_planning[j].isModule_registered())
                    {
                        if (event_planning[j].getEvent_registered() != null && (event_planning[j].getEvent_registered().equals("present") || event_planning[j].getEvent_registered().equals("registered")))
                        {
                            View slot_planning = inflater_child.inflate(R.layout.slot_planning, null);
                            TextView hours = (TextView) slot_planning.findViewById(R.id.hours);
                            hours.setText("-" + getHour(event_planning[j].getStart()) + "-" + getHour(event_planning[j].getEnd()) + ": ");
                            TextView name = (TextView) slot_planning.findViewById(R.id.name);
                            name.setText(event_planning[j].getTitlemodule() + " - " + event_planning[j].getActi_title() + " - " +
                                    event_planning[j].getType_title() + " - " + (event_planning[j].getRoom()).getCode());
                            ImageView sub = (ImageView) slot_planning.findViewById(R.id.sub);
                            setSubIcon(sub, event_planning[j]);
                            subscribe(event_planning[j], sub, request);
                            ImageView token = (ImageView) slot_planning.findViewById(R.id.token);
                            setTokenIcon(token, event_planning[j]);
                            slot_planning.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                            event_parent.addView(slot_planning);
                        }
                    }
                }
            }
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_planning, menu);
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
    }*/
}
