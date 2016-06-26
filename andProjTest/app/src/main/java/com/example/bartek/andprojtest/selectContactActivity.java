package com.example.bartek.andprojtest;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class selectContactActivity extends Activity implements AdapterView.OnItemClickListener {

    List<Contact> contacts = new ArrayList<>();
    MyAdapter adapter ;
    ImageView select;
    EditText search;
    private String prefix;


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("mesages", ((myApp) (this).getApplication()).messageSet);
        savedInstanceState.putSerializable("contacts", ((myApp)(this).getApplication()).numbersSet);
        Log.i("main2", "onSaveInstanceState");
    }

    /**
     * this function create new cursor, after using it, only messages that starts with typed in editText field string are shown
     * @return Cursor
     */
    private  Cursor searchResult() {
        ContentResolver cr = getContentResolver();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //select _ID, NAME and NUMBER
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};

        //condition - has phone number
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = '"
                // and starts with
                + ("1") + "' AND (" +ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE ? OR " + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE ?)";

        //this
        String[] selectionArgs = new String[]{prefix + "%", " " + prefix + "%"};

        //sorting
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";
        // + " AND " + ContactsContract.Contacts.DISPLAY_NAME + " LIKE " + prefix + "%"

        //return result of the query
        return cr.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    /**
     *
     * function that is called at the creation of the activity
     * @param savedInstanceState (Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            ((myApp)(this).getApplication()).messageSet = (ArrayList<Message>) savedInstanceState.getSerializable("messages");
            ((myApp)(this).getApplication()).numbersSet = (ArrayList<Contact>) savedInstanceState.getSerializable("contacts");
        }

        //getting all contacts
        getAllContacts(this.getContentResolver(),1);
        ListView lv= (ListView) findViewById(R.id.lv);

        //adapter
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.setItemsCanFocus(false);
        lv.setTextFilterEnabled(true);

        select = (ImageView) findViewById(R.id.button1);
        select.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                //on click - we choose selected contacts and add them to contacts list
                StringBuilder checkedcontacts = new StringBuilder();

                for(int i = 0; i < contacts.size(); i++)

                {
                    if(adapter.mCheckStates.get(i))
                    {
                        checkedcontacts.append(contacts.get(i).getName());
                        checkedcontacts.append("\n");
                        boolean add = true;
                        for (Contact con: ((myApp) getApplication()).numbersSet) {
                            if (contacts.get(i).getId().equals(con.getId())) {
                                add = false;
                            }
                        }
                        if (add) {
                            ((myApp) getApplication()).numbersSet.add(contacts.get(i));
                        }
                        adapter.setChecked(i, false);
                    }
                }

                getAllContacts(selectContactActivity.this.getContentResolver(),1);
                adapter.notifyDataSetChanged();
            }
        });

        // edit text that make filtering
        search = (EditText) findViewById(R.id.searchText);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                prefix = cs.toString();
                getAllContacts(selectContactActivity.this.getContentResolver(),2);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        adapter.toggle(arg2);
    }

    /**
     * this function looks for all of the contacts that have phone number saved and returns them
     * @return Cursor
     */
    private Cursor getCursor() {
        ContentResolver cr = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        //select only ID, NUMBER, NAME
        String[] projection = new String[] {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER };
        //conition - must have phone number
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER + " = '"
                + ("1") + "'";
        String[] selectionArgs = null;
        //sorting
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";

        return cr.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    /**
     * function that cleans up and set up again contact list
     *
     * @param cr
     * @param q - if 1, then all contacts, else - only those that starts with
     */
    public  void getAllContacts(ContentResolver cr, int q) {
        Cursor phones;
        contacts.removeAll(contacts);
        if(q == 1)
            phones = getCursor();
        else
            phones = searchResult();
        //adding conacts to list
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
            boolean add = true;
            for (Contact con: ((myApp) getApplication()).numbersSet) {
                if (id.equals(con.getId())) {
                    add = false;
                }
            }
            if (add) {
                contacts.add(new Contact(id, phoneNumber,name));
            }
        }

        phones.close();
    }

    /**
     * this class is simply adapter that exends BaseAdapter
     */
    class MyAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener
    {  private SparseBooleanArray mCheckStates;
        LayoutInflater mInflater;
        TextView tv;
        CheckBox cb;

        /**
         * constructor
         */
        MyAdapter()
        {
            mCheckStates = new SparseBooleanArray(contacts.size());
            mInflater = (LayoutInflater)selectContactActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        /**
         * simple function that return number of elements in list
         * @return size (int)
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return contacts.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub

            return 0;
        }

        /**
         * this function set text and set box - not checked
         * @param position - position in list
         * @param convertView
         * @param parent
         * @return view
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi=convertView;
            if(convertView==null)
                vi = mInflater.inflate(R.layout.row, null);
            tv= (TextView) vi.findViewById(R.id.textView1);
            cb = (CheckBox) vi.findViewById(R.id.checkBox1);
            tv.setText(contacts.get(position).getName());
            cb.setTag(position);
            cb.setChecked(mCheckStates.get(position, false));
            cb.setOnCheckedChangeListener(this);

            return vi;
        }

        /**
         * simple function that check if contact is checked
         * @param position - position`
         * @return - true (checked), false (unchecked)
         */
        public boolean isChecked(int position) {
            return mCheckStates.get(position, false);
        }

        /**
         * simple function taht check unchecked contact
         * @param position
         * @param isChecked
         */
        public void setChecked(int position, boolean isChecked) {
            mCheckStates.put(position, isChecked);
        }

        /**
         * simple function taht check unchecked contact and vice versa
         * @param position
         */
        public void toggle(int position) {
            setChecked(position, !isChecked(position));
        }

        /**
         * function that is called after check/uncheck the box
         * @param buttonView
         * @param isChecked
         */
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            mCheckStates.put((Integer) buttonView.getTag(), isChecked);
        }
    }
}