package com.intradroid.dt.intradroid;

import android.content.Intent;
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

public class LoginActivity extends ActionBarActivity {

    private String TokenJson = "00000000";

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
                            RequestAPI request = new RequestAPI();

                            String param[] = {inputLogin.getText().toString(), inputPassword.getText().toString()};
                            String paramName[] = {"login", "password"};
                            String result = request.performQuery("login", paramName, param);

                            if (!result.equals("")) {
                                ObjectMapper mapper = new ObjectMapper();
                                TokenJSON token = mapper.readValue(result, TokenJSON.class);
                                TokenJson = token.getToken();
                                Log.v("TOKEN", "Final Token get from JSON : " + TokenJson);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("token", token.getToken());
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Bad Login or Password", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
