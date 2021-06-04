package com.example.res_end;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationAction;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Contacts2Activity extends AppCompatActivity {
    BD db;

    SQLiteDatabase wdb, rdb;





    RecyclerView recyclerView;
    static class Contact{
        long id;
        String name;
        long image_id;
        String numbder;
        public Contact(long id, String name, long image_id, String numbder) {
            this.id = id;
            this.name = name.trim();
            this.image_id = image_id;
            this.numbder = numbder;

        }
        public String getNumbder() {
            return numbder;
        }

        public void setNumbder(String numbder) {
            this.numbder = numbder;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name.trim();
        }

        public long getImage_id() {
            return image_id;
        }

        public void setImage_id(long image_id) {
            this.image_id = image_id;
        }
    }
class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder>{
        List<Contacts2Activity.Contact> contacts = new ArrayList<>();

        public void setContacts(List<Contact> contacts) {
            this.contacts = contacts;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view,parent,false));
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(contacts.get(position));


        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameView, phoneView;
            ImageView imageView, ret;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                nameView = itemView.findViewById(R.id.name);
                imageView = itemView.findViewById(R.id.contactImage);
                ret = itemView.findViewById(R.id.imageView);
                phoneView = itemView.findViewById(R.id.phone);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int positionIndex = getAdapterPosition();
                        Toast toast = Toast.makeText(getApplicationContext(),
                               ""+ contacts.get(positionIndex), Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(Contacts2Activity.this, ConversationActivity2.class);
                        startActivity(intent);
                    }
                });



            }




            public void bind(Contact contact) {
                nameView.setText(contact.getName());
                phoneView.setText(contact.getNumbder());
                try {
                    Uri baseUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contact.getId());
                    Uri imageUri = Uri.withAppendedPath(baseUri,ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                    imageView.setImageURI(imageUri);
                    ret.setImageResource(R.drawable.user);

                }catch (Exception e){
                    e.printStackTrace();

                }

            }

        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerView= findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RvAdapter adapter = new RvAdapter();
        recyclerView.setAdapter(adapter);
        ContentResolver resolver = getContentResolver();
        Cursor cur = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, ContactsContract.Contacts.DISPLAY_NAME);
        ArrayList<Contact> contacts = new ArrayList<>();
        if (cur!=null && cur.moveToFirst()){
            do {
                long id = cur.getLong(0);
                String name = cur.getString(1);
                long image_id = cur.getLong(2);
                String phone = cur.getString(3);
                Contact contact = new Contact(id,name.trim(),image_id,phone);
                contacts.add(contact);
            }while (cur.moveToNext());
        }

        if (cur!=null) cur.close();
        adapter.setContacts(contacts);
    }

}
