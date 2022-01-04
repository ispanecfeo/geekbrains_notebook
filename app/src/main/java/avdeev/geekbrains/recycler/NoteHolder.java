package avdeev.geekbrains.recycler;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import avdeev.geekbrains.R;
import avdeev.geekbrains.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private TextView note_date;
    private TextView note_level;
    private Note note;

    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener) {
        super(itemView);
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        note_date   = itemView.findViewById(R.id.note_date_parent);
        note_level  = itemView.findViewById((R.id.note_level));

        itemView.setOnClickListener(v -> listener.onNoteClick(note));
    }

    void bind(Note note)
    {
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        note_date.setText(dateFormat.format(note.getDate()));
        note_level.setText(note.getImportance());
    }


}
