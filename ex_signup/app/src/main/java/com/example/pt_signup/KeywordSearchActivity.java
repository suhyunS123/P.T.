package com.example.pt_signup;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class KeywordSearchActivity extends AppCompatActivity {
    private XmlPullParserFactory factory;
    private XmlPullParser parser;
    private KeywordAdapter adapter;

    private Button btn_search;
    private EditText EditTextkeyin;
    private ListView ListView;

    private String keyword;
    private static final String TAG = "anony";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keywordsearch);

        StrictMode.enableDefaults();

        btn_search = (Button) findViewById(R.id.btn_keywordsearch);
        EditTextkeyin = (EditText) findViewById(R.id.editText_keyword);

        ListView = (ListView) findViewById(R.id.listView_keywordsearch);

        adapter = new KeywordAdapter();
        ListView.setAdapter(adapter);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = EditTextkeyin.getText().toString();
                keyword = keyword.trim(); //공백만 있어도 에러
                if (keyword.getBytes().length > 0) {
                    try {
                        set_parser();
                        parsing_loop();
                    } catch (Exception e) {
                        Log.d(TAG, "set_parser or parsing_loop 오류");
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void set_parser() throws IOException, XmlPullParserException {
        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        String path = "http://api.visitkorea.or.kr/openapi/service/rest/EngService/searchKeyword?MobileOS=AND&MobileApp=PT-signup&ServiceKey=Zyf6bxgH%2Bs226Nze1ZkFl8FfEFfeiB4gqYOTNLSetuoFTmhA7Muq7lbV%2FAbX0NSu6oTYs6vcby91mYnCe4SBWQ%3D%3D&arrange=P&keyword=" + keyword;

        URL url = new URL(path);
        InputStream is = url.openStream();
        parser.setInput(new InputStreamReader(is, "UTF-8"));


    }

    public void parsing_loop() throws XmlPullParserException, IOException {
        final int STEP_NONE = 0;
        final int STEP_ADDR = 1;
        final int STEP_CID = 2;
        final int STEP_IMG = 3;
        final int STEP_NAME = 4;

        int step = STEP_NONE;
        String CID = null;
        String imageUrl = null;
        String name = null;
        String addr = null;

        int eventType = parser.getEventType();
        adapter.removeAll();


        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                Log.d(TAG, "start Document");
                parser.next();
                eventType = parser.getEventType();
            }
            Log.d(TAG, "next success");
            if (eventType == XmlPullParser.START_TAG) {
                String startTag = parser.getName();
                Log.d(TAG, "startTag읽음"+startTag);
                if (startTag.equals("addr1")) {
                    step = STEP_ADDR;
                } else if (startTag.equals("contentid")) {
                    step = STEP_CID;
                } else if (startTag.equals("firstimage")) {
                    step = STEP_IMG;
                } else if (startTag.equals("title")) {
                    step = STEP_NAME;
                } else {
                    step = STEP_NONE;
                }
            } else if (eventType == XmlPullParser.TEXT && step != STEP_NONE) {
                String text = parser.getText();
                if (step == STEP_ADDR) {
                    addr = text;
                } else if (step == STEP_CID) {
                    CID = text;
                } else if (step == STEP_IMG) {
                    imageUrl = text;
                } else if (step == STEP_NAME) {
                    name = text;
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                String endTag = parser.getName();
                if (endTag.equals("item")) {
                    adapter.addItem(CID, imageUrl, name, addr);
                    step = STEP_NONE;
                }
            } else {

            }

            eventType = parser.next();
        }

    }

}
