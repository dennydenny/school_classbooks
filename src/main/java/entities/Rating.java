package entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

// Сущность оценка.
public class Rating {

    private int id;
    private int classbookId;
    private int pupilId;
    private int mark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyy")
    private Date date;

    public Rating() {
    }

    public Rating(int classbookId, int pupilId, int mark, Date date) {
        this.classbookId = classbookId;
        this.pupilId = pupilId;
        this.mark = mark;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassbookId() {
        return classbookId;
    }

    public void setClassbookId(int classbookId) {
        this.classbookId = classbookId;
    }

    public int getPupilId() {
        return pupilId;
    }

    public void setPupilId(int pupilId) {
        this.pupilId = pupilId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
