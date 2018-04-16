package com.iak.okiprasetyo.androidroomiak.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

/**
 * Created by Oki Prasetyo on 11/26/2017.
 */
@Dao
public interface ContactDao {
    //get data record from table contact
    @Query("SELECT COUNT(*) FROM " + ConfigureTableContact.TableContact)
    int count();

    //anotation insert data
    @Insert
    long insert(ConfigureTableContact configureTableContact);

    //insert data array
    @Insert
    long[] insertAll(ConfigureTableContact[] configureTableContacts);

    //query get data per column
    @Query("SELECT*FROM " + ConfigureTableContact.TableContact + " WHERE " + ConfigureTableContact.ColumnID + " = :id")
    Cursor selectById(long id);

    //query all
    @Query("SELECT*FROM " + ConfigureTableContact.TableContact)
    Cursor selectAll();

    //delete by id
    @Query("DELETE FROM " + ConfigureTableContact.TableContact + " WHERE " + ConfigureTableContact.ColumnID + " = :id")
    int deleteById(long id);

    //update
    @Update
    int update(ConfigureTableContact configureTableContact);

}
