package com.aitlindia.httpgetpost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by krishna on 25/3/17.
 */
public class SignUp extends AppCompatActivity {

    EditText username, password, confirmPass, email,url;
    TextView response;
    int length;
    String URLSignUp="http://192.168.43.239:3000/signup/";
    String URLValidation="http://192.168.43.239:3000/validation/";
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPass = (EditText) findViewById(R.id.confirmPass);
        email = (EditText) findViewById(R.id.email);
        response = (TextView) findViewById(R.id.response);
        url = (EditText) findViewById(R.id.url);


        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                 new SignUp.MyValidation("POST").execute(URLValidation,username.getText().toString());
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               if(!isEmailValid(email.getText().toString())){
                   email.setError("not valid email");
               }

            }
        });

        confirmPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               if(!(password.getText().toString()).equals(confirmPass.getText().toString())){
                 confirmPass.setError("not match with password");
               }
            }
        });
    }

    public void post(View view) {

           if(username.getError() == null && email.getError() == null && confirmPass.getError() == null) {

               response.setText("all done!");

               new SignUp.MySignUp("POST").execute(URLSignUp,
                       username.getText().toString()
                       ,email.getText().toString(),
                       confirmPass.getText().toString());

               Intent intent = new Intent(this,SignIn.class);
               startActivity(intent);

           }
        else {
            response.setText("their is something wrong");
        }
    }

  /*  public void setUrl(View view) {
       URL = url.getText().toString();
        Toast.makeText(this,"url is set",Toast.LENGTH_SHORT).show();
    }
*/
    private class MyValidation extends AsyncTask<String, Integer, String> {

        ProgressDialog myDialog = new ProgressDialog(SignUp.this);

        String ReqTYPE = "GET";

        public MyValidation(String TYPE) {
            ReqTYPE = TYPE;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

         //   myDialog.setMessage("Waiting for response !");
       ///    myDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            JSONParser myRquest = new JSONParser();

            JSONObject myObj = new JSONObject();
            HashMap myParam = new HashMap();

            myParam.put("username", params[1]);

            if (ReqTYPE.equals("GET")) {
                myObj = myRquest.makeHttpRequest(params[0], "GET", myParam);
            } else if (ReqTYPE.equals("POST")) {
                myObj = myRquest.makeHttpRequest(params[0], "POST", myParam);
            }

            publishProgress(10);


            return myObj.toString();
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);
            //myDialog.cancel();

            Log.d("HTTP Class", "Resp Log: " + resp);

            length = resp.length();

            if (length == 24){
                username.setError("userName is already exits");
           }
        }
    }

    private class MySignUp extends AsyncTask<String, Integer, String> {

       String ReqTYPE ;

        public MySignUp(String TYPE) {
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
            myParam.put("email", params[2]);
            myParam.put("confirm", params[3]);

             if (ReqTYPE.equals("POST")) {
                myObj = myRquest.makeHttpRequest(params[0], "POST", myParam);
            }

            publishProgress(10);


            return myObj.toString();
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);

        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
