package com.example.addressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import com.example.addressbook.AddressService.AddressBinder;

public class MainActivity extends AppCompatActivity {
    EditText firstNameInput;
    ListView contactList;
    Button addName;
    ArrayList<String> storage = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    AddressService addressService;
    boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        addName = (Button) findViewById(R.id.add);
        contactList = (ListView) findViewById(R.id.contactList);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, storage);
        contactList.setAdapter(arrayAdapter);

        addName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });
    }

    public void onButtonClick(View v) {
        if (isBound) {
            addressService.addToStorage(firstNameInput.getText().toString(), storage);
            arrayAdapter.notifyDataSetChanged();
            firstNameInput.setText("");
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AddressBinder binder = (AddressBinder) service;
            addressService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, AddressService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isBound) {
            unbindService(mConnection);
            isBound = false;
        }
    }
}