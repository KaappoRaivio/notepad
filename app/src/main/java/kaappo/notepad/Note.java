package kaappo.notepad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private static List<Note> instances = new ArrayList<>();

    private static int numberOfInstances;

    private String body;
    private String title;
    private long timeCreated;
    private int id;

    Note(String body, String title, Context context) {
        this.body = body;
        this.title = title;
        this.timeCreated = System.currentTimeMillis();

        Note.instances.add(this);

        this.id = getFreeID(context);
    }

    private Note(String body, String title, long timeCreated, Context context) {
        this.body = body;
        this.title = title;
        this.timeCreated = timeCreated;

        Note.instances.add(this);
        this.id = getFreeID(context);
    }

    Note(String body, String title, long timeCreated, int id) {
        this.body = body;
        this.title = title;
        this.timeCreated = timeCreated;
        this.id = id;

        Note.instances.add(this);
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public static List<Note> getInstances() {
        return instances;
    }


    @Nullable
    public static Note findNoteById(int iID) {
        int index = -1;

        for (int i = 0; i < instances.size(); i++) {
            if (instances.get(i).getId() == iID) {
                index = i;
                break;

            }
        }

        if (index != -1) {
            System.out.println("instance: " + instances.get(index).getTitle());
            return instances.get(index);
            //return instances.get(2);
        }
        else {
            System.out.println("findEntryById: Invalid index " + iID + "!");
            return null;
        }

    }

    public static int getFreeID(Context context) {
        SQLiteDatabase db = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);

        Cursor resultSet = db.rawQuery("SELECT MAX(ID) FROM notes;", null);

        resultSet.moveToFirst();

        String result = resultSet.getString(0);

        int biggestID;

        if (result == null) {
            biggestID = 0;

        }
        else {
            biggestID = Integer.parseInt(result);
        }



        return biggestID + 1;
    }


}
