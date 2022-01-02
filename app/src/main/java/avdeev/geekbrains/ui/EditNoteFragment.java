package avdeev.geekbrains.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.nio.BufferUnderflowException;

import avdeev.geekbrains.R;
import avdeev.geekbrains.data.Constants;
import avdeev.geekbrains.data.InMemoryRepoImpl;
import avdeev.geekbrains.data.Note;
import avdeev.geekbrains.data.Repo;


public class EditNoteFragment extends Fragment {

    public static final String KEY = "NOTE";
    private Note note;
    private EditText title;
    private EditText description;
    private NotesListActivity listner;

    public static EditNoteFragment newInstance(Note note) {

        EditNoteFragment fragment = new EditNoteFragment();
        if (note != null) {
            Bundle args = new Bundle();
            args.putSerializable(KEY, note);
            fragment.setArguments(args);
        }
        return fragment;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NotesListActivity) {
            this.listner = (NotesListActivity) context;
        } else {
            throw new IllegalStateException("Activity must implements NotesListFragmentListener");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        this.title = (EditText)view.findViewById(R.id.edit_note_title);
        this.description = (EditText)view.findViewById(R.id.edit_note_description);

        if (args != null && args.containsKey(KEY)) {
            this.note = (Note) args.getSerializable(KEY);
            this.title.setText(this.note.getTitle());
            this.description.setText(this.note.getDescription());
        }

        view.findViewById(R.id.edit_note_update).setOnClickListener(v -> {

            Repo repo = InMemoryRepoImpl.getInstance();

            if (this.note != null) {
                this.note.setTitle(this.title.getText().toString());
                this.note.setDescription(this.description.getText().toString());
                repo.update(this.note);
            } else {
                this.note = new Note(
                    this.title.getText().toString(),
                    this.description.getText().toString()
                );
                repo.create(this.note);
            }

            ((NotesListActivity)requireActivity()).onUpdateNote();
        });

        if (this.listner != null) {
            this.listner.setNote(this.note);
        }
    }
}
