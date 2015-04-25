package com.example.minhvu.testing_android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by minhvu on 4/22/15.
 */
public class Signup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Register();
    }

    public void Register() {
        Button register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView v_username = (TextView) findViewById(R.id.username);
                String s_username = v_username.getText().toString();

                TextView v_password = (TextView) findViewById(R.id.password);
                String s_password = v_password.getText().toString();

                TextView v_retype_pwd = (TextView) findViewById(R.id.retype_pwd);
                String s_retype_pwd = v_retype_pwd.getText().toString();

                TextView v_email = (TextView) findViewById(R.id.email);
                String s_email = v_email.getText().toString();

                if (s_retype_pwd.equals(s_password)) {
                    LogIn login = new LogIn(s_username, s_password, s_email);
                    login.execute("http://10.0.2.2:5000/accounts");
                }
                else Toast.makeText(getApplicationContext(), "Retype password does not match"
                        , Toast.LENGTH_LONG).show();
            }
        });
    }
}
