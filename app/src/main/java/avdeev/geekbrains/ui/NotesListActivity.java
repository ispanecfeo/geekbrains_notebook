package avdeev.geekbrains.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerNonConfig;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import avdeev.geekbrains.R;
import avdeev.geekbrains.data.InMemoryRepoImpl;
import avdeev.geekbrains.data.Note;

public class NotesListActivity extends AppCompatActivity
        implements NotesListFragment.NotesListFragmentListener,
        YesNoDialogFragment.ClosingDialogListner, NavigationView.OnNavigationItemSelectedListener {

    private NotesListFragment notesListFragment;
    private Note note = null;
    public static final String CURRENT_NOTE = "CURRENT_NOTE";
    private DrawerLayout drawer;
    private AppBarConfiguration mAppBarConfiguration;
    public String EDIT_TAG = "EDIT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_notes_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_NOTE)) {
            this.note = (Note) savedInstanceState.getSerializable(CURRENT_NOTE);
        }

        startNotesListFragment(savedInstanceState != null);

    }


    @Override
    public void recyclerViewNoteOnClick(Note note) {

        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(note);

        if (isLandscape()) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_edit_note, editNoteFragment, EDIT_TAG)
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
            outState.putSerializable(CURRENT_NOTE, this.note);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {

        if (isLandscape()) {
            new YesNoDialogFragment().show(getSupportFragmentManager(), null);
            return;
        }
        if (getSupportFragmentManager().getFragments().size() == 1 && getSupportFragmentManager().getFragments().get(0) instanceof NotesListFragment) {
            new YesNoDialogFragment().show(getSupportFragmentManager(), null);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void Close() {
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_main:
                startNotesListFragment(true);
                break;

            case R.id.nav_about:

                if (isLandscape()) {
                    EditNoteFragment editNoteFragment = (EditNoteFragment) getSupportFragmentManager().findFragmentByTag(EDIT_TAG);
                    if (editNoteFragment != null) {
                          getSupportFragmentManager()
                            .beginTransaction()
                            .remove(editNoteFragment)
                            .commit();
                    }
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,
                        new AboutFragment()).commit();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        InMemoryRepoImpl.getInstance().save();
        super.onDestroy();
    }
}