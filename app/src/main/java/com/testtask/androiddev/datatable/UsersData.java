package com.testtask.androiddev.datatable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.testtask.androiddev.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersData extends SQLiteOpenHelper implements AbstractModelDAO<User>
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABSASE_NAME = "UserData";

    public static final String TABLE_NAME = "Users";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_NOTE = "note";
    public static final String KEY_PHOTO = "photo";

    private static final String CREATE = "create table " + TABLE_NAME + "(" + KEY_ID +
            " integer primary key," + KEY_NAME + " text," + KEY_PHONE + " text," + KEY_MAIL
            + " text," + KEY_NOTE + " text, " + KEY_PHOTO + " text)";
    private static final String UPGRADE = "drop table if exists " + TABLE_NAME;

    private final ContentValues _content;

    private static UsersData _instance;

    private SQLiteDatabase _database;

    private UsersData(Context context)
    {
        super(context, DATABSASE_NAME, null, DATABASE_VERSION);

        _content = new ContentValues();
    }

    @Override
    public List<User> getAll()
    {
        _database = getWritableDatabase();

        final Cursor cursor = _database.query(TABLE_NAME, null,null, null, null, null, null);
        final List<User> users = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            User user;

            do
            {
                user = new User(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                user.setPhone(cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
                user.setMail(cursor.getString(cursor.getColumnIndex(KEY_MAIL)));
                user.setNote(cursor.getString(cursor.getColumnIndex(KEY_NOTE)));
                user.setPhoto(cursor.getBlob(cursor.getColumnIndex(KEY_PHOTO)));

                users.add(user);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        close();

        return users;
    }

    @Override
    public boolean insert(User user)
    {
        _database = getWritableDatabase();

        _content.put(KEY_ID, user.getId());
        _content.put(KEY_NAME, user.getName());
        _content.put(KEY_PHONE, user.getPhone());
        _content.put(KEY_MAIL, user.getMail());
        _content.put(KEY_NOTE, user.getNote());
        _content.put(KEY_PHOTO, user.getPhoto());

        final boolean res = _database.insert(TABLE_NAME, null, _content) > 0;

        close();

        return res;
    }

    @Override
    public boolean update(User user)
    {
        return false;
    }

    @Override
    public boolean delete(User user)
    {
        _database = getWritableDatabase();

        return _database.delete(TABLE_NAME, user != null ? (KEY_ID+"="+user.getId()) : null, null) > 0;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(UPGRADE);
        onCreate(db);
    }

    public static synchronized UsersData getInstance(Context context)
    {
        if (_instance == null)
        {
            _instance = new UsersData(context.getApplicationContext());
        }
        return _instance;
    }
}