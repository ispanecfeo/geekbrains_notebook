package avdeev.geekbrains.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import avdeev.geekbrains.R;
import avdeev.geekbrains.data.Note;

public class NotesListActivity extends AppCompatActivity implements NotesListFragment.NotesListFragmentListener {

    private NotesListFragment notesListFragment;
    private Note note = null;
    public static final String CURRENT_NOUT = "CURRENT_NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_notes_main);

        if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_NOUT)) {
            this.note = (Note) savedInstanceState.getSerializable(CURRENT_NOUT);
        }

        if (savedInstanceState == null)
            startNotesListFragment(false);
        else {
            startNotesListFragment(true);
        }

    }


    @Override
    public void recyclerViewNoteOnClick(Note note) {

        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(note);

        if (isLandscape()) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_edit_note, editNoteFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        } else {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, editNoteFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        }

    }


    private void startNotesListFragment(Boolean fillRepo) {

       if (this.notesListFragment == null) {
           this.notesListFragment = NotesListFragment.newInstance(fillRepo, this.note);
       }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, notesListFragment)
                .commit();
    }

    public void onUpdateNote() {
        startNotesListFragment(true);
    }

    public void setNote(Note note) {
        this.note = note;
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (this.note != null) {
            outState.putSerializable(CURRENT_NOUT, this.note);
        }
        super.onSaveInstanceState(outState);
    }
}