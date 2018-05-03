package entities;

// Класс ученика.
public class Pupil {

    private int pupilId;
    private String name;

    public Pupil(int pupilId, String name) {
        this.pupilId = pupilId;
        this.name = name;
    }

    public Pupil() {};

    public int getpupilId() {
        return pupilId;
    }

    public void setpupilId(int id) {
        this.pupilId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
