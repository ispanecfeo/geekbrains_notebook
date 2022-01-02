package avdeev.geekbrains.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import avdeev.geekbrains.R;
import avdeev.geekbrains.data.Constants;
import avdeev.geekbrains.data.InMemoryRepoImpl;
import avdeev.geekbrains.data.Note;
import avdeev.geekbrains.data.Repo;

public class EditNoteActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private Button saveNote;
    private int id = -1;
    private Note note;
    private String mode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_note);

        title = findViewById(R.id.edit_note_title);
        description = findViewById(R.id.edit_note_description);
        saveNote = findViewById(R.id.edit_note_update);

        Intent intent = getIntent();
        if(intent != null)
        {
            this.mode = intent.getStringExtra(Constants.MODE);

            if (this.mode.equals(Constants.UPDATE)) {

                this.note = (Note) intent.getSerializableExtra(Constants.NOTE);
                id = this.note.getId();
                title.setText(note.getTitle());
                description.setText(note.getDescription());
            }

        }

        saveNote.setOnClickListener(v -> {

            Repo repo = InMemoryRepoImpl.getInstance();

            if (this.mode.equals(Constants.UPDATE)) {
                this.note.setTitle(this.title.getText().toString());
                this.note.setDescription(this.description.getText().toString());
                repo.update(this.note);
            } else if (this.mode.equals(Constants.ADD)) {
                repo.create(
                        new Note(
                             this.title.getText().toString(),
                             this.description.getText().toString()
                        )
                );
            }
            setResult(Activity.RESULT_OK);
            finish();
        });

    }
}
