package com.example.res_end;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ConversationActivity2 extends AppCompatActivity {
    public static String tel, name; //класс для беседы
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation2);
        textView=findViewById(R.id.qwerty);
        textView.setText(tel);



    }
}