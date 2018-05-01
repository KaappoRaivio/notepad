package kaappo.notepad;

import java.util.ArrayList;
import java.util.List;

public class Note {
    private static List<Note> instances = new ArrayList<>();

    private String body;
    private String title;
    private long timeCreated;

    Note(String body, String title) {
        this.body = body;
        this.title = title;
        this.timeCreated = System.currentTimeMillis();

        Note.instances.add(this);
    }

    private Note(String body, String title, long timeCreated) {
        this.body = body;
        this.title = title;
        this.timeCreated = timeCreated;

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

    public static List<Note> getInstances() {
        return instances;
    }

    public String repr() {
        return "\t" + body + "\udbff\udfff" + title + "\udbff\udfff" + timeCreated + "\udbff\udfff";
    }

    public static Note fromRepr(String repr) {
        String title;
        String body;
        long timeCreated;

        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < repr.length(); i++) {
            if (repr.substring(i, i + 1).equals("\udbff\udfff")) {
                indexes.add(i);
            }
        }

        body = repr.substring(indexes.get(0), indexes.get(1));
        title = repr.substring(indexes.get(1), indexes.get(2));
        timeCreated = Long.parseLong(repr.substring(indexes.get(2), indexes.get(3)));

        return new Note(body, title, timeCreated);
    }
}
