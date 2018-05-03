package kaappo.notepad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class ShowNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notes);

        RecyclerView rv = (RecyclerView) findViewById(R.id.noteList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new NoteAdapter(new NoteListProvider().get(this)));
    }

    class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
        private final List<Note> entries;

        NoteAdapter(List<Note> entries) {
            this.entries = entries;
        }

        @Override
        public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            final View v = layoutInflater.inflate(R.layout.kauppalista, viewGroup, false);

            return new NoteViewHolder(v);
            }

        @Override
        public void onBindViewHolder(NoteViewHolder noteViewHolder, int i) {
            noteViewHolder.title.setText(this.entries.get(i).getTitle());
            noteViewHolder.body.setText(this.entries.get(i).getBody());

            noteViewHolder.card.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noteViewHolder.card.getLayoutParams().height = 256;


            noteViewHolder.card.setTag(this.entries.get(i).getId());
        }

        @Override
        public int getItemCount() {
            return this.entries.size();
        }

    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView body;
        CardView card;

        NoteViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            body = (TextView) view.findViewById(R.id.body);
            card = (CardView) view.findViewById(R.id.card);
        }
    }

    public void openNote(View v) {
        Note note = Note.findNoteById((Integer) v.getTag());
        System.out.println("" + (Integer) v.getTag() + " " + note.getTitle());
        String title = note.getTitle();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", title);
        startActivity(intent);
    }
}
