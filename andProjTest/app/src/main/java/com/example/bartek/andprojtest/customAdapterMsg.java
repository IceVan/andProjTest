package com.example.bartek.andprojtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * Created by Bartek on 2015-05-26.
 */
public class customAdapterMsg extends ArrayAdapter<String> {
    private final Context context;
    private final int resourceID;
    private final ArrayList<String> arr;
    private final ArrayList<Message> message;

    //konstruktor adaptera
    public customAdapterMsg(Context context, int resource, ArrayList<String> bah, ArrayList<Message> msg) {
        super(context, resource, bah);
        arr = bah;
        this.context = context;
        this.resourceID = resource;
        message = msg;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resourceID, parent, false);
        //pozycja w tablicy do ktorej sie odwolujemy
        //nazwa i wiadomosc na danej pozycji sa rownowazne
        final int pos = position;
        final CheckBox checkTxt = (CheckBox) rowView.findViewById(R.id.checkBox);
        //ustawianie tekstu w checkboxie
        checkTxt.setText(arr.get(position));
        //zaznaczanie boxa jesli w wiadomosciach jest ustawiona flaga
        if(message.get(pos).getState()) {
            checkTxt.setChecked(true);
        }
        //listener zmienia flage wiadomosci przy zmianie zaznaczenia
        checkTxt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                message.get(pos).setState(b);
                //checkTxt.setText(message.get(pos).getText() + message.get(pos).getState());
                checkTxt.setText(message.get(pos).getText());
            }
        });
        return rowView;
    }
}