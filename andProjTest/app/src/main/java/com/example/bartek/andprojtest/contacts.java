package com.example.bartek.andprojtest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class contacts extends ActionBarActivity {

    private ListView lV;
    private ImageView addBttn;
    private ImageView removeAllBttn;
    private ImageView sendBttn;
    contacts main = this;
    static private customAdapter adapter;
    static private ArrayList<String> myStringArray1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            ((myApp)(this).getApplication()).messageSet = (ArrayList<Message>) savedInstanceState.getSerializable("messages");
            ((myApp)(this).getApplication()).numbersSet = (ArrayList<Contact>) savedInstanceState.getSerializable("contacts");
        }
        setContentView(R.layout.activity_contacts);
        myStringArray1 = new ArrayList<String>();
        for(Contact c : ((myApp) getApplication()).numbersSet)
        {
            myStringArray1.add(c.getName());
            //c.setFlag(false);
        }
        lV = (ListView) findViewById(R.id.listViewC);
        addBttn = (ImageView) findViewById(R.id.imageButtonAddC);
        removeAllBttn = (ImageView) findViewById(R.id.imageButtonRemoveC);
        sendBttn = (ImageView) findViewById(R.id.imageButtonSendC);

        adapter = new customAdapter(this, R.layout.checkboxrow, myStringArray1, ((myApp) getApplication()).numbersSet);
        lV.setAdapter(adapter);
        //TODO uzupelnianie listy


        addBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_activity();
            }
        });

        removeAllBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = null;

                //((myApp) getApplication()).numbersSet.removeAll(((myApp) getApplication()).numbersSet);
                ArrayList<Contact> numberSet2 = new ArrayList<>(((myApp) getApplication()).numbersSet);
                for(Contact m : numberSet2)
                {
                    if(m.getFlag())
                    {
                        ((myApp) getApplication()).numbersSet.remove(m);
                        myStringArray1.remove(m.getName());
                    }
                }
                adapter = new customAdapter(main, R.layout.checkboxrow, myStringArray1,((myApp) getApplication()).numbersSet);
                lV.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        sendBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmSendingSMS();
            }
        });
    }

    private void confirmSendingSMS() {
         new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle(R.string.send)
        .setMessage(R.string.realy_send)
        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                (new SendingSMS(contacts.this)).sendMessages(((myApp) getApplication()).messageSet, ((myApp) getApplication()).numbersSet);
                Toast.makeText(getApplicationContext(), "Messages sent", Toast.LENGTH_SHORT).show();
            }

        })
                .setNegativeButton(R.string.no, null)
        .show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("mesages", ((myApp) (this).getApplication()).messageSet);
        savedInstanceState.putSerializable("contacts", ((myApp)(this).getApplication()).numbersSet);
        Log.i("main2", "onSaveInstanceState");
    }

    @Override
    public void onResume() {
        super.onResume();
        myStringArray1.removeAll(myStringArray1);

        for(Contact c : ((myApp) getApplication()).numbersSet)
        {
            myStringArray1.add(c.getName());
            //c.setFlag(false);
        }
        lV = (ListView) findViewById(R.id.listViewC);
        //adapter = new customAdapter(main, R.layout.checkboxrow, myStringArray1);
        //lV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void start_activity() {
        Intent intent = new Intent(this, selectContactActivity.class);
        startActivity(intent);
    }
}
