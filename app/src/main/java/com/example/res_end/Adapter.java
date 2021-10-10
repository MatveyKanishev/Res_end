package com.example.res_end;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class Adapter extends BaseAdapter {
    Context context; ArrayList<Sms> list_sms;
    public Adapter(Context context, ArrayList<Sms> list_sms){
        this.context=context;
        this.list_sms=list_sms;
    }

    @Override
    public int getCount() {
        return list_sms.size();
    }

    @Override
    public Object getItem(int position) {
        return list_sms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sms sms = list_sms.get(position);
        convertView= LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        TextView sent = convertView.findViewById(R.id.sent);
        TextView recd = convertView.findViewById(R.id.recd);
        TextView sent_time = convertView.findViewById(R.id.sent_time);
        TextView recd_time = convertView.findViewById(R.id.recd_time);
        recd_time.setText(sms.time);

        if(sms.inndicator==null){
            sent.setText(sms.message);
//            sent_time.setText(sms.time);
        }
        else {
            recd.setText(sms.message);
//            recd_time.setText(sms.time);
        }

    return convertView;}
}
