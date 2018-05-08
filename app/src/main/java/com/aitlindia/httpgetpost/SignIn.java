package com.aitlindia.httpgetpost;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by krishna on 25/3/17.
 */
public class SignIn extends AppCompatActivity {

    EditText user,pass;
    private static int length;
    String URLSignIn = "http://192.168.43.239:3000/signin/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);

    }

    public void login(View view) {

        new SignIn.MySignIn("POST").execute(URLSignIn ,
                user.getText().toString()
                ,pass.getText().toString());

        if (length == 20){
            Intent intent = new Intent(this,temp.class);
            startActivity(intent);
        }
       else {
            Toast.makeText(this,"something is wrong!!",Toast.LENGTH_SHORT).show();
        }

    }

    public void gotoSignUp(View view) {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }


    private class MySignIn extends AsyncTask<String, Integer, String> {

        String ReqTYPE ;

        public MySignIn(String TYPE) {
            ReqTYPE = TYPE;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            JSONParser myRquest = new JSONParser();

            JSONObject myObj = new JSONObject();
            HashMap myParam = new HashMap();

            myParam.put("username", params[1]);
            myParam.put("password", params[2]);


            if (ReqTYPE.equals("POST")) {
                myObj = myRquest.makeHttpRequest(params[0], "POST", myParam);
            }

            return myObj.toString();
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);
            length = resp.length();
        }
    }
}
