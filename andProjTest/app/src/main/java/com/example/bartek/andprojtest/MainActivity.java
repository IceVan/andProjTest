package com.example.bartek.andprojtest;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;


public class MainActivity extends ListActivity {

    protected static final int GUIUPDATEIDENTIFIER = 0x777;
    Thread myRefreshThread = null;


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
        String[] values = new String[] {
                "intro",
                "messages",
                "contacts",
                "send",
                "config"};
        mainMenuArrayAdapter adapter = new mainMenuArrayAdapter(this, values);
        setListAdapter(adapter);
        if(((myApp)((Activity) this).getApplication()).banner != null) Log.i("Adapter", "banner out != null");
        else Log.i("Adapter", "banner out == null ");
        //new Thread(new RefreshRunner()).start();
    }
}
