package avdeev.geekbrains.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.nio.BufferUnderflowException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

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
    private Spinner level;
    private EditText dateText;
    private Date noteDate;
    private NotesListActivity listner;
    private DatePickerDialog picker;
    private Calendar calendar;

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
        this.calendar = Calendar.getInstance();
        this.title = (EditText)view.findViewById(R.id.edit_note_title);
        this.description = (EditText)view.findViewById(R.id.edit_note_description);
        this.level = (Spinner) view.findViewById(R.id.spinner);
        this.dateText = (EditText) view.findViewById(R.id.note_date);
        this.dateText.setInputType(EditorInfo.TYPE_NULL);

        this.dateText.setOnClickListener(v -> {

            if (this.noteDate == null){
                this.noteDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            }

            this.calendar.setTime(this.noteDate);

            picker = new DatePickerDialog(
                    requireContext(),
                    (view1, year, month, dayOfMonth) -> {
                        this.calendar.set(year, month, dayOfMonth);
                        this.noteDate = this.calendar.getTime();
                        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        this.dateText.setText(dateFormat.format(this.noteDate));
                    },
                    this.calendar.get(Calendar.YEAR),
                    this.calendar.get(Calendar.MONTH),
                    this.calendar.get(Calendar.DAY_OF_MONTH)
            );
            picker.show();

        });


        if (args != null && args.containsKey(KEY)) {
            this.note = (Note) args.getSerializable(KEY);
            this.title.setText(this.note.getTitle());
            this.description.setText(this.note.getDescription());
            this.noteDate = this.note.getDate();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            this.dateText.setText(dateFormat.format(this.noteDate));
            String[] levels = getResources().getStringArray(R.array.importance);
            ArrayAdapter<String> stringAdapter =
                    new ArrayAdapter<>(
                            requireContext(),
                            R.layout.support_simple_spinner_dropdown_item,
                            levels
                    );
            this.level.setAdapter(stringAdapter);
            this.level.setSelection(stringAdapter.getPosition(this.note.getImportance()));
        }

        view.findViewById(R.id.edit_note_update).setOnClickListener(v -> {

            Repo repo = InMemoryRepoImpl.getInstance();

            if (this.note != null) {
                this.note.setTitle(this.title.getText().toString());
                this.note.setDescription(this.description.getText().toString());
                this.note.setDate(this.noteDate);
                this.note.setImportance(this.level.getSelectedItem().toString());
                repo.update(this.note);
            } else {
                this.note = new Note(
                    this.title.getText().toString(),
                    this.description.getText().toString(),
                    this.noteDate,
                    this.level.getSelectedItem().toString()
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
