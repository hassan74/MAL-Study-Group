package com.enterprises.wayne.yugicards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 7/31/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    /* constants */
    public static final String DATABASE_NAME = "yugy.data";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String createSql =
                "CREATE TABLE CARDS(\n" +
                        "title TEXT PRIMARY KEY\n" +
                        ", description TEXT \n" +
                        ", type TEXT \n" +
                        ", image_url TEXT\n" +
                        ", UNIQUE (title) ON CONFLICT REPLACE)";
        sqLiteDatabase.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String deleteSql = "DROP TABLE IF EXISTS CARDS\n";
        sqLiteDatabase.execSQL(deleteSql);
        onCreate(sqLiteDatabase);
    }


    /**
     * adds a card to the database
     * the key is the card's title
     * replaces in case of conflict
     */
    public void insertCard(Card card)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", card.getTitle());
        contentValues.put("description", card.getDescription());
        contentValues.put("type", card.getType());
        contentValues.put("image_url", card.getImageUrl());

        getWritableDatabase().insert("CARDS", null, contentValues);
    }


    /**
     * returns all the cards in the database
     */
    public List<Card> getCards()
    {
        Cursor cursor = getReadableDatabase().query("CARDS", null, null, null, null, null, null);

        // parse data from the cursor
        List<Card> cardList = new ArrayList<>();
        if (cursor.moveToFirst())
            do
            {
                Card card = new Card();

                card.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                card.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                card.setType(cursor.getString(cursor.getColumnIndex("type")));
                card.setImageUrl(cursor.getString(cursor.getColumnIndex("image_url")));

                cardList.add(card);
            } while (cursor.moveToNext());

        return cardList;
    }
}
