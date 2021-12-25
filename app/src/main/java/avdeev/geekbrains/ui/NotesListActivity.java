package avdeev.geekbrains.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import avdeev.geekbrains.R;
import avdeev.geekbrains.data.Constants;
import avdeev.geekbrains.data.InMemoryRepoImpl;
import avdeev.geekbrains.data.Note;
import avdeev.geekbrains.data.Repo;
import avdeev.geekbrains.recycler.NotesAdapter;

public class NotesListActivity extends AppCompatActivity implements NotesAdapter.OnNoteClickListener {

    // private Repo repository = new InMemoryRepoImpl();
    private Repo repository = InMemoryRepoImpl.getInstance();
    private RecyclerView list;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        fillRepo();

        adapter = new NotesAdapter();
        adapter.setNotes(repository.getAll());

        adapter.setOnNoteClickListener(this);


        list = findViewById(R.id.list);
        // list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list.setLayoutManager(new LinearLayoutManager(this)); // Vertical
        list.setAdapter(adapter);


    }

    private void fillRepo() {
        repository.create(new Note("Title 1", "Description 1"));
        repository.create(new Note("Title 2", "Description 2"));
        repository.create(new Note("Title 3", "Description 3"));
        repository.create(new Note("Title 4", "Description 4"));
        repository.create(new Note("Title 5", "Description 5"));
        repository.create(new Note("Title 6", "Description 6"));
        repository.create(new Note("Title 7", "Description 7"));
        repository.create(new Note("Title 8", "Description 8"));
        repository.create(new Note("Title 9", "Description 9"));
        repository.create(new Note("Title 10", "Description 10"));
        repository.create(new Note("Title 11", "Description 11"));
        repository.create(new Note("Title 12", "Description 12"));
        repository.create(new Note("Title 13", "Description 13"));
        repository.create(new Note("Title 14", "Description 14"));
        repository.create(new Note("Title 15", "Description 15"));
        repository.create(new Note("Title 16", "Description 16"));
    }

    @Override
    public void onNoteClick(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(Constants.NOTE, note);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.main_create:
                // TODO запустить EditNoteActivity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}