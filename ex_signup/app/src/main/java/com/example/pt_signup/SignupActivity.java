package com.example.pt_signup;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SignupActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "anony";

    private EditText EditTextName;
    private EditText EditTextPw;
    private TextView TextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Log.d(TAG, "in signupActivity");

        EditTextName = (EditText)findViewById(R.id.editText_main_name);
        EditTextPw = (EditText)findViewById(R.id.editText_main_pw);
        TextViewResult = (TextView)findViewById(R.id.textView_main_result);

        Button btn_signup = (Button)findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //firebase 인증에서 현재 유저의 UID가져오기
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String UID = user.getUid();
                String name = EditTextName.getText().toString();
                String pw = EditTextPw.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/insert.php", UID,pw,name);

                EditTextName.setText("");
                EditTextPw.setText("");
            }
        });
    }

    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SignupActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "POST response - " + result);
            progressDialog.dismiss();
            TextViewResult.setText(result);
            Log.d(TAG, "POST response - " + result);
            updateUI();
        }

        @Override
        protected String doInBackground(String... params) {
            String UID = (String)params[1];
            String pw = (String)params[2];
            String name = (String)params[3];

            String serverURL = (String)params[0];

            String postParameters = "UID=" + UID + "&pw=" + pw + "&name=" + name;

            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                //timeout설정
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));

                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code : " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == httpURLConnection.HTTP_OK) {
                    Log.d(TAG, "connected");
                    inputStream = httpURLConnection.getInputStream();
                }
                else {
                    Log.d(TAG, "something wrong...");
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString();

            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error", e);
                return new String("Error: " + e.getMessage());
            }
        }

    }

    private void updateUI() {
        //DefaultActivity로 넘어가는 함수
        Intent intent = new Intent(this, DefaultActivity.class);
        startActivity(intent);
        finish();
    }
}
