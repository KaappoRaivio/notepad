package kaappo.notepad;

import android.content.Context;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent.hasExtra("name")) {
            Note note = open(intent.getStringExtra("name"));
            TextView body = (TextView) findViewById(R.id.body);
            TextView title = (TextView) findViewById(R.id.title);

            body.setText(note.getBody());
            title.setText(note.getTitle());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSave(View view) {
        EditText bodyfield = (EditText) findViewById(R.id.body);
        EditText titlefield = (EditText) findViewById(R.id.title);

        String body = bodyfield.getText().toString();
        String title = titlefield.getText().toString();



        Note note = new Note(body, title);

        toasti(title);

        save(title, note);

        toasti(open(title).getTitle());



    }

    public void save(String fileName, Note content) {
        boolean success = false;

        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(fileName, 0));
            out.write(content.repr());
            out.close();
            success = true;
        } catch (Throwable t) {
            toasti("ei onnistunut" + t.toString());
        }

        if (success) {
            writeToMaster(fileName, this);
        }
        else {
            toasti("ongelma!");
        }
    }

    private void writeToMaster(String fName, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("masterTable.txt", Context.MODE_APPEND));
            outputStreamWriter.write(fName + "\n");
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }



    public void toasti(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public boolean FileExists(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public Note open(String fileName) {
        String content = "";

        if (FileExists(fileName)) {

            try {
                InputStream in = openFileInput(fileName);
                if ( in != null) {
                    InputStreamReader tmp = new InputStreamReader( in );
                    BufferedReader reader = new BufferedReader(tmp);

                    String str;
                    StringBuilder buf = new StringBuilder();

                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    }
                    in.close();

                    content = buf.toString();
                }

            }
            catch (java.io.FileNotFoundException e) {toasti("Ei löydy!");} catch (Throwable t) {
                toasti("Virhe" + t.toString());
            }
        }

        return Note.fromRepr(content);
    }

    public void showSaved(View view) {
        Intent intent = new Intent(this, ShowNotes.class);

        startActivity(intent);

    }
}
