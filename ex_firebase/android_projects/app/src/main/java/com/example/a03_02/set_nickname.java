package com.example.a03_02;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class set_nickname extends AppCompatActivity {
    private EditText et_nn;
    private FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_nickname);
        et_nn = findViewById(R.id.inputnn);
        Button btn_nn = findViewById(R.id.btnnn);
        btn_nn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                set_nn();
            }
        });
    }
    private void set_nn()
    {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        String input_nn = et_nn.getText().toString();

        Map<String, Object> childUpdate = new HashMap<>();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String cur_uid = "";

        if (currentUser == null) {
            cur_uid = "currentUserNull";
        }
        else {
            cur_uid = currentUser.getUid();
        }
        User user = new User(input_nn, cur_uid);
        Map<String, Object> postValues = user.toMap();

        childUpdate.put("/user/" + input_nn, postValues);
        mRef.updateChildren(childUpdate);
        G.user = user;

        Intent intent = new Intent(this, AfterActivity.class);
        startActivity(intent);
    }
}
