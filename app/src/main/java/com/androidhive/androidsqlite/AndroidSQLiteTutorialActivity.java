package com.androidhive.androidsqlite;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class AndroidSQLiteTutorialActivity extends Activity {
    private static final String TAG = "AndroidSQLiteTutorialAc";
    private DatabaseHandler db;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new DatabaseHandler(this, 1);

        List<Contact> contacts = db.getAllContacts();
        if (contacts.isEmpty()) {
            Log.d("Insert: ", "Inserting ..");
            db.addContact(new Contact("Ravi", "9100000000"));
            db.addContact(new Contact("Srinivas", "9199999999"));
            db.addContact(new Contact("Tommy", "9522222222"));
            db.addContact(new Contact("Karthik", "9533333333"));
        }


        // Reading all contacts
        for (Contact cn : db.getAllContacts()) {
            Log.i(TAG, "onCreate: "+ "id: "+ cn.getId()+ " name: "+ cn.getName()+ " phone: "+ cn.getPhoneNumber());
        }

        findViewById(R.id.btUpgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // upgrade db to version 2
                if (db.getDatabaseVersion() == 1) {
                    Log.i(TAG, "onClick: column count of version " + db.getDatabaseVersion() + ": " + db.getColumnCount());
                    db = new DatabaseHandler(AndroidSQLiteTutorialActivity.this, 2);
                    Log.i(TAG, "onClick: column count of version " + db.getDatabaseVersion() + ": " + db.getColumnCount());

                    // add address for address column
                    for (Contact cn : db.getAllContacts()) {
                        if (cn.getAddress() == null) {
                            cn.setAddress("vn");
                            db.updateContact(cn);
                        }

                    }
                    // Reading all contacts
                    for (Contact cn : db.getAllContacts()) {
                        Log.i(TAG, "onCreate: "+ "id: "+ cn.getId()+ " name: "+ cn.getName()+
                                " phone: "+ cn.getPhoneNumber()+" address: "+ cn.getAddress());
                    }
                }
            }
        });
    }
}