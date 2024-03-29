package avdeev.geekbrains.ui;



import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import avdeev.geekbrains.R;
import avdeev.geekbrains.data.InMemoryRepoImpl;
import avdeev.geekbrains.data.Note;
import avdeev.geekbrains.data.PopupMenuItemClickListener;
import avdeev.geekbrains.data.Repo;
import avdeev.geekbrains.recycler.NoteHolder;
import avdeev.geekbrains.recycler.NotesAdapter;

public class NotesListFragment
        extends Fragment
        implements PopupMenuItemClickListener
{

    private static final String FLAG = "FLAG";
    private NotesAdapter adapter;
    private Repo reposytory = InMemoryRepoImpl.getInstance();
    private RecyclerView list;
    private NotesListFragmentListener listner;
    private Note note;
    private static final String CURRENT_NOTE = "CURRENT_NOTE";

    public static NotesListFragment newInstance(Boolean fillRepo, Note note) {

        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        if (note != null) {
            args.putSerializable(CURRENT_NOTE, note);
        }
        args.putBoolean(FLAG, fillRepo);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof NotesListFragmentListener) {
            this.listner = (NotesListFragmentListener) context;
        } else {
            throw new IllegalStateException("Activity must implements NotesListFragmentListener");
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void click(int command, Note note, int position) {
        switch (command)
        {
            case R.id.context_delete:
                reposytory.delete(note);
                adapter.delete(reposytory.getAll(), position);
                return;

            case R.id.context_modify:

                if (this.listner != null) {
                    this.note = note;
                    this.listner.recyclerViewNoteOnClick(note);
                }

        }
    }

    interface NotesListFragmentListener {
        void recyclerViewNoteOnClick(Note note);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(FLAG) && !args.getBoolean(FLAG)) {
            reposytory.fillRepo(requireContext());
            args.putBoolean(FLAG, true);
        }

        if (args != null && args.containsKey(CURRENT_NOTE)) {
            this.note = (Note)args.getSerializable(CURRENT_NOTE);
        }

        adapter = new NotesAdapter();
        adapter.setNotes(reposytory.getAll());
        adapter.setPopupMenuItemClickListener(this);

        list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(requireContext()));
        list.setAdapter(adapter);
        list.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayout.VERTICAL));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(0, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                NoteHolder holder = (NoteHolder) viewHolder;
                Note note = holder.getNote();
                reposytory.delete(note);
                adapter.delete(reposytory.getAll(), position);
            }
        });
        helper.attachToRecyclerView(list);




        adapter.setOnNoteClickListener(note -> {
            if (this.listner != null) {
                this.note = note;
                this.listner.recyclerViewNoteOnClick(note);
            }
        });

        if (isLandscape() && this.listner != null) {

            if (savedInstanceState != null) {
                this.note = (Note) savedInstanceState.getSerializable(CURRENT_NOTE);
            } else {
                if (this.note == null) {
                    this.note = reposytory.getAll().get(0);
                }
            }

            this.listner.recyclerViewNoteOnClick(this.note);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.main_create) {
            ((NotesListActivity) requireActivity()).recyclerViewNoteOnClick(null);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        if (this.note != null) {
            outState.putSerializable(CURRENT_NOTE, this.note);
        }
        super.onSaveInstanceState(outState);

    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }
}

