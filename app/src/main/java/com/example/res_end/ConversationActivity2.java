package com.example.res_end;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConversationActivity2 extends AppCompatActivity {
    public static String tel, name; //класс для беседы
    TextView textView, textView2, textView3, text;
    String SENT_SMS = "SENT_SMS";
    String DELIVER_SMS = "DELIVER_SMS";
    ListView listView;
    Button button;
    PendingIntent sent_pi, deliver_pi;
    Intent sent_intent = new Intent(SENT_SMS);
    Intent deliver_intent = new Intent(DELIVER_SMS);
    String tel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sent_pi= PendingIntent.getBroadcast(ConversationActivity2.this, 0, sent_intent, 0);
        deliver_pi=PendingIntent.getBroadcast(ConversationActivity2.this,0,deliver_intent,0);
        setContentView(R.layout.activity_conversation2);
        textView=findViewById(R.id.qwerty);
        textView2 = findViewById(R.id.rr);
//        textView3 = findViewById(R.id.qq);
        listView = findViewById(R.id.listView);
        button=findViewById(R.id.button);
        text=findViewById(R.id.text);
        textView.setText(name);
        textView2.setText(tel);
        String [] number = tel.split(" ");
        tel="";
        for (int i = 0; i < number.length; i++) {
            tel=tel+(number[i]);
        }
        String number1[] = tel.split("-");
        tel="";
        for (int i = 0; i < number1.length; i++) {
            tel=tel+(number1[i]);
        }
        todo_sms_cont();
        button.setOnClickListener(new View.OnClickListener() {//отправка сообщения

            @Override
            public void onClick(View v) {

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(tel, null, text.getText().toString(), sent_pi, deliver_pi);
            }
        });





    }
    void todo_sms_cont(){ //метод для получения смс определённого контакта
        ContentResolver resolver = getContentResolver();//  \/ 1 ничего - все сообщения 2 inbox-полученные 3 sent-отправленные
        Cursor cur = resolver.query(Uri.parse("content://sms/"), new String[]{"_id","date","address","body"},null,null,null);//пираметры запросса
        StringBuilder builder = new StringBuilder();
        if (cur!=null && cur.moveToFirst() ){
            do{

                if (tel.equals(cur.getString(2))){
                builder.append(cur.getInt(0));

                    builder.append("\n");
                builder.append(cur.getString(1));
                    builder.append("\n");
                builder.append(cur.getString(2));
                builder.append("\n");

                builder.append(cur.getString(3));
                    builder.append("\n");


                builder.append("di;set;jkhfyrty");

                }

            }while (cur.moveToNext());
            builder.append("\n");
            builder.append("\n");
        }
        String[] words = builder.toString().split("di;set;jkhfyrty");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item_2,
                R.id.texty, words);
        listView.setAdapter(adapter);

//        textView3.setText(builder.toString());

        if(cur!=null) cur.close();


    }

@Override
protected void onResume() {
    super.onResume();
    registerReceiver(sentRaceiver, new IntentFilter(SENT_SMS));
    registerReceiver(deliverRaceiver, new IntentFilter(DELIVER_SMS));
}

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(sentRaceiver);
        unregisterReceiver(deliverRaceiver);
    }

    BroadcastReceiver deliverRaceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()){
                case Activity.RESULT_OK:
                    Toast.makeText(context, "Delivered", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(context, "Error deliver", Toast.LENGTH_LONG).show();

                    break;
            }
        }
    };
    BroadcastReceiver sentRaceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()){
                case Activity.RESULT_OK:
                    Toast.makeText(context, "Sented", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(context, "Error sent", Toast.LENGTH_LONG).show();

                    break;
            }
        }
    };
}