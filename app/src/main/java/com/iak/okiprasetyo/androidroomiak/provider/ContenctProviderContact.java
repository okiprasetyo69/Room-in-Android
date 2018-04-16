package com.iak.okiprasetyo.androidroomiak.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.iak.okiprasetyo.androidroomiak.data.ConfigureTableContact;
import com.iak.okiprasetyo.androidroomiak.data.ContactDao;
import com.iak.okiprasetyo.androidroomiak.data.DBContact;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Oki Prasetyo on 11/26/2017.
 */

public class ContenctProviderContact extends ContentProvider {

    public static final String NamaPackage = "com.iak.okiprasetyo.androidroomiak.provider";
    public static final Uri URLContentProvider = Uri.parse(
            "content://" + NamaPackage + "/" + ConfigureTableContact.TableContact
    );
    private static final int CodeContactDir = 1;
    private static final int CodeContactItem = 2;
    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(NamaPackage, ConfigureTableContact.TableContact, CodeContactDir);
        MATCHER.addURI(NamaPackage, ConfigureTableContact.TableContact + "/#", CodeContactItem);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] stringarray, @Nullable String SingleString, @Nullable String[] stringsarray2, @Nullable String SingleString2) {
        final int code = MATCHER.match(uri);
        if (code == CodeContactDir || code == CodeContactItem) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            ContactDao contactDao = DBContact.getDbContact(context).contactDao();
            final Cursor cursor;
            if (code == CodeContactDir) {
                cursor = contactDao.selectAll();
            } else {
                cursor = contactDao.selectById(ContentUris.parseId(uri));
            }
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)) {
            case CodeContactDir:
                return "vnd.android.cursor.dir/" + NamaPackage + "." + ConfigureTableContact.TableContact;
            case CodeContactItem:
                return "vnd.android.cursor.item/" + NamaPackage + "." + ConfigureTableContact.TableContact;
            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        switch (MATCHER.match(uri)) {
            case CodeContactDir:
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                final long id = DBContact.getDbContact(context).contactDao().insert(ConfigureTableContact.
                    fromContentValues(contentValues));
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case CodeContactItem:
                throw new IllegalArgumentException("Invalid URI, can't insert with ID: " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)) {
            case CodeContactDir:
                throw new IllegalArgumentException("Invalid URI, can't insert with ID: " + uri);
            case CodeContactItem:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final int count = DBContact.getDbContact(context).contactDao().deleteById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;

            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        switch (MATCHER.match(uri)) {
            case CodeContactDir:
                throw new IllegalArgumentException("Invalid URI, can't insert with ID: " + uri);
            case CodeContactItem:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                final ConfigureTableContact configureTableContact = ConfigureTableContact.fromContentValues(contentValues);
                final int count = DBContact.getDbContact(context).contactDao().update(configureTableContact);
                context.getContentResolver().notifyChange(uri, null);
                return count;
            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        switch (MATCHER.match(uri)){
            case CodeContactDir:
                final Context context = getContext();
                if (context == null){
                    return 0;
                }
                final DBContact dbContact = DBContact.getDbContact(context);
                final ConfigureTableContact[] configureTableContact = new ConfigureTableContact[values.length];
                for (int i = 0; i < values.length; i++){
                    configureTableContact[i] = ConfigureTableContact.fromContentValues(values[i]);
                }
                return dbContact.contactDao().insertAll(configureTableContact).length;
            case CodeContactItem:
                throw new IllegalArgumentException("Invalid URI : " + uri);
            default:
                throw new IllegalArgumentException("Unknown URI : " + uri);
        }
    }

    @NonNull
    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations) throws OperationApplicationException {
       final Context context = getContext();
        if (context == null){
            return new ContentProviderResult[0];
        }
        final DBContact dbContact = DBContact.getDbContact(context);
        dbContact.beginTransaction();
        try {
            final ContentProviderResult[]results = super.applyBatch(operations);
            dbContact.setTransactionSuccessful();
            return results;
        } finally {
            dbContact.endTransaction();
        }
    }
}
