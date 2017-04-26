package in.vilik.tamkapp.timetable;

/**
 * Implements a class representing class room details.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class ClassRoom {

    /**
     * Code of the class room, e.g. C3-21.
     */
    private String code;

    /**
     * Name of the class room, e.g. Game Lab.
     */
    private String name;

    /**
     * Name of the building the class room is located in.
     */
    private String building;

    /**
     * Gets code of the class room.
     *
     * @return  Code of the class room
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code for the class room.
     *
     * @param code  Code for the class room
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets name of the class room.
     *
     * @return Name of the class room
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for the class room.
     *
     * @param name Name for the class room
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets building of the class room.
     *
     * @return  Name of the class room
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Sets building for the class room.
     *
     * @param building  Building for the class room
     */
    public void setBuilding(String building) {
        this.building = building;
    }
}
