package com.example.pt_signup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class KeywordAdapter extends BaseAdapter {
    private ArrayList<KeywordItem> listViewArr = new ArrayList<>();

    public KeywordAdapter() {

    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        final int idx = index;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_keyword, parent, false);
        }

        ImageView ImageView = (ImageView)convertView.findViewById(R.id.imageView1);
        TextView TextViewCID = (TextView)convertView.findViewById(R.id.textView3);
        TextView TextViewName = (TextView)convertView.findViewById(R.id.textView1);
        TextView TextViewAddr = (TextView)convertView.findViewById(R.id.textView2);

        KeywordItem kwItem = listViewArr.get(idx);

        Glide.with(context).load(kwItem.getImageUrl()).into(ImageView);
        TextViewCID.setText(kwItem.getCID());
        TextViewName.setText(kwItem.getName());
        TextViewAddr.setText(kwItem.getAddr());

        return convertView;
    }

    @Override
    public int getCount() {
        return listViewArr.size();
    }

    @Override
    public long getItemId(int idx) {
        return (idx);
    }

    @Override
    public Object getItem(int idx) {
        return listViewArr.get(idx);
    }

    public void addItem(String CID, String imageUrl, String name, String addr) {
        KeywordItem item = new KeywordItem();

        item.setCID(CID);
        item.setImageUrl(imageUrl);
        item.setName(name);
        item.setAddr(addr);
        listViewArr.add(item);
    }

    public void removeAll() {
        listViewArr.removeAll(listViewArr);
    }
}
