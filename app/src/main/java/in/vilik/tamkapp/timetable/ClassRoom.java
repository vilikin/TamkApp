package in.vilik.tamkapp.timetable;

/**
 * Created by vili on 16/04/2017.
 */

public class ClassRoom {
    private Reservation parent;
    private String code;
    private String name;
    private String building;

    public ClassRoom() {
    }

    public Reservation getParent() {
        return parent;
    }

    public void setParent(Reservation parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public String toString() {
        return "ClassRoom {" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", building='" + building + '\'' +
                '}';
    }
}
