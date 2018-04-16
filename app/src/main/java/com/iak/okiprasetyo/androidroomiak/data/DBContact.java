package com.iak.okiprasetyo.androidroomiak.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

/**
 * Created by Oki Prasetyo on 11/26/2017.
 */
@Database(entities = {ConfigureTableContact.class}, version = 1)
public abstract class DBContact extends RoomDatabase {
    public abstract ContactDao contactDao();

    private static DBContact dbContact;

    public static synchronized DBContact getDbContact(Context context) {
        if (dbContact == null) {
            dbContact = Room.databaseBuilder(context.getApplicationContext(), DBContact.class, "contact").build();
            dbContact.populateInitialData();
        }
        return dbContact;
    }
    @VisibleForTesting
    public static void switchToMemory(Context context){
        dbContact = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), DBContact.class).build();
    }
    private void populateInitialData() {
        if (contactDao().count() == 0) {
            ConfigureTableContact configureTableContact = new ConfigureTableContact();
            beginTransaction();
            try {
                for (int i = 0; i < ConfigureTableContact.ContactArray.length; i++) {
                    configureTableContact.Nama = ConfigureTableContact.ContactArray[i];
                    contactDao().insert(configureTableContact);
                }
                setTransactionSuccessful();
            } finally {
                endTransaction();
            }
        }
    }
}
