package avdeev.geekbrains.recycler;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import avdeev.geekbrains.R;
import avdeev.geekbrains.data.Note;
import avdeev.geekbrains.data.PopupMenuItemClickListener;

public class NoteHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    private TextView title;
    private TextView description;
    private TextView note_date;
    private TextView note_level;
    private Note note;
    private PopupMenu popupMenu;
    private ImageView noteMenu;
    private PopupMenuItemClickListener popupMenuItemClickListener;

    public NoteHolder(@NonNull View itemView, NotesAdapter.OnNoteClickListener listener, PopupMenuItemClickListener popupMenuItemClickListener) {
        super(itemView);

        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        note_date   = itemView.findViewById(R.id.note_date_parent);
        note_level  = itemView.findViewById((R.id.note_level));
        this.popupMenuItemClickListener = popupMenuItemClickListener;

        noteMenu    = itemView.findViewById(R.id.note_menu);

        popupMenu   = new PopupMenu(itemView.getContext(), noteMenu);
        popupMenu.inflate(R.menu.context);
        popupMenu.setOnMenuItemClickListener(this);

        noteMenu.setOnClickListener(v -> popupMenu.show());

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


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.context_delete:
                this.popupMenuItemClickListener.click(
                        R.id.context_delete,
                        this.note,
                        getAdapterPosition()
                );
                return true;

            case R.id.context_modify:
                this.popupMenuItemClickListener.click(
                        R.id.context_modify,
                        this.note,
                        getAdapterPosition()
                );
                return true;
            default:
                return false;
        }
    }

    public Note getNote() {
        return this.note;
    }
}
