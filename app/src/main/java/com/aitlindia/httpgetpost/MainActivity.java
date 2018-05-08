package com.aitlindia.httpgetpost;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView RespText;
    EditText editTextGET,editTextPOST, editurl;
    Button seturl;
  /*  String URL="http://aitlhttp.herokuapp.com/httprequest";
*/
  String URL="http://192.168.43.239:3000/signup/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RespText = (TextView)findViewById(R.id.textView2);
        editTextGET = (EditText)findViewById(R.id.editText2);
        editTextPOST = (EditText)findViewById(R.id.editText3);
        editurl = (EditText)findViewById(R.id.editurl);
        seturl = (Button) findViewById(R.id.seturl);
    }

    public void OnClickGET(View view) {

        new myAsyncTask("GET").execute(URL,"Hi AITL! GET Req ",editTextGET.getText().toString());
    }

    public void OnClickPOST(View view) {
        new myAsyncTask("POST").execute(URL,"Hi AITL! POST Req ",editTextPOST.getText().toString());
    }

    public void OnClickSetUrl(View view) {
       URL =  editurl.getText().toString();
    }

    private class myAsyncTask extends AsyncTask<String,Integer,String>{

        ProgressDialog myDialog = new ProgressDialog(MainActivity.this);

        String ReqTYPE = "GET";

        public myAsyncTask(String TYPE){
            ReqTYPE = TYPE;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            myDialog.setMessage("Waiting for response !");
            myDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            JSONParser myRquest = new JSONParser();

            JSONObject myObj = new JSONObject();
            HashMap myParam = new HashMap();
            myParam.put("data",params[1]);
            myParam.put("user",params[2]);

            if(ReqTYPE.equals("GET")){
                myObj = myRquest.makeHttpRequest(params[0],"GET",myParam);
            }else if(ReqTYPE.equals("POST")){
                myObj = myRquest.makeHttpRequest(params[0],"POST",myParam);
            }

            publishProgress(10);


            return myObj.toString();
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);
            myDialog.cancel();

            Log.d("HTTP Class", "Resp Log: "+resp);

            RespText.setText(resp);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }



}
