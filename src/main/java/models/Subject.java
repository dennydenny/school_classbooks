package models;

public class Subject {

    private int id;
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public Subject(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
