package in.vilik.tamkapp.timetable;

/**
 * Implements a class representing realization details.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0426
 * @since 1.7
 */
public class Realization {

    /**
     * Name of the realization.
     */
    private String name;

    /**
     * Code of the realization.
     */
    private String code;

    /**
     * Gets name of the realization.
     *
     * @return  Name of the realization
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name for the realization.
     *
     * @param name  Name for the realization
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets code of the realization.
     *
     * @return  Code of the realization
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code for the realization.
     *
     * @param code Code for the realization
     */
    public void setCode(String code) {
        this.code = code;
    }
}
