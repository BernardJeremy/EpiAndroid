package com.intradroid.dt.intradroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

/**
 * Created by bernar_w on 31/01/2015.
 */
public class Rapport extends ActivityManagement{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        String userName = intent.getStringExtra("user");

        String param[] = {token, userName};
        String paramName[] = {"token", "user"};

        System.out.println("IN RAPPORT : " + userName);

        setContentView(R.layout.activity_user);

        initMenuDrawer();
        Header.searchOnEvent((ImageView) findViewById(R.id.search_button), (EditText) findViewById(R.id.input_search), this, token);

        RequestAPI.performQuery("user", paramName, param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                try {
                    String result = String.valueOf(response);

                    RapportJson rapport = RequestAPI.getMapper().readValue(result, RapportJson.class);

                    setAllDataRapport(rapport);
                    setProfileImageRapport(rapport);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, JSONObject errorResponse)
            {
                Toast.makeText(Rapport.this, String.valueOf(errorResponse), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void setAllDataRapport(RapportJson rapport)
    {
        final TextView credit = (TextView) findViewById(R.id.credit);
        final TextView name = (TextView) findViewById(R.id.big_name);
        final TextView login = (TextView) findViewById(R.id.login);
        final TextView promo = (TextView) findViewById(R.id.promo);
        final TextView cycle = (TextView) findViewById(R.id.cycle);
        final TextView gpa = (TextView) findViewById(R.id.gpa);
        final TextView gpaLabel = (TextView) findViewById(R.id.gpa_label);

        name.setText(rapport.getTitle());
        login.setText(rapport.getLogin());
        promo.setText(rapport.getSchool_title() + " " + rapport.getPromo());
        cycle.setText("Cycle " + rapport.getCourse_code());
        credit.setText(rapport.getCredits());
        gpaLabel.setText("G.P.A");
        gpa.setText(rapport.getGpa()[0].getGpa());
    }

    public void setProfileImageRapport(RapportJson rapport)
    {
        try {
            RequestAPI.getImageQuery(rapport.getPicture(), new BinaryHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, org.apache.http.Header[] headers, byte[] fileData) {
                    System.out.println("Load Img OK");
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
