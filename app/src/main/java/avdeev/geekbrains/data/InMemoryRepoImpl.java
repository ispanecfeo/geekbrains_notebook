package avdeev.geekbrains.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InMemoryRepoImpl implements Repo {

    private static InMemoryRepoImpl repo;

    public static Repo getInstance()
    {
        if(repo == null)
        {
            repo = new InMemoryRepoImpl();
        }
        return repo;
    }


    private InMemoryRepoImpl()
    {

    }


    private ArrayList<Note> notes = new ArrayList<>();
    private int counter = 0;


    @Override
    public int create(Note note) {
        int id = counter++;
        note.setId(id);
        notes.add(note);
        return id;
    }

    @Override
    public Note read(int id) {
        for(int i = 0; i < notes.size(); i++)
        {
            if(notes.get(i).getId() == id)
                return notes.get(i);
        }
        return null;
    }

    @Override
    public void update(Note note) {
        for(int i = 0; i < notes.size(); i++)
        {
            if(notes.get(i).getId().equals(note.getId())) {
                notes.set(i, note);
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for(int i = 0; i < notes.size(); i++)
        {
            if(notes.get(i).getId() == id)
            {
                notes.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Note> getAll() {
        return notes;
    }


    public void fillRepo() {

        Date current = Calendar.getInstance().getTime();
        String usualLevel = "usual";

        repo.create(new Note("Title 1", "Description 1", current, usualLevel));
        repo.create(new Note("Title 2", "Description 2", current, usualLevel));
        repo.create(new Note("Title 3", "Description 3", current, usualLevel));
        repo.create(new Note("Title 4", "Description 4", current, usualLevel));
        repo.create(new Note("Title 5", "Description 5", current, usualLevel));
        repo.create(new Note("Title 6", "Description 6", current, usualLevel));
        repo.create(new Note("Title 7", "Description 7", current, usualLevel));
        repo.create(new Note("Title 8", "Description 8", current, usualLevel));
        repo.create(new Note("Title 9", "Description 9", current, usualLevel));
        repo.create(new Note("Title 10", "Description 10", current, usualLevel));
        repo.create(new Note("Title 11", "Description 11", current, usualLevel));
        repo.create(new Note("Title 12", "Description 12", current, usualLevel));
        repo.create(new Note("Title 13", "Description 13", current, usualLevel));
        repo.create(new Note("Title 14", "Description 14", current, usualLevel));
        repo.create(new Note("Title 15", "Description 15", current, usualLevel));
        repo.create(new Note("Title 16", "Description 16", current, usualLevel));

    }
}