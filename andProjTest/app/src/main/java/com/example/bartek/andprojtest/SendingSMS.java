package com.example.bartek.andprojtest;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class SendingSMS {

    private Context context;
    private ArrayList<Contact> listOfNumbers = new ArrayList<>();
    private ArrayList<Message> listOfMessages = new ArrayList<>();

    /**
     * simple consturctor
     * @param c - context
     */
    public SendingSMS(Context c) {
        context = c;
    }

    /**
     * constructor - not used anymore
     * @param numbers
     * @param messages
     */
    public SendingSMS(ArrayList<Contact> numbers, ArrayList<Message> messages) {
        listOfNumbers = numbers;
        listOfMessages = messages;
    }

    /*
    public void setListOfNumbers(ArrayList<Contact> numbers) {
        listOfNumbers = numbers;
    }

    public ArrayList<Contact> getListOfNumbers() {
        return listOfNumbers;
    }

    public void setListOfMessages(ArrayList<Message> messages) {
        listOfMessages = messages;
    }

    public ArrayList<Message> getListOfMessages() {
        return listOfMessages;
    }


    public void sendMessages() {
        int randomSize = listOfMessages.size();
        Random generator = new Random();
        for (Contact c: listOfNumbers) {
            int i = generator.nextInt(randomSize);
            sendSMS(c.getNumber(), listOfMessages.get(i).getText());
        }
    }*/

    /**
     * main function that select what messages and to who must be send - based on flag in Contact and Message classes
     * @param messages
     * @param numbers
     */
    public void sendMessages(ArrayList<Message> messages, ArrayList<Contact> numbers) {

        // first - clean lists
        listOfMessages.clear();
        listOfNumbers.clear();

        //add contacts that are set
        for (Contact c: numbers) {
            if(c.getFlag()) {
                listOfNumbers.add(c);
            }
        }

        //add messages that are set
        for (Message m: messages) {
            if(m.getState()) {
                listOfMessages.add(m);
            }
        }
        int randomSize = listOfMessages.size();
        if(randomSize == 0) {
            return;
        }
        Random generator = new Random();

        //select random message for all contacts
        for (Contact c: listOfNumbers) {
            int i = generator.nextInt(randomSize);
            sendSMS(c.getNumber(), listOfMessages.get(i).getText());
        }
    }

    /**
     * function that send message to contact
     * @param number (String) - number of contact
     * @param message (Strong) - message to be send
     */
    public void sendSMS(String number, String message) {
        Log.i("sendSMS", number + " " + message);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(number, null, message, null, null);/*
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", number);
        smsIntent.putExtra("sms_body", message);
        context.startActivity(smsIntent);*/
        restoreSms(number, message);
    }

    /**
     * function that save message in send items on the phone
     * @param number
     * @param message
     * @return
     */
    public boolean restoreSms(String number, String message) {
        /*try {*/
            ContentValues values = new ContentValues();
            values.put("address", number);
            values.put("body", message);
            context.getContentResolver().insert(Uri.parse("content://sms/sent"), values);
            //Uri.parse("content://sms/inbox", values);
            return true;/*
        } catch (Exception ex) {
            Log.i("sa","aaaa");
            return false;
        }*/
    }
}

