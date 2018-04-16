package com.iak.okiprasetyo.androidroomiak.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;

import static com.iak.okiprasetyo.androidroomiak.data.ConfigureTableContact.ColumnID;
import static com.iak.okiprasetyo.androidroomiak.data.ConfigureTableContact.ColumnName;

/**
 * Created by Oki Prasetyo on 11/26/2017.
 */
@Entity(tableName = ConfigureTableContact.TableContact)
public class ConfigureTableContact {
    //create table database
    public static final String TableContact = "Contact";
    //create column id
    public static final String ColumnID = BaseColumns._ID;
    public static final String ColumnName = "Nama";
    //Config Table
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = ColumnID)
    public long id;
    @ColumnInfo(name = ColumnName)
    public String Nama;

    public static ConfigureTableContact fromContentValues(ContentValues contentValues){
       final ConfigureTableContact configureTableContact = new ConfigureTableContact();
        if (contentValues.containsKey(ColumnID)){
            configureTableContact.id = contentValues.getAsLong(ColumnID);
        }
        if (contentValues.containsKey(ColumnName)){
            configureTableContact.Nama = contentValues.getAsString(ColumnName);
        }
        return configureTableContact;
    }
    static final String[] ContactArray = {
         "Android",
            "IOS",
            "Tizen",
            "Windows Phone"
    };
}
