package avdeev.geekbrains.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import avdeev.geekbrains.R;
import avdeev.geekbrains.data.Note;
import avdeev.geekbrains.data.PopupMenuItemClickListener;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder>
{

    private List<Note> notes = new ArrayList<>();
    private PopupMenuItemClickListener popupMenuItemClickListener;

    public void setNotes(List<Note> notes){

        this.notes = notes;
        notifyDataSetChanged();

    }


    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }


    private OnNoteClickListener listener;


    public void setOnNoteClickListener(OnNoteClickListener listener) {

        this.listener = listener;
    }

    public void setPopupMenuItemClickListener(PopupMenuItemClickListener listener) {
        this.popupMenuItemClickListener = listener;
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(
                R.layout.fragment_note_item,
                parent,
                false
        );

        return new NoteHolder(view, listener, this.popupMenuItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(
            notes.get(position)
        );
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void delete(List<Note> all, int position) {
        this.notes = all;
        notifyItemRemoved(position);
    }
}
