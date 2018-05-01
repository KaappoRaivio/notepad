package kaappo.notepad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSave(View view) {
        String body = findViewById(R.id.body).toString();
        String title = findViewById(R.id.title).toString();

        Note note = new Note(body, title);

        Save(title, note);

    }

    public void Save(String fileName, Note content) {
        try {
            OutputStreamWriter out =
                    new OutputStreamWriter(openFileOutput(fileName, 0));
            out.write(content.repr());
            out.close();
            toasti("onnistui");
        } catch (Throwable t) {
            toasti("ei onnistunut" + t.toString());
        }
    }

    public void toasti(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean FileExists(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public String Open(String fileName) {
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

        return content;
    }
}
