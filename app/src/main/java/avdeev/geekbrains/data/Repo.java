package avdeev.geekbrains.data;

import android.content.Context;

import java.util.List;

public interface Repo {

    int  create(Note note);
    Note read(int id);
    void update(Note note);
    void delete(Note note);
    void delete(int id);
    void fillRepo(Context context);
    void save();

    List<Note> getAll();
}
