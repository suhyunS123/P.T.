package com.example.pt_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class DefaultActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnRevoke, btnLogout, btnkw;
    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);


        btnLogout = (Button)findViewById(R.id.btn_logout);
        btnRevoke = (Button)findViewById(R.id.btn_revoke);
        btnkw = (Button)findViewById(R.id.btn_gotokeysearch);

        mAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(this);
        btnRevoke.setOnClickListener(this);
        btnkw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotoKeySearch();
            }
        });
    }

    private void gotoKeySearch() {
        Intent intent = new Intent(this, KeywordSearchActivity.class);
        startActivity(intent);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    private void revokeAccess() {
        mAuth.getCurrentUser().delete();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                signOut();
                finishAffinity();
                break;
            case R.id.btn_revoke:
                revokeAccess();
                finishAffinity();
                break;
        }
    }
}
