package kaappo.notepad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        SQLiteDatabase db = openOrCreateDatabase("notes", MODE_PRIVATE, null);

        
        if (intent.hasExtra("note")) {

            Bundle bundle = intent.getBundleExtra("note");

            String noteTitle = bundle.getString("title");
            String noteBody = bundle.getString("body");
            int noteId = bundle.getInt("id");
            long noteTimeCreated = bundle.getLong("timeCreated");


            Note note = new Note(noteBody, noteTitle, noteTimeCreated, noteId);

            System.out.println("oncreate: title:" + note.getTitle());
            System.out.println("oncreate: body:" + note.getBody());


            EditText body = (EditText) findViewById(R.id.body);
            EditText title = (EditText) findViewById(R.id.title);

            body.setText(note.getBody());
            title.setText(note.getTitle());
        }
        else {
            db.execSQL("CREATE TABLE IF NOT EXISTS notes(title TEXT, body TEXT, timeCreated TEXT, ID TEXT PRIMARY KEY)");
            db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS idx_positions_title ON notes (ID);");
        }


    }

    public void onSave(View view) {
        EditText bodyfield = (EditText) findViewById(R.id.body);
        EditText titlefield = (EditText) findViewById(R.id.title);

        String body = bodyfield.getText().toString();
        String title = titlefield.getText().toString();



        Note note = new Note(body, title, this);

        toasti(title);

        DatabaseHandler.replaceNoteByID(note);

        showSaved(null);




    }

    public void save(Note content) {
        SQLiteDatabase db = openOrCreateDatabase("notes", MODE_PRIVATE, null);
        //db.execSQL("CREATE TABLE IF NOT EXISTS notes(title TEXT, body TEXT, timeCreated TEXT, ID TEXT)");

        db.execSQL("INSERT INTO notes VALUES('"
                + content.getTitle() + "', '"
                + content.getBody() + "', '"
                + content.getTimeCreated() + "', '"
                + content.getId()
                + "');"
        );


    }



    public void toasti(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }



    public Note open(String name) {

        SQLiteDatabase db = openOrCreateDatabase("notes", MODE_PRIVATE,null);

        Cursor resultSet = db.rawQuery("Select * from notes where title = '" + name + "';", null);

        resultSet.moveToFirst();

        String title = resultSet.getString(resultSet.getColumnIndex("title"));
        String body = resultSet.getString(resultSet.getColumnIndex("body"));
        long timeCreated = Long.parseLong(resultSet.getString(resultSet.getColumnIndex("timeCreated")));
        int  ID = Integer.parseInt(resultSet.getString(resultSet.getColumnIndex("ID")));

        resultSet.close();

        return new Note(body, title, timeCreated, ID);

    }

    public Note open(int mID) {

        SQLiteDatabase db = openOrCreateDatabase("notes", MODE_PRIVATE,null);

        Cursor resultSet = db.rawQuery("Select * from notes where ID = '" + mID + "';", null);

        resultSet.moveToFirst();

        String title = resultSet.getString(resultSet.getColumnIndex("title"));
        String body = resultSet.getString(resultSet.getColumnIndex("body"));
        long timeCreated = Long.parseLong(resultSet.getString(resultSet.getColumnIndex("timeCreated")));
        int  ID = Integer.parseInt(resultSet.getString(resultSet.getColumnIndex("ID")));

        resultSet.close();

        return new Note(body, title, timeCreated, ID);

    }


    public void showSaved(View view) {
        Intent intent = new Intent(this, ShowNotes.class);

        startActivity(intent);

    }

    public static Context getAppContext() {
        return MainActivity.context;
    }
}
