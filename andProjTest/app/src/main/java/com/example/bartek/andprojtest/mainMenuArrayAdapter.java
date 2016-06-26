package com.example.bartek.andprojtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Bartek on 2015-05-24.
 */
public class mainMenuArrayAdapter extends ArrayAdapter<String>{
    private final Context context;
    private final String[] values;


    public mainMenuArrayAdapter(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;

    }
    //uchwyt odswierzajacy animacje
    Handler myGUIUpdateHandler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(android.os.Message msg)
        {
            switch(msg.what)
            {
                case MainActivity.GUIUPDATEIDENTIFIER:
                    ((myApp)((Activity) context).getApplication()).banner.invalidate();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //watek
    class RefreshRunner implements Runnable
    {
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted())
            {
                android.os.Message message = new android.os.Message();
                message.what = MainActivity.GUIUPDATEIDENTIFIER;
                myGUIUpdateHandler.sendMessage(message);
                try
                {
                    Thread.sleep(100);
                }
                catch(InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //wiersze listy zostana uzupelnione za pomoca R.layout.rowlayout
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        //ustawianie tekstu na otrzymana wartosc
        textView.setText(values[position]);
        String s = values[position];

        //tworzenie listenerow na podstawie otrzymanych wartosci string
        if (s.startsWith("config"))
        {
            imageView.setImageResource(R.drawable.config);
        }else if (s.startsWith("contacts"))  {
            imageView.setImageResource(R.drawable.cont);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, contacts.class);
                    context.startActivity(intent);
                }
            });
        }else if (s.startsWith("messages")) {
            imageView.setImageResource(R.drawable.add);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, messages.class);
                    context.startActivity(intent);
                }
            });
        }else if (s.startsWith("send")) {
            imageView.setImageResource(R.drawable.send);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmSendingSMS();
                }
            });

        }//dodanie animowanego bannera
        else if (s.startsWith("intro")) {
            View rowView2 = inflater.inflate(R.layout.my_view_layout, parent, false);

            ((myApp)((Activity) context).getApplication()).banner = (introView) rowView2.findViewById(R.id.introView3); //brzydkie kaczatko xD
            //dziala, nie mam pojecia dlaczego ale dziala wiec nie ruszac kurwa
            //watek odswierzajacy animacje
            new Thread(new RefreshRunner()).start();
            return rowView2;
        }

        return rowView;
    }

    //potwierdzenie wyslanina smsa
    private void confirmSendingSMS() {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.send)
                .setMessage(R.string.realy_send)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        (new SendingSMS(context)).sendMessages(((myApp)((Activity) context).getApplication()).messageSet, ((myApp)((Activity) context).getApplication()).numbersSet);
                        Toast.makeText(context.getApplicationContext(), "Messages sent", Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();
    }
}
