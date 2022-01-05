package avdeev.geekbrains.data;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private Date date;
    private String importance;

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Note(String title, String description, Date date, String importance) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.importance = importance;
    }

    public Note(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
