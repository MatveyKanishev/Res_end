package com.example.res_end;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity { //главный экран он не выводиться но он есть

    Button btn;
    TextView outView, btnSC1, btnSC2;
    BD db;

    SQLiteDatabase wdb, rdb;

    @Override
    protected void onCreate (Bundle savedInstanstanceState){
        if(!checkPermission(Manifest.permission.RECEIVE_SMS)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 222); //вроде запрос разрешения
        }
        super.onCreate(savedInstanstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        outView = findViewById(R.id.output);//текстовое поле вывода сообщений
        btnSC1=findViewById(R.id.sc1);//определение кнопки 1
        btnSC2=findViewById(R.id.sc2);//определение кнопки 2
        btn.setOnClickListener(v-> {
            if(hasSMSPermission()){
                todo_sms();
            }else {
                requestSMSPermission();

            }
        });
        btnSC1.setOnClickListener(v->{
            if(hasContactsPermission()){
                activityContacts1(); //метод перехода в activityContacts1
            }else {
                requestContactsPermission();

            }
        });
        btnSC2.setOnClickListener(v->{
            if(hasContactsPermission()){
                activityContacts2();     //метод перехода в activityContacts2
            }else {
                requestContactsPermission();

            }
        });

    }

    private boolean checkPermission(String receiveSms) {
        return false;
    }

    private void requestContactsPermission() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS}, CODE_PERMISSION_CONTACTS);
        }
    }

    private void activityContacts2() {
        Intent intent = new Intent(this, Contacts2Activity.class);
        startActivity(intent); //сам преход 2
    }
    private void activityContacts1() {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent); //сам переход 1
    }


    private boolean hasContactsPermission() {
        return
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)== PackageManager.PERMISSION_GRANTED;
    }
    void todo_sms(){ //метод для получения смс
        db = new BD(getBaseContext());
        wdb = db.getWritableDatabase();
        rdb = db.getReadableDatabase();
        ContentResolver resolver = getContentResolver();//  \/ 1ничего - все сообщения 2 inbox-полученные 3 sent-отправленные
        Cursor cur = resolver.query(Uri.parse("content://sms"), new String[]{"_id","date","address","body"},null,null,null);//пираметры запросса
        StringBuilder builder = new StringBuilder();
        if (cur!=null && cur.moveToFirst() ){
            do{
//                ContentValues contentValues = new ContentValues();

                builder.append("   0   ");
                builder.append(cur.getInt(0));
//                contentValues.put(BD.COLUMN_IND, cur.getInt(0));
                builder.append("   0   ");
                builder.append(cur.getString(1));
//                contentValues.put(BD.COLUMN_TIME, cur.getInt(1));
                builder.append("   0   ");
                builder.append(cur.getString(2));
//                contentValues.put(BD.COLUMN_NUMBER, cur.getString(2));//добавление в базу данных данных, не работае
                builder.append("   0   ");
                builder.append(cur.getString(3));
//                contentValues.put(BD.COLUMN_MESSAGE, cur.getInt(3));
//                wdb.insert(BD.TABLE_NAME, null, contentValues);
                System.out.println(cur.getCount());

                builder.append("\n");
                builder.append("\n");
                builder.append("\n");

            }while (cur.moveToNext());
        }
        outView.setText(builder.toString());

        if(cur!=null) cur.close();

    }
    boolean hasSMSPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)== PackageManager.PERMISSION_GRANTED;
    }
    final static int CODE_PERMISSION_SMS=42;
    final static int CODE_PERMISSION_CONTACTS=43;
    void requestSMSPermission(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, CODE_PERMISSION_SMS);

        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.N) метод для получения sms, не работает
//    public  void GetSMSList(View v){
//
//        TextView mText =findViewById(R.id.output);
//        mText.append("\n\n      SMS сообщения");
//        mText.append("\n-------------------------------------------------------------");
//        Uri uriSms = Uri.parse("content://sms/sent");
//        Context context=this;
//        Cursor cur = context.getContentResolver().query(uriSms, null,null,null,null);
//        startManagingCursor(cur);
//        int i=0;
//        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
//        if (cur.getCount() > 0){
//            while (cur.moveToNext()){
//                i++;
//                mText.append("\n"+i+ "      " + format1.format(cur.getLong(4)) + " 11111111111)))" + cur.getString(2) + "22222222222)" + cur.getString(12) );
//                mText.append("\n");
//            }
//        }
//        mText.append("\n########################################");
//
//    }
}

