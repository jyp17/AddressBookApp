package com.example.addressbook;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;

public class AddressService extends Service {
    private final IBinder mBinder = new AddressBinder();

    public class AddressBinder extends Binder {
        AddressService getService() {
            return AddressService.this;
        }
    }

    public AddressService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void addToStorage(String newTask, ArrayList<String> storage) {
        storage.add(newTask);
    };
}