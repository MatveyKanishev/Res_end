package com.example.res_end;

import androidx.annotation.RequiresApi;
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
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ConversationActivity2 extends AppCompatActivity {
    public static String tel, name; //класс для беседы
    Adapter adapter;
    Timer mTimer;
    ArrayList<Sms> list_sms = new ArrayList<>();
    TextView textView, textView2, textView3, text;
    String SENT_SMS = "SENT_SMS";
    String DELIVER_SMS = "DELIVER_SMS";
    ListView listView;
    Button button;
    PendingIntent sent_pi, deliver_pi;
    Intent sent_intent = new Intent(SENT_SMS);
    Intent deliver_intent = new Intent(DELIVER_SMS);
    String tel2;



    @RequiresApi(api = Build.VERSION_CODES.N)
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

        mTimer = new Timer();


        button.setOnClickListener(new View.OnClickListener() {//отправка сообщения

            @Override
            public void onClick(View v) {
                if (!text.getText().toString().equals("")){
                    Date d = new Date();


                    CharSequence s  = DateFormat.format("dd-MM-yyyy HH:mm:ss ", d.getTime());

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(tel, null, text.getText().toString(), sent_pi, deliver_pi);

                text.setText("");

                }
            }
        });
        GetSMSList();






    }
    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void startAlarm() {
//        Uri uriSms = Uri.parse("content://sms");
//        Context context=this;
//
//
//
//        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
//
//        mTimer.scheduleAtFixedRate(new TimerTask() {
//
//
//                                       @Override
//                                       public void run() {
//                                           Cursor cur = context.getContentResolver().query(uriSms, null,null,null,null);
//                                           startManagingCursor(cur);
//
//                                           if (cur!=null && cur.moveToFirst() ){
//                                               do{
//
//                                                   if (tel.equals(cur.getString(2))){
////                    builder.append(format1.format(cur.getLong(4)));
////                    builder.append("_jlzf86_");
////                    builder.append(cur.getString(12));
////                    builder.append("_jlzf86_");
////                    builder.append(cur.getString(6));
////                    builder.append("di;set;jkhfyrty");
//                                                       list_sms.add(new Sms(format1.format(cur.getLong(4)), cur.getString(12), cur.getString(6) ));
//
//                                                   }
//
//                                               }while (cur.moveToNext());
////            builder.append("\n");
////            builder.append("\n");
////            String[] words = builder.toString().split("di;set;jkhfyrty");
////
////            for (int j = 0; j <words.length; j++) {
////                String[] words2 = words[j].split("_jlzf86_");
////                list_sms.add(new Sms(words2[0],words2[1],words2[2]));
////            }
//                                               adapter = new Adapter(context,list_sms);
//
////            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item_2,
////                    R.id.texty, words);
////                                               listView.setAdapter(adapter);
//
//                                               if(cur!=null) cur.close();
//                                           }
//
//                                           runOnUiThread(new Runnable() {
//                                               @Override
//                                               public void run() {
//
//                                                   listView.setAdapter(adapter);
//
//                                               }
//                                           });
//                                       }
//                                   }
//                , 0        // Это задержка старта, сейчас 0;
//                , 50000); // Это Ваш период в 10 минут;
//    }

    private void cancelTimer() {

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
//    void todo_sms_cont(){ //метод для получения смс определённого контакта (старый)
//        ContentResolver resolver = getContentResolver();//  \/ 1 ничего - все сообщения 2 inbox-полученные 3 sent-отправленные
//        Cursor cur = resolver.query(Uri.parse("content://sms/"), new String[]{"_id","date","address","body"},null,null,null);//пираметры запросса
//        StringBuilder builder = new StringBuilder();
//        if (cur!=null && cur.moveToFirst() ){
//            do{
//
//                if (tel.equals(cur.getString(2))){
//                builder.append(cur.getInt(0));
//
//                    builder.append("\n");
//                    Date date = new Date(cur.getInt(1));// пример Fri May 30 08:20:12 MSD 2008
//                    String[] as = date.toString().split(" ");
////                    switch (as[0]){
////                        case "Fri" :
////                            as[0]= String.valueOf(R.string.fri); break;
////                        case "Mon" :
////                            as[0]= String.valueOf(R.string.mon); break;
////                        case "Tue" :
////                            as[0]= String.valueOf(R.string.tue); break;
////                        case "Wed" :
////                            as[0]= String.valueOf(R.string.wed); break;
////                        case "Thu" :
////                            as[0]= String.valueOf(R.string.thu); break;
////                        case "Sat" :
////                            as[0]= String.valueOf(R.string.sat); break;
////                        case "Sun" :
////                            as[0]= String.valueOf(R.string.sun); break;
////                        default:
////                            as[0]="error"; break;
////
////                    }
////                    switch (as[1]){
////                        case "Jan" :
////                            as[1]= String.valueOf(R.string.Jan); break;
////                        case "Feb" :
////                            as[1]= String.valueOf(R.string.Fed); break;
////                        case "Mar" :
////                            as[1]= String.valueOf(R.string.Mar); break;
////                        case "Apr" :
////                            as[1]= String.valueOf(R.string.Apr); break;
////                        case "May" :
////                            as[1]= String.valueOf(R.string.May); break;
////                        case "Jun" :
////                            as[1]= String.valueOf(R.string.Jun); break;
////                        case "Jul" :
////                            as[1]= String.valueOf(R.string.Jul); break;
////                        case "Aug" :
////                            as[1]= String.valueOf(R.string.Aug); break;
////                        case "Sep" :
////                            as[1]= String.valueOf(R.string.Sep); break;
////                        case "Oct" :
////                            as[1]= String.valueOf(R.string.Oct); break;
////                        case "Nov" :
////                            as[1]= String.valueOf(R.string.Nov); break;
////                        case "Dec" :
////                            as[1]= String.valueOf(R.string.Dec); break;
////                            default:
////                            as[1]="error"; break;
////
////                    }
//                    switch (as[0]){
//                        case "Fri" :
//                            as[0]= "пт"; break;
//                        case "Mon" :
//                            as[0]= "пн"; break;
//                        case "Tue" :
//                            as[0]= "вт"; break;
//                        case "Wed" :
//                            as[0]= "ср"; break;
//                        case "Thu" :
//                            as[0]= "чт"; break;
//                        case "Sat" :
//                            as[0]= "сб"; break;
//                        case "Sun" :
//                            as[0]= "вс"; break;
//                        default:
//                            as[0]="error"; break;
//
//                    }
//                    switch (as[1]){
//                        case "Jan" :
//                            as[1]= "янв."; break;
//                        case "Feb" :
//                            as[1]= "февю"; break;
//                        case "Mar" :
//                            as[1]= "мар."; break;
//                        case "Apr" :
//                            as[1]= "апр."; break;
//                        case "May" :
//                            as[1]= "май"; break;
//                        case "Jun" :
//                            as[1]= "июн."; break;
//                        case "Jul" :
//                            as[1]= "июл."; break;
//                        case "Aug" :
//                            as[1]= "авг."; break;
//                        case "Sep" :
//                            as[1]= "сен."; break;
//                        case "Oct" :
//                            as[1]= "окт."; break;
//                        case "Nov" :
//                            as[1]= "ноя."; break;
//                        case "Dec" :
//                            as[1]= "дек."; break;
//                        default:
//                            as[1]="error"; break;
//
//                    }
//                    String Date=""+as[0]+" "+as[2]+" "+as[1]+" "+as[3]+" "+as[5];
//
//
//
//
//                builder.append(Date);
//                    builder.append("\n");
//                builder.append(cur.getString(2));
//                builder.append("\n");
//
//                builder.append(cur.getString(3));
//                    builder.append("\n");
//
//
//                builder.append("di;set;jkhfyrty");
//
//                }
//
//            }while (cur.moveToNext());
//            builder.append("\n");
//            builder.append("\n");
//        }
//        String[] words = builder.toString().split("di;set;jkhfyrty");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.list_item_2,
//                R.id.texty, words);
//        listView.setAdapter(adapter);
//
////        textView3.setText(builder.toString());
//
//        if(cur!=null) cur.close();
//
//
//    }
    @RequiresApi(api = Build.VERSION_CODES.N)// новый метод для получения sms
    public  void GetSMSList(){
        Uri uriSms = Uri.parse("content://sms");
        Context context=this;
        Cursor cur = context.getContentResolver().query(uriSms, null,null,null,null);




        startManagingCursor(cur);

        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");

        if (cur!=null && cur.moveToFirst() ){
            do{

                if (tel.equals(cur.getString(2))){
////
                    list_sms.add(new Sms(format1.format(cur.getLong(4)), cur.getString(12), cur.getString(6) ));
//
                }

            }while (cur.moveToNext());
//
//
            adapter = new Adapter(this, list_sms);

//
            listView.setAdapter(adapter);

            if(cur!=null) cur.close();
        }
//
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
                    Toast.makeText(context, "доставлено", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(context, "отправлено", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(context, "Error sent", Toast.LENGTH_LONG).show();

                    break;
            }
        }
    };
}