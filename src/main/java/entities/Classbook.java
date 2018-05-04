package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Daniel.Khaliulin on 25.04.2018.
 */
public class Classbook {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int classId;
    private int subjectId;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int id) {
        this.classId = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
