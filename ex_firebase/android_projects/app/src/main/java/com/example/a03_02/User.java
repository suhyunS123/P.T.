package com.example.a03_02;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    public String nickname;
    public String Uid;
    public User() {
        //Default
    }
    public User(String nickname, String Uid){
        this.nickname = nickname;
        this.Uid = Uid;
    }
    public String getNickname() {
        return nickname;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("nickname", nickname);
        result.put("Uid", Uid);
        return result;
    }
}
