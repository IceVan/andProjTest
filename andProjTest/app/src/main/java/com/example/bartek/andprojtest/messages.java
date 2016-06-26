package com.example.bartek.andprojtest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class messages extends ActionBarActivity {

    private ListView lV;
    private ImageView addBttn;
    private ImageView removeAllBttn;
    private ImageView sendBttn;
    private EditText txtEdit;
    messages main = this;
    static private customAdapterMsg adapter;
    static private ArrayList<String> myStringArray1;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("mesages", ((myApp) (this).getApplication()).messageSet);
        savedInstanceState.putSerializable("contacts", ((myApp)(this).getApplication()).numbersSet);
        Log.i("main2", "onSaveInstanceState");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            ((myApp)(this).getApplication()).messageSet = (ArrayList<Message>) savedInstanceState.getSerializable("messages");
            ((myApp)(this).getApplication()).numbersSet = (ArrayList<Contact>) savedInstanceState.getSerializable("contacts");
        }
        setContentView(R.layout.activity_messages);
        myStringArray1 = new ArrayList<String>();
        for(Message m : ((myApp) getApplication()).messageSet)
        {
            myStringArray1.add(m.getText());
            //m.setState(false);
        }
        lV = (ListView) findViewById(R.id.listViewM);
        addBttn = (ImageView) findViewById(R.id.imageButtonAddM);
        removeAllBttn = (ImageView) findViewById(R.id.imageButtonRemoveM);
        sendBttn = (ImageView) findViewById(R.id.imageButtonSendM);
        txtEdit = (EditText) findViewById(R.id.editTextM);


        adapter = new customAdapterMsg(this, R.layout.checkboxrow, myStringArray1, ((myApp) getApplication()).messageSet);
        lV.setAdapter(adapter);
        //TODO uzupelnianie listy

        addBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = null;
                String tempEdit = txtEdit.getText().toString();
                txtEdit.setText("");
                final TextView tV = new TextView(main);
                final Message msg = new Message(tempEdit,0.0);
                ((myApp) getApplication()).messageSet.add(msg);
                myStringArray1.add(tempEdit);

                //closing textEdit
                InputMethodManager inputManager =
                        (InputMethodManager) main.
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(
                        main.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                //redraw listView
                adapter = new customAdapterMsg(main, R.layout.checkboxrow, myStringArray1, ((myApp) getApplication()).messageSet);
                lV.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        //TODO napisanie usuwania pojedynczych elementow - sprawdzanie, co bylo zaznaczone i usuwanie tego
        //TODO a nie usuwanie wszystkiego
        //TODO + checkbox do zaznaczania wszystkich elementow, jesli jednak ktosby chcial usunac wszystko

        removeAllBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = null;
                txtEdit.setText("");
                //((myApp) getApplication()).messageSet.removeAll(((myApp) getApplication()).messageSet);
                ArrayList<Message> messageSet2 = new ArrayList<>(((myApp) getApplication()).messageSet);
                for(Message m : messageSet2)
                {
                    if(m.getState())
                    {
                        ((myApp) getApplication()).messageSet.remove(m);
                        myStringArray1.remove(m.getText());
                    }
                }

                //myStringArray1.removeAll(myStringArray1);
                adapter = new customAdapterMsg(main, R.layout.checkboxrow, myStringArray1, ((myApp) getApplication()).messageSet);
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

                        (new SendingSMS(messages.this)).sendMessages(((myApp) getApplication()).messageSet, ((myApp) getApplication()).numbersSet);
                        Toast.makeText(getApplicationContext(), "Messages sent", Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}
