package com.example.minhvu.testing_android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;


public class MainActivity extends ActionBarActivity {
    CallbackManager callbackManager;
    LoginManager loginManager;
    String s_signin = "http://10.0.2.2:5000/accounts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignInAction();
        SignUpAction();
        FacebookAction();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
    }

    public void SignInAction() {
        Button signin = (Button) findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView v_username = (TextView) findViewById(R.id.signin_username);
                String s_username = v_username.getText().toString();

                TextView v_password = (TextView) findViewById(R.id.signin_password);
                String s_password = v_password.getText().toString();

                LogIn login = new LogIn(s_username, s_password, "", "");
                login.execute(s_signin);
            }
        });
    }

    public void SignUpAction() {
        Button signup = (Button) findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this, Signup.class);
                MainActivity.this.startActivity(newIntent);
            }
        });
    }

    public void FacebookAction() {
        callbackManager = CallbackManager.Factory.create();

        loginManager = LoginManager.getInstance();

        Collection<String> permissions = Arrays.asList("email","public_profile", "user_friends");

        loginManager.logInWithReadPermissions(this, permissions); // Null Pointer Exception here

        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        final AccessToken accessToken = loginResult.getAccessToken();
                        GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                                try {
                                    String s_username = user.get("first_name").toString() +
                                            user.get("last_name").toString();
                                    String s_facebook_id = user.get("id").toString();
                                    LogIn login = new LogIn(s_username, "", "",s_facebook_id);
                                    login.execute(s_signin);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
    }
}

