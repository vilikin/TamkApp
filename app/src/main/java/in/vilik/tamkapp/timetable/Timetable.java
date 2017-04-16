package in.vilik.tamkapp.timetable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vili on 16/04/2017.
 */

public class Timetable {
    private List<TimetableElement> elements;

    public Timetable() {
        this.elements = new ArrayList<>();
    }

    public List<TimetableElement> getElements() {
        return elements;
    }

    public void setElements(List<TimetableElement> elements) {
        this.elements = elements;
    }
}
