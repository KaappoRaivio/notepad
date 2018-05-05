package kaappo.notepad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NoteListProvider {
    static List<Note> get(Context context) {

        SQLiteDatabase db = context.openOrCreateDatabase("notes", MODE_PRIVATE ,null);

        List<Note> notes = new ArrayList<>();

        Cursor resultSet = db.rawQuery("Select * from notes", null);

        while (resultSet.moveToNext()) {
            String title = resultSet.getString(resultSet.getColumnIndex("title"));
            String body = resultSet.getString(resultSet.getColumnIndex("body"));
            long timeCreated = Long.parseLong(resultSet.getString(resultSet.getColumnIndex("timeCreated")));
            int  ID = Integer.parseInt(resultSet.getString(resultSet.getColumnIndex("ID")));

            Note note = new Note(body, title, timeCreated, ID);
            notes.add(note);

        }

        resultSet.close();

        return notes;


    }


}