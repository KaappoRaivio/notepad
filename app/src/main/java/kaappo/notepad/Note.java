package kaappo.notepad;

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

    Note(String body, String title) {
        this.body = body;
        this.title = title;
        this.timeCreated = System.currentTimeMillis();

        Note.instances.add(this);

        this.id = Note.numberOfInstances++;
    }

    private Note(String body, String title, long timeCreated) {
        this.body = body;
        this.title = title;
        this.timeCreated = timeCreated;

        Note.instances.add(this);
        this.id = Note.numberOfInstances++;
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

    public String repr() {
        return "\t" + body + "\t" + title + "\t" + timeCreated + "\t";
    }

    public static Note fromRepr(String repr) {
        String title;
        String body;
        long timeCreated;

        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < repr.length(); i++) {
            if (repr.substring(i, i + 1).equals("\t")) {
                indexes.add(i);
            }
        }

        body = repr.substring(indexes.get(0) + 1, indexes.get(1));
        title = repr.substring(indexes.get(1) + 1, indexes.get(2));
        timeCreated = Long.parseLong(repr.substring(indexes.get(2) + 1, indexes.get(3)));

        return new Note(body, title, timeCreated);
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


}
