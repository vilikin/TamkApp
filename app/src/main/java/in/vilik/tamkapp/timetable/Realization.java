package in.vilik.tamkapp.timetable;

/**
 * Created by vili on 16/04/2017.
 */

public class Realization {
    private Reservation parent;

    private String name;
    private String code;

    public Realization(Reservation parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
