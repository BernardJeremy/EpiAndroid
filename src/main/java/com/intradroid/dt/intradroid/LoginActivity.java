package com.intradroid.dt.intradroid;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public class LoginActivity extends ActivityManagement {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button mButton = (Button)findViewById(R.id.connect_button);
        final EditText inputLogin = (EditText)findViewById(R.id.input_login);
        final EditText inputPassword = (EditText)findViewById(R.id.input_password);

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        try {

                            System.out.println("CONNECTING");
                            String param[] = {inputLogin.getText().toString(), inputPassword.getText().toString()};
                            String paramName[] = {"login", "password"};

                            RequestAPI.performQuery("login", paramName, param, new JsonHttpResponseHandler() {

                                @Override
                                public void onSuccess(int statusCode, org.apache.http.Header[] headers, JSONObject response) {
                                    try {
                                        String TokenJson;
                                        TokenJSON token = RequestAPI.getMapper().readValue(String.valueOf(response), TokenJSON.class);
                                        TokenJson = token.getToken();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("token", TokenJson);
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, org.apache.http.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                                    Toast.makeText(LoginActivity.this, "Bad Login or Password", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
