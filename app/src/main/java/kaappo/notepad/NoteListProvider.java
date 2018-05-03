package kaappo.notepad;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteListProvider {
    static List<Note> get(Context context) {
        List<Note> tempList = new ArrayList<>();

        String[] masterTable = readFromMaster(context).split("\n");

        for (String i : masterTable) {
            tempList.add(open(i, context));
        }

        return tempList;
    }

    public static boolean FileExists(String fname, Context context) {
        File file = context.getFileStreamPath(fname);
        return file.exists();
    }

    public static Note open(String fileName, Context context) {
        String content = "";

        if (FileExists(fileName, context)) {

            try {
                InputStream in = context.openFileInput(fileName);
                if (in != null) {
                    InputStreamReader tmp = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(tmp);

                    String str;
                    StringBuilder buf = new StringBuilder();

                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    }
                    in.close();

                    content = buf.toString();
                }

            } catch (Throwable e) {
            }

        }

        return Note.fromRepr(content);
    }

    public static String readFromMaster(Context context) {
        String content = "";

        if (FileExists("masterTable.txt", context)) {

            try {
                InputStream in = context.openFileInput("masterTable.txt");
                if (in != null) {
                    InputStreamReader tmp = new InputStreamReader(in);
                    BufferedReader reader = new BufferedReader(tmp);

                    String str;
                    StringBuilder buf = new StringBuilder();

                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    }
                    in.close();

                    content = buf.toString();
                }

            } catch (Throwable t) {
            }

        }

        return content;
    }
}