package entities;

import java.util.List;

public class Class {

    private int classId;
    //@JsonDeserialize(using = PupilsJsonDeserializer.class)
    private List<Pupil> pupils;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public List<Pupil> getPupils() {
        return pupils;
    }

    public void setPupils(List<Pupil> pupils) {
        this.pupils = pupils;
    }
}
