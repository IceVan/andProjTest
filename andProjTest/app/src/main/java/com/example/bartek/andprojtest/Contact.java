package com.example.bartek.andprojtest;


import java.io.Serializable;

public class Contact implements Serializable {

    private String id, name, number;
    private boolean choosed;

    /**
     * Empty constructor
     * not used
     */
    public Contact() {

    }

    /*
    * Constructor without ID
    * not used anymore
     */
    public Contact(String setNumber, String setName) {
        name = setName;
        number = setNumber;
        choosed = false;
    }

    /**
     * Main constructor of the class
     * @param: setId (String) - Id of the contact in the Phone
     *      setNumber (String) - phone number
     *      setName (String) - contact name
     */
    public Contact(String setId, String setNumber, String setName) {
        id = setId;
        name = setName;
        number = setNumber;
        choosed = false;
    }


    /**
     * simple function to set number of the phone
    */
    public void setNumber(String newNumber) {
        number = newNumber;
    }

    /**
     * simple function to get phone number
     * @return number (String) - phone number
     */
    public String getNumber() {
        return number;
    }

    /**
     * simple function to set concact name
     * not used
     * @param newName (String) contact name
     *
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * simple function to get contact name
     * @return name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * simple function to get flag (true - contact selected, false - contact not selected)
     * @return choosed (boolean)
     */
    public boolean getFlag() {return choosed; }

    /**
     * simple function to set slag (true - selected, false - not selected)
     * @param f (boolean)
     */
    public void setFlag(boolean f) { choosed = f; }

    /**
     * simple function to set ID
     * @param newId (String)
     */
    public void setId(String newId) { id = newId; }

    /**
     * simple function to get ID
     * @return id (String)
     */
    public String getId() { return id; }
}
