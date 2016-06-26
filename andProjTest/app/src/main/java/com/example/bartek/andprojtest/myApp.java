package com.example.bartek.andprojtest;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Bartek on 2015-05-24.
 */
public class myApp extends Application {
    /*
    public Set<Message> messageSet = new HashSet<>();
    public Set<Contact> numbersSet = new HashSet<>();
    */
    /*
        kolekcje wiadomosci i kontaktow dzielone na cala aplikacje
     */
    public ArrayList<Message> messageSet = new ArrayList<>();
    public ArrayList<Contact> numbersSet = new ArrayList<>();
    public introView banner = null;
}
