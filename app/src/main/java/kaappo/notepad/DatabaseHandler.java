package kaappo.notepad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DatabaseHandler {

    private DatabaseHandler(){}


    private static Context getContext () {
        return MainActivity.getAppContext();
    }

    public static Note openNoteByID (int noteID) {
        SQLiteDatabase db = getContext().openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        Cursor resultSet = db.rawQuery("SELECT * FROM notes WHERE ID = '" + noteID + "';", null);

        resultSet.moveToFirst();

        String title = resultSet.getString(resultSet.getColumnIndex("title"));
        String body = resultSet.getString(resultSet.getColumnIndex("body"));
        long timeCreated = Long.parseLong(resultSet.getString(resultSet.getColumnIndex("timeCreated")));
        int  ID = Integer.parseInt(resultSet.getString(resultSet.getColumnIndex("ID")));

        resultSet.close();

        return new Note(body, title, timeCreated, ID);

    }

    public static void saveNote (Note content) {
        SQLiteDatabase db = getContext().openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        db.execSQL("INSERT INTO notes VALUES('"
                + content.getTitle() + "', '"
                + content.getBody() + "', '"
                + content.getTimeCreated() + "', '"
                + content.getId()
                + "');"
        );
    }

    public static List<Note> getAllNotes () {
        SQLiteDatabase db = getContext().openOrCreateDatabase("notes", MODE_PRIVATE ,null);

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
