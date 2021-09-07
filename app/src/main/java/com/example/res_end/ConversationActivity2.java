package com.example.res_end;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConversationActivity2 extends AppCompatActivity {
    public static String tel, name; //класс для беседы
    TextView textView, textView2, textView3;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation2);
        textView=findViewById(R.id.qwerty);
        textView2 = findViewById(R.id.rr);
//        textView3 = findViewById(R.id.qq);
        listView = findViewById(R.id.listView);


        textView.setText(name);
        textView2.setText(tel);
        todo_sms_cont();
    }
    void todo_sms_cont(){ //метод для получения смс определённого контакта
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
}