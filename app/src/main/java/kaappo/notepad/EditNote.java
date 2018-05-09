package kaappo.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditNote extends AppCompatActivity {

    int noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws RuntimeException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        //Älä muokkaa tästä ylöspäin!

        Intent intent = getIntent();
        if (!intent.hasExtra(MainActivity.NOTE_INTENT_KEY)) {
            throw new RuntimeException("EditNote.java doesnt have an intent attatched!");
        }

        this.noteID = intent.getIntExtra(MainActivity.NOTE_INTENT_KEY, 0);
        Note note = DatabaseHandler.openNoteByID(noteID);

        TextView title = (TextView) findViewById(R.id.title);
        TextView body = (TextView) findViewById(R.id.body);

        title.setText(note.getTitle());
        body.setText(note.getBody());






    }

    public void onUpdateNote (View v) {

        DatabaseHandler.deleteNoteByID(noteID);

        EditText titleField = (EditText) findViewById(R.id.title);
        EditText bodyField = (EditText) findViewById(R.id.body);

        String title = titleField.getText().toString();
        String body = bodyField.getText().toString();

        DatabaseHandler.saveNote(new Note(body, title));

        Intent intent = new Intent(this, ShowNotes.class);
        startActivity(intent);


    }
}
