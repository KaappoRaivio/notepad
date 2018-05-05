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
    static List<Note> get() {
        return DatabaseHandler.getAllNotes();

    }


}