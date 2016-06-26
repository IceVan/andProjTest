package com.example.bartek.andprojtest;

import java.io.Serializable;


public class Message implements Serializable {

    private double rating = 0.0;
    private String text = "";
    private boolean state = false;

    /**
     * empty constructor, not used
     */
    public Message() {
    }

    /**
     * constructor, not used
     * @param newText (String)
     */
    public Message(String newText) {
        text = newText;
    }

    /**
     *
     * @param newText (Sring) - message text
     * @param newRating (double) - rating of the message (in case of connecting to server)
     */
    public Message(String newText, double newRating) {
        text = newText;
        rating = newRating;
    }

    /**
     * simply function that set state flag (true - message is selected, false - not selected)
     * @param newState (boolean)
     */
    public void setState(boolean newState) {
        state = newState;
    }

    /**
     * imply function that get flag (true - message is selected, false - not selected)
     * @return state (boolean)
     */
    public boolean getState() {
        return state;
    }

    /*public void setRating(double newRating) {
        rating = newRating;
    }

    public double getRating() {
        return rating;
    }*/

    /*
     * simple function to edit message text
     * @param newText
     */
    public void setText(String newText) {
        text = newText;
    }

    /**
     * simple function to get message text
     * @return text (String)
     */
    public String getText() {
        return text;
    }
}