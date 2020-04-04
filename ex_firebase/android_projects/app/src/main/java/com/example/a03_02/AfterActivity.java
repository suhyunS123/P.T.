package com.example.a03_02;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AfterActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnRevoke, btnLogout;
    TextView tv_nn, tv_Uid;
    private FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        String cur_uid = G.user.Uid;
        String cur_nn = G.user.nickname;
        tv_nn = (TextView)findViewById(R.id.tv_nn);
        tv_Uid = (TextView)findViewById(R.id.tv_Uid);
        tv_nn.setText(G.user.nickname);
        tv_Uid.setText(G.user.Uid);

        btnLogout = (Button)findViewById(R.id.btn_logout);
        btnRevoke = (Button)findViewById(R.id.btn_revoke);

        mAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(this);
        btnRevoke.setOnClickListener(this);
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