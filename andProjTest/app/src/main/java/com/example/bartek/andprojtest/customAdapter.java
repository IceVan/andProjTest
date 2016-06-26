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
public class customAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final int resourceID;
    private final ArrayList<String> arr;
    private final ArrayList<Contact> contacts2;

    //konstruktor adaptera
    public customAdapter(Context context, int resource, ArrayList<String> bah, ArrayList<Contact> cont) {
        super(context, resource, bah);
        arr = bah;
        this.context = context;
        this.resourceID = resource;
        contacts2 = cont;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resourceID, parent, false);
        final CheckBox checkTxt = (CheckBox) rowView.findViewById(R.id.checkBox);
        //pozycja w tablicy do ktorej sie odwolujemy
        //nazwa i kontakt na danej pozycji sa rownowazne
        final int pos = position;
        //ustawianie tekstu w checkboxie
        checkTxt.setText(contacts2.get(pos).getName() +"\n" + contacts2.get(pos).getNumber());
        //zaznaczanie boxa jesli w kontaktach jest ustawiona flaga
        if(contacts2.get(pos).getFlag()) {
            checkTxt.setChecked(true);
        }
        //listener zmienia flage kontaktu przy zmianie zaznaczenia
        checkTxt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                contacts2.get(pos).setFlag(b);
                //checkTxt.setText(contacts2.get(pos).getName() +"\n" + contacts2.get(pos).getNumber() + " " + contacts2.get(pos).getFlag());
                checkTxt.setText(contacts2.get(pos).getName() +"\n" + contacts2.get(pos).getNumber());

            }
        });
        return rowView;
    }
}